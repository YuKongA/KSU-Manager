import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.android)
}

val appName = "KernelSU"
val pkgName = "top.yukonga.kernelsu"

@Suppress("UnstableApiUsage")
android {
    namespace = pkgName
    compileSdk = 36

    defaultConfig {
        applicationId = pkgName
        minSdk = 26
        targetSdk = 36
        versionCode = 10003
        versionName = "1.0.3"
    }
    val properties = Properties()
    runCatching { properties.load(project.rootProject.file("local.properties").inputStream()) }
    val keystorePath = properties.getProperty("KEYSTORE_PATH") ?: System.getenv("KEYSTORE_PATH")
    val keystorePwd = properties.getProperty("KEYSTORE_PASS") ?: System.getenv("KEYSTORE_PASS")
    val alias = properties.getProperty("KEY_ALIAS") ?: System.getenv("KEY_ALIAS")
    val pwd = properties.getProperty("KEY_PASSWORD") ?: System.getenv("KEY_PASSWORD")
    if (keystorePath != null) {
        signingConfigs {
            register("github") {
                storeFile = file(keystorePath)
                storePassword = keystorePwd
                keyAlias = alias
                keyPassword = pwd
                enableV3Signing = true
                enableV4Signing = true
            }
        }
    } else {
        signingConfigs {
            register("release") {
                enableV3Signing = true
                enableV4Signing = true
            }
        }
    }
    androidResources.generateLocaleConfig = true
    buildFeatures {
        buildConfig = true
        compose = true
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            vcsInfo.include = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName(if (keystorePath != null) "github" else "release")
        }
        debug {
            if (keystorePath != null) signingConfig = signingConfigs.getByName("github")
        }
    }
    dependenciesInfo.includeInApk = false
    kotlin.jvmToolchain(21)
    packaging {
        applicationVariants.all {
            outputs.all {
                (this as BaseVariantOutputImpl).outputFileName = "$appName-v$versionName($versionCode)-$name.apk"
            }
        }
        resources.excludes += "**"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)

    implementation(libs.miuix)
    implementation(libs.haze)

    implementation(libs.com.github.topjohnwu.libsu.core)
    implementation(libs.com.github.topjohnwu.libsu.service)
    implementation(libs.com.github.topjohnwu.libsu.io)

    debugImplementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
}