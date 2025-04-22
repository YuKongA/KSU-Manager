package top.yukonga.kernelsu

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Apps
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Security
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import top.yukonga.kernelsu.ui.HomePage
import top.yukonga.kernelsu.ui.ModulesPage
import top.yukonga.kernelsu.ui.SuperusersPage
import top.yukonga.kernelsu.ui.theme.AppTheme
import top.yukonga.miuix.kmp.basic.NavigationBar
import top.yukonga.miuix.kmp.basic.NavigationItem
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.theme.MiuixTheme

@OptIn(FlowPreview::class)
@Composable
fun App() {
    AppTheme {
        val pagerState = rememberPagerState(pageCount = { 3 })
        val coroutineScope = rememberCoroutineScope()
        val selectedPage by remember { derivedStateOf { pagerState.currentPage } }

        val home = stringResource(R.string.home)
        val superuser = stringResource(R.string.superuser)
        val module = stringResource(R.string.module)

        val navigationItems = remember {
            listOf(
                NavigationItem(home, Icons.Rounded.Home),
                NavigationItem(superuser, Icons.Rounded.Security),
                NavigationItem(module, Icons.Rounded.Apps)
            )
        }

        val hazeState = remember { HazeState() }
        val hazeStyle = HazeStyle(
            blurRadius = 25.dp,
            noiseFactor = 0f,
            backgroundColor = MiuixTheme.colorScheme.background,
            tint = HazeTint(MiuixTheme.colorScheme.background.copy(0.67f))
        )

        Scaffold(
            bottomBar = {
                NavigationBar(
                    color = Color.Transparent,
                    modifier = Modifier.hazeEffect(state = hazeState, style = hazeStyle),
                    items = navigationItems,
                    selected = selectedPage,
                    onClick = { index ->
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        ) { padding ->
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false,
                pageContent = { page ->
                    when (page) {
                        0 -> HomePage(hazeState = hazeState, hazeStyle = hazeStyle)
                        1 -> SuperusersPage(hazeState = hazeState, hazeStyle = hazeStyle)
                        2 -> ModulesPage(hazeState = hazeState, hazeStyle = hazeStyle)
                    }
                }
            )
        }
    }
}