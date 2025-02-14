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
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import top.yukonga.kernelsu.ui.HomePage
import top.yukonga.kernelsu.ui.ModulesPage
import top.yukonga.kernelsu.ui.SuperusersPage
import top.yukonga.kernelsu.ui.theme.AppTheme
import top.yukonga.miuix.kmp.basic.HorizontalPager
import top.yukonga.miuix.kmp.basic.NavigationBar
import top.yukonga.miuix.kmp.basic.NavigationItem
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.theme.MiuixTheme

@OptIn(FlowPreview::class)
@Composable
fun App() {
    AppTheme {
        val pagerState = rememberPagerState(pageCount = { 3 })
        var targetPage by remember { mutableIntStateOf(pagerState.currentPage) }
        val coroutineScope = rememberCoroutineScope()

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
        val hazeStyle = HazeStyle(
            blurRadius = 25.dp,
            backgroundColor = MiuixTheme.colorScheme.background,
            tint = HazeTint(MiuixTheme.colorScheme.background.copy(0.67f))
        )

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                NavigationBar(
                    color = Color.Transparent,
                    modifier = Modifier.hazeEffect(
                        state = hazeState,
                        style = hazeStyle
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
                    .fillMaxSize(),
                pagerState = pagerState,
                pageContent = { page ->
                    when (page) {
                        0 -> HomePage(hazeState = hazeState)
                        1 -> SuperusersPage(hazeState = hazeState)
                        2 -> ModulesPage(hazeState = hazeState)
                    }
                }
            )
        }
    }
}