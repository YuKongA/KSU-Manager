package top.yukonga.kernelsu.ui

import android.os.Build
import android.system.Os
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Link
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import top.yukonga.kernelsu.R
import top.yukonga.kernelsu.ui.component.BasicComponent
import top.yukonga.kernelsu.ui.component.CardView
import top.yukonga.kernelsu.ui.component.InfoText
import top.yukonga.kernelsu.utils.getManagerVersion
import top.yukonga.kernelsu.utils.getSELinuxStatus
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.Icon
import top.yukonga.miuix.kmp.basic.LazyColumn
import top.yukonga.miuix.kmp.basic.MiuixScrollBehavior
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.basic.Switch
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.basic.TopAppBar
import top.yukonga.miuix.kmp.basic.rememberTopAppBarState
import top.yukonga.miuix.kmp.extra.SuperDialogDefaults.insideMargin
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.theme.MiuixTheme.colorScheme
import top.yukonga.miuix.kmp.utils.getWindowSize

@Composable
fun HomePage(
    hazeState: HazeState
) {
    val lkmMode = true // TODO
    val workingMode = when (lkmMode) {
        true -> " <LKM>"
        else -> " <GKI>"
    }
    val ksuVersion = 12345 // TODO
    val ksuSuperuserCount = 123 // TODO
    val ksuModuleCount = 12 // TODO

    val topAppBarScrollBehavior = MiuixScrollBehavior(rememberTopAppBarState())

    val hazeStyleTopAppBar = HazeStyle(
        blurRadius = 25.dp,
        backgroundColor = MiuixTheme.colorScheme.background,
        tint = HazeTint(
            MiuixTheme.colorScheme.background.copy(0.67f)
        )
    )

    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

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

                Spacer(Modifier.height(12.dp + padding.calculateTopPadding()))
                Row(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Card(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .weight(1f),
                        color = if (isSystemInDarkTheme()) Color(0xFF1A3825) else Color(0xFFDFFAE4),
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    Toast.makeText(context.applicationContext, "TODO", Toast.LENGTH_SHORT).show()
                                }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(14.dp)
                                    //.padding(top = 14.dp, start = 14.dp)
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    //textAlign = TextAlign.Center,
                                    text = stringResource(R.string.home_working)+workingMode,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    //color = colorScheme.onSurfaceVariantSummary


                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    stringResource(R.string.home_working_version, ksuVersion),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                )
//                                Spacer(Modifier.height(10.dp))
                            }
                            Box(
                                modifier = Modifier.fillMaxSize().offset(38.dp,45.dp),
                                contentAlignment = Alignment.BottomEnd
                            ){
                                Icon(
                                    modifier = Modifier.size(170.dp),
                                    imageVector = Icons.Outlined.CheckCircle,
                                    tint = Color(0xFF36D167),
                                    contentDescription = null
                                )

                            }

                        }

                    }
                    Column(
                        modifier = Modifier.wrapContentHeight().weight(1f)
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            insideMargin = PaddingValues(14.dp),
                        ) {
                            Column{
                                Text(
                                    text = stringResource(R.string.superuser),
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = colorScheme.onSurfaceVariantSummary


                                )
                                Row (
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.Bottom,
                                ){
                                    Text(
                                        text = ksuSuperuserCount.toString(),
                                        fontSize = 26.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        modifier = Modifier.padding(start = 5.dp, bottom = 3.5.dp),
                                        text = "个",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = colorScheme.onSurfaceVariantSummary
                                    )
                                }

                            }

                        }
                        Spacer(Modifier.height(12.dp))
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            insideMargin = PaddingValues(14.dp),
                        ) {
                            Column{
                                Text(
                                    text = stringResource(R.string.module),
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = colorScheme.onSurfaceVariantSummary


                                )
                                Row (
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.Bottom,
                                ) {

                                    Text(
                                        text = ksuModuleCount.toString(),
                                        fontSize = 26.sp,
                                        fontWeight = FontWeight.Bold,
                                        //color = colorScheme.onSurfaceVariantSummary

                                    )
                                    Text(
                                        modifier = Modifier.padding(start = 5.dp, bottom = 3.5.dp),
                                        text = "个",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = colorScheme.onSurfaceVariantSummary
                                    )
                                }

                            }

                        }
                    }
                }
//                    BasicComponent(
//                        title = ,
//                        summary = stringResource(R.string.home_working_version, ksuVersion) + "\n" +
//                                stringResource(R.string.home_superuser_count, ksuSuperuserCount) + "\n" +
//                                stringResource(R.string.home_module_count, ksuModuleCount),
//                        rightActions = {
//                            Icon(
//                                modifier = Modifier
//                                    .size(40.dp)
//                                    .padding(end = 12.dp),
//                                imageVector = Icons.Rounded.CheckCircle,
//                                tint = MiuixTheme.colorScheme.onSurface,
//                                contentDescription = null
//                            )
//
//                        },
//                        onClick = {
//                            // TODO
//                        },
//                        insideMargin = PaddingValues(18.dp)
//                    )

                CardView {
                    val uname = Os.uname()
                    val managerVersion = getManagerVersion()
                    Column(
                        modifier = Modifier.padding(19.dp)
                    ) {
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
                }
                CardView {
                    BasicComponent(
                        title = stringResource(R.string.home_support_title),
                        summary = stringResource(R.string.home_support_content),
                        rightActions = {
                            Icon(
                                modifier = Modifier.size(28.dp),
                                imageVector = Icons.Rounded.Link,
                                tint = MiuixTheme.colorScheme.onSurface,
                                contentDescription = null
                            )
                        },
                        onClick = {
                            uriHandler.openUri("https://patreon.com/weishu")
                        },
                        insideMargin = PaddingValues(18.dp)
                    )

                }
                val learnUrl = stringResource(R.string.home_learn_kernelsu_url)
                CardView {
                    BasicComponent(
                        title = stringResource(R.string.home_learn_kernelsu),
                        summary = stringResource(R.string.home_click_to_learn_kernelsu),
                        rightActions = {
                            Icon(
                                modifier = Modifier.size(28.dp),
                                imageVector = Icons.Rounded.Link,
                                tint = MiuixTheme.colorScheme.onSurface,
                                contentDescription = null
                            )
                        },
                        onClick = {
                            uriHandler.openUri(learnUrl)
                        },
                        insideMargin = PaddingValues(18.dp)
                    )
                }
                Spacer(Modifier.height(padding.calculateBottomPadding() + 12.dp))
            }
        }
    }
}
