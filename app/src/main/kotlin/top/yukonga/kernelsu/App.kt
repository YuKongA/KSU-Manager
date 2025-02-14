package top.yukonga.kernelsu

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Apps
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Security
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import top.yukonga.kernelsu.ui.HomeView
import top.yukonga.kernelsu.ui.SansSerifView
import top.yukonga.kernelsu.ui.SerifView
import top.yukonga.kernelsu.ui.theme.AppTheme
import top.yukonga.miuix.kmp.basic.HorizontalPager
import top.yukonga.miuix.kmp.basic.MiuixScrollBehavior
import top.yukonga.miuix.kmp.basic.NavigationBar
import top.yukonga.miuix.kmp.basic.NavigationItem
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.basic.TopAppBar
import top.yukonga.miuix.kmp.basic.rememberTopAppBarState
import top.yukonga.miuix.kmp.theme.MiuixTheme

@OptIn(FlowPreview::class)
@Composable
fun App() {
    AppTheme {
        val topAppBarScrollBehavior0 = MiuixScrollBehavior(rememberTopAppBarState())
        val topAppBarScrollBehavior1 = MiuixScrollBehavior(rememberTopAppBarState())
        val topAppBarScrollBehavior2 = MiuixScrollBehavior(rememberTopAppBarState())

        val topAppBarScrollBehaviorList = listOf(
            topAppBarScrollBehavior0,
            topAppBarScrollBehavior1,
            topAppBarScrollBehavior2,
        )

        val pagerState = rememberPagerState(pageCount = { 3 })
        var targetPage by remember { mutableIntStateOf(pagerState.currentPage) }
        val coroutineScope = rememberCoroutineScope()

        val currentScrollBehavior = when (pagerState.currentPage) {
            0 -> topAppBarScrollBehaviorList[0]
            1 -> topAppBarScrollBehaviorList[1]
            else -> topAppBarScrollBehaviorList[2]
        }

        val navigationItems = listOf(
            NavigationItem(
                stringResource(R.string.home),
                Icons.Rounded.Home
            ),
            NavigationItem(
                stringResource(R.string.superuser),
                Icons.Rounded.Security
            ),
            NavigationItem(
                stringResource(R.string.module),
                Icons.Rounded.Apps
            )
        )

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.debounce(150).collectLatest { page ->
                targetPage = page
            }
        }

        val hazeState = remember { HazeState() }

        val hazeStyleTopAppBar = HazeStyle(
            blurRadius = 25.dp,
            backgroundColor = if (currentScrollBehavior.state.heightOffset > -1) Color.Transparent else MiuixTheme.colorScheme.background,
            tint = HazeTint(
                MiuixTheme.colorScheme.background.copy(
                    if (currentScrollBehavior.state.heightOffset > -1) 1f
                    else lerp(1f, 0.67f, (currentScrollBehavior.state.heightOffset + 1) / -143f)
                )
            )
        )

        val hazeStyleNavigationBar = HazeStyle(
            blurRadius = 25.dp,
            backgroundColor = MiuixTheme.colorScheme.background,
            tint = HazeTint(MiuixTheme.colorScheme.background.copy(0.67f))
        )

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    color = Color.Transparent,
                    modifier = Modifier.hazeEffect(
                        state = hazeState,
                        style = hazeStyleTopAppBar
                    ),
                    title = stringResource(R.string.app_name),
                    scrollBehavior = currentScrollBehavior
                )
            },
            bottomBar = {
                NavigationBar(
                    color = Color.Transparent,
                    modifier = Modifier.hazeEffect(
                        state = hazeState,
                        style = hazeStyleNavigationBar
                    ),
                    items = navigationItems,
                    selected = targetPage,
                    onClick = { index ->
                        targetPage = index
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        ) { padding ->
            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .hazeSource(state = hazeState),
                pagerState = pagerState,
                pageContent = { page ->
                    when (page) {
                        0 -> HomeView(topAppBarScrollBehaviorList[0], padding)
                        1 -> SansSerifView(topAppBarScrollBehaviorList[1], padding)
                        2 -> SerifView(topAppBarScrollBehaviorList[2], padding)
                    }
                }
            )
        }
    }
}