package top.yukonga.kernelsu.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.pm.PackageInfoCompat
import com.topjohnwu.superuser.Shell
import top.yukonga.kernelsu.R

@Composable
@Suppress("DEPRECATION")
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
            "Enforcing" -> stringResource(R.string.selinux_status_enforcing)
            "Permissive" -> stringResource(R.string.selinux_status_permissive)
            "Disabled" -> stringResource(R.string.selinux_status_disabled)
            else -> stringResource(R.string.selinux_status_unknown)
        }
    }

    return if (output.endsWith("Permission denied")) {
        stringResource(R.string.selinux_status_enforcing)
    } else {
        stringResource(R.string.selinux_status_unknown)
    }
}

@Composable
fun getManagerVersion(): Pair<String, Long> {
    val context = LocalContext.current
    val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
    val versionCode = PackageInfoCompat.getLongVersionCode(packageInfo)
    return Pair(packageInfo.versionName!!, versionCode)
}