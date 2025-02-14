package top.yukonga.kernelsu.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.pm.PackageInfoCompat

import com.topjohnwu.superuser.Shell

@Composable
fun getSELinuxStatus(): String {
    val shell = Shell.Builder.create()
        .setFlags(Shell.FLAG_REDIRECT_STDERR)
        .build("sh")

    val list = ArrayList<String>()
    val result = shell.use {
        it.newJob().add("getenforce").to(list, list).exec()
    }
    val output = result.out.joinToString("\n").trim()

    if (result.isSuccess) {
        return when (output) {
            "Enforcing" -> "Enforcing"
            "Permissive" -> "Permissive"
            "Disabled" -> "Disabled"
            else -> "Unknown"
        }
    }

    return if (output.endsWith("Permission denied")) {
        "Permission denied"
    } else {
        "Unknown"
    }
}

@Composable
fun getManagerVersion(): Pair<String, Long> {
    val context = LocalContext.current
    val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)!!
    val versionCode = PackageInfoCompat.getLongVersionCode(packageInfo)
    return Pair(packageInfo.versionName!!, versionCode)
}