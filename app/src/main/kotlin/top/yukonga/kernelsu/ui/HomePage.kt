package top.yukonga.kernelsu.ui

import android.os.Build
import android.system.Os
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Link
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import top.yukonga.kernelsu.R
import top.yukonga.kernelsu.ui.component.CardView
import top.yukonga.kernelsu.ui.component.InfoText
import top.yukonga.kernelsu.utils.getManagerVersion
import top.yukonga.kernelsu.utils.getSELinuxStatus
import top.yukonga.miuix.kmp.basic.Icon
import top.yukonga.miuix.kmp.basic.LazyColumn
import top.yukonga.miuix.kmp.basic.MiuixScrollBehavior
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.basic.TopAppBar
import top.yukonga.miuix.kmp.basic.rememberTopAppBarState
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.getWindowSize

@Composable
fun HomePage(
    hazeState: HazeState
) {
    val topAppBarScrollBehavior = MiuixScrollBehavior(rememberTopAppBarState())

    val hazeStyleTopAppBar = HazeStyle(
        blurRadius = 25.dp,
        backgroundColor = MiuixTheme.colorScheme.background,
        tint = HazeTint(
            MiuixTheme.colorScheme.background.copy(0.67f)
        )
    )
    Scaffold(
        topBar = {
            TopAppBar(
                color = Color.Transparent,
                modifier = Modifier.hazeEffect(
                    state = hazeState,
                    style = hazeStyleTopAppBar
                ),
                title = stringResource(R.string.app_name),
                scrollBehavior = topAppBarScrollBehavior
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .hazeSource(state = hazeState)
                .height(getWindowSize().height.dp),
            topAppBarScrollBehavior = topAppBarScrollBehavior
        ) {
            item {
                val uriHandler = LocalUriHandler.current
                Spacer(Modifier.height(12.dp + padding.calculateTopPadding()))
                CardView(
                    color = if (isSystemInDarkTheme()) Color(0xFF081831) else Color(0xFFD0E4F8),
                    onClick = {
                        // TODO
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(34.dp)
                                .padding(start = 6.dp),
                            imageVector = Icons.Rounded.CheckCircle,
                            tint = MiuixTheme.colorScheme.onSurface,
                            contentDescription = null
                        )
                        Column(modifier = Modifier.padding(start = 18.dp)) {
                            val lkmMode = true // TODO
                            val workingMode = when (lkmMode) {
                                true -> " <LKM>"
                                else -> " <GKI>"
                            }
                            val ksuVersion = 12345 // TODO
                            val ksuSuperuserCount = 123 // TODO
                            val ksuModuleCount = 12 // TODO
                            InfoText(
                                title = stringResource(R.string.home_working) + workingMode,
                                content = stringResource(R.string.home_working_version, ksuVersion) + "\n" +
                                        stringResource(R.string.home_superuser_count, ksuSuperuserCount) + "\n" +
                                        stringResource(R.string.home_module_count, ksuModuleCount),
                                bottomPadding = 0.dp
                            )
                        }
                    }
                }
                CardView {
                    val uname = Os.uname()
                    val managerVersion = getManagerVersion()
                    InfoText(
                        title = stringResource(R.string.home_kernel),
                        content = uname.release
                    )
                    InfoText(
                        title = stringResource(R.string.home_manager_version),
                        content = "${managerVersion.first} (${managerVersion.second})"
                    )
                    InfoText(
                        title = stringResource(R.string.home_fingerprint),
                        content = Build.FINGERPRINT
                    )
                    InfoText(
                        title = stringResource(R.string.home_selinux_status),
                        content = getSELinuxStatus(),
                        bottomPadding = 0.dp
                    )
                }
                CardView(
                    onClick = {
                        uriHandler.openUri("https://patreon.com/weishu")
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 6.dp)
                        ) {
                            InfoText(
                                title = stringResource(R.string.home_support_title),
                                content = stringResource(R.string.home_support_content),
                                bottomPadding = 0.dp
                            )
                        }
                        Icon(
                            modifier = Modifier.size(28.dp),
                            imageVector = Icons.Rounded.Link,
                            tint = MiuixTheme.colorScheme.onSurface,
                            contentDescription = null
                        )
                    }
                }
                val learnUrl = stringResource(R.string.home_learn_kernelsu_url)
                CardView(
                    onClick = {
                        uriHandler.openUri(learnUrl)
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 6.dp)
                        ) {
                            InfoText(
                                title = stringResource(R.string.home_learn_kernelsu),
                                content = stringResource(R.string.home_click_to_learn_kernelsu),
                                bottomPadding = 0.dp
                            )
                        }
                        Icon(
                            modifier = Modifier.size(28.dp),
                            imageVector = Icons.Rounded.Link,
                            tint = MiuixTheme.colorScheme.onSurface,
                            contentDescription = null
                        )
                    }
                }
                Spacer(Modifier.height(padding.calculateBottomPadding() + 12.dp))
            }
        }
    }
}
