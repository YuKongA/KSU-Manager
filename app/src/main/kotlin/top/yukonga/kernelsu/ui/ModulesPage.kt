package top.yukonga.kernelsu.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
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
import top.yukonga.kernelsu.R
import top.yukonga.kernelsu.ui.component.CardView
import top.yukonga.kernelsu.ui.component.InfoText
import top.yukonga.miuix.kmp.basic.LazyColumn
import top.yukonga.miuix.kmp.basic.MiuixScrollBehavior
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.basic.TopAppBar
import top.yukonga.miuix.kmp.basic.rememberTopAppBarState
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.getWindowSize

@Composable
fun ModulesPage(
    hazeState: HazeState
) {
    val topAppBarScrollBehavior = MiuixScrollBehavior(rememberTopAppBarState())

    val hazeStyleTopAppBar = HazeStyle(
        blurRadius = 25.dp,
        backgroundColor = if (topAppBarScrollBehavior.state.heightOffset > -1) Color.Transparent else MiuixTheme.colorScheme.background,
        tint = HazeTint(
            MiuixTheme.colorScheme.background.copy(
                if (topAppBarScrollBehavior.state.heightOffset > -1) 1f
                else lerp(1f, 0.67f, (topAppBarScrollBehavior.state.heightOffset + 1) / -143f)
            )
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
                title = stringResource(R.string.module),
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
                // TODO
                CardView {
                    InfoText(
                        title = "TODO",
                        content = "TODO TODO TODO",
                        bottomPadding = 0.dp
                    )
                }
                Spacer(Modifier.height(padding.calculateBottomPadding() + 12.dp))
            }
        }
    }
}
