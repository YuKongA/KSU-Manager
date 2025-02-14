package top.yukonga.kernelsu.ui

import android.os.Build
import android.system.Os
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material.icons.rounded.Link
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yukonga.kernelsu.R
import top.yukonga.kernelsu.utils.getManagerVersion
import top.yukonga.kernelsu.utils.getSELinuxStatus
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.CardDefaults
import top.yukonga.miuix.kmp.basic.Icon
import top.yukonga.miuix.kmp.basic.LazyColumn
import top.yukonga.miuix.kmp.basic.ScrollBehavior
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.SmoothRoundedCornerShape
import top.yukonga.miuix.kmp.utils.getWindowSize

@Composable
fun HomeView(
    topAppBarScrollBehavior: ScrollBehavior,
    padding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .height(getWindowSize().height.dp),
        topAppBarScrollBehavior = topAppBarScrollBehavior
    ) {
        item {
            val uriHandler = LocalUriHandler.current
            Spacer(Modifier.height(12.dp + padding.calculateTopPadding()))
            CardView(
                color = Color(0xFFD0E4F8),
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
                        imageVector = Icons.Rounded.CheckCircleOutline,
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
                        Text(
                            text = stringResource(R.string.home_working) + workingMode,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = stringResource(R.string.home_working_version, ksuVersion),
                            fontSize = 14.sp
                        )
                        Text(
                            text = stringResource(R.string.home_superuser_count, ksuSuperuserCount),
                            fontSize = 14.sp
                        )
                        Text(
                            text = stringResource(R.string.home_module_count, ksuModuleCount),
                            fontSize = 14.sp
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

@Composable
fun CardView(
    color: Color = MiuixTheme.colorScheme.surface,
    onClick: (() -> Unit)? = null,
    view: @Composable () -> Unit,
) {
    val modifierThen = if (onClick != null) Modifier.clickable { onClick() } else Modifier
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp)
            .clip(SmoothRoundedCornerShape(CardDefaults.CornerRadius))
            .then(modifierThen),
        color = color,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            view()
        }
    }
}

@Composable
fun InfoText(
    title: String,
    content: String,
    bottomPadding: Dp = 12.dp
) {
    Column {
        Text(
            text = title,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = content,
            fontSize = 15.sp,
            modifier = Modifier.padding(bottom = bottomPadding)
        )
    }
}