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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            Spacer(Modifier.height(12.dp + padding.calculateTopPadding()))
            CardView(
                color = Color(0xFFD0E4F8)
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
                        Text(
                            text = "工作中 <LKM>",
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "当前版本：12345",
                            fontSize = 14.sp
                        )
                        Text(
                            text = "超级用户数：123",
                            fontSize = 14.sp
                        )
                        Text(
                            text = "模块数：12",
                            fontSize = 14.sp
                        )
                    }
                }
            }
            CardView {
                val uname = Os.uname()
                val managerVersion = getManagerVersion()
                InfoText(
                    title = "内核版本",
                    content = uname.release
                )
                InfoText(
                    title = "管理器版本",
                    content = "${managerVersion.first} (${managerVersion.second})"
                )
                InfoText(
                    title = "系统指纹",
                    content = Build.FINGERPRINT
                )
                InfoText(
                    title = "SELinux 状态",
                    content = getSELinuxStatus(),
                    bottomPadding = 0.dp
                )
            }
            val uriHandler = LocalUriHandler.current
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
                            title = "支持开发",
                            content = "KernelSU 将保持免费开源，向开发者捐赠以表示支持。",
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
            CardView(
                onClick = {
                    uriHandler.openUri("https://kernelsu.org/guide/what-is-kernelsu.html")
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
                            title = "了解更多",
                            content = "了解如何安装 KernelSU 以及如何开发模块。",
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
            .clip(SmoothRoundedCornerShape(CardDefaults.ConorRadius))
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