package top.yukonga.kernelsu.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import top.yukonga.kernelsu.R
import top.yukonga.kernelsu.ui.component.CardView
import top.yukonga.miuix.kmp.basic.BasicComponent
import top.yukonga.miuix.kmp.basic.MiuixScrollBehavior
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.basic.TopAppBar
import top.yukonga.miuix.kmp.utils.getWindowSize
import top.yukonga.miuix.kmp.utils.overScrollVertical

@Composable
fun SuperusersPage(
    hazeState: HazeState,
    hazeStyle: HazeStyle
) {
    val topAppBarScrollBehavior = MiuixScrollBehavior()

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
        LazyColumn(
            modifier = Modifier
                .hazeSource(state = hazeState)
                .height(getWindowSize().height.dp)
                .overScrollVertical()
                .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
        ) {
            item {
                Spacer(Modifier.height(12.dp + padding.calculateTopPadding()))
                // TODO
                CardView {
                    BasicComponent(
                        title = "TODO",
                        summary = "TODO TODO TODO",
                        insideMargin = PaddingValues(18.dp)
                    )
                }
                Spacer(Modifier.height(padding.calculateBottomPadding() + 12.dp))
            }
        }
    }
}

