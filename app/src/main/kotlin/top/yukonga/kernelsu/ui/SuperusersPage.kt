package top.yukonga.kernelsu.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import kotlinx.coroutines.delay
import top.yukonga.kernelsu.R
import top.yukonga.kernelsu.ui.component.CardView
import top.yukonga.miuix.kmp.basic.BasicComponent
import top.yukonga.miuix.kmp.basic.MiuixScrollBehavior
import top.yukonga.miuix.kmp.basic.PullToRefresh
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.basic.TopAppBar
import top.yukonga.miuix.kmp.basic.rememberPullToRefreshState
import top.yukonga.miuix.kmp.utils.getWindowSize
import top.yukonga.miuix.kmp.utils.overScrollVertical

@Composable
fun SuperusersPage(
    hazeState: HazeState,
    hazeStyle: HazeStyle,
    paddingBottom: PaddingValues,
) {
    val topAppBarScrollBehavior = MiuixScrollBehavior()
    val pullToRefreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }
    var ii = remember { mutableIntStateOf(1) }

    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            isRefreshing = true
            delay(300)
            pullToRefreshState.completeRefreshing {
                isRefreshing = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                color = Color.Transparent,
                modifier = Modifier.hazeEffect(state = hazeState, style = hazeStyle),
                title = stringResource(R.string.superuser),
                scrollBehavior = topAppBarScrollBehavior
            )
        },
    ) { padding ->
        PullToRefresh(
            pullToRefreshState = pullToRefreshState,
            onRefresh = { ii.intValue++ },
            contentPadding = PaddingValues(top = padding.calculateTopPadding())
        ) {
            LazyColumn(
                modifier = Modifier
                    .hazeSource(state = hazeState)
                    .height(getWindowSize().height.dp)
                    .overScrollVertical()
                    .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
                contentPadding = PaddingValues(
                    top = padding.calculateTopPadding() + 12.dp,
                    bottom = paddingBottom.calculateBottomPadding()
                ),
                overscrollEffect = null
            ) {
                item {
                    // TODO
                    for (i in 0 until ii.intValue) {
                        CardView {
                            BasicComponent(
                                title = "App ${i + 1}",
                                summary = "TODO"
                            )
                        }
                    }
                }
            }
        }
    }
}

