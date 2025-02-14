package top.yukonga.kernelsu.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.LazyColumn
import top.yukonga.miuix.kmp.basic.ScrollBehavior
import top.yukonga.miuix.kmp.utils.getWindowSize

@Composable
fun SansSerifView(
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
            // TODO
            Spacer(Modifier.height(padding.calculateBottomPadding() + 12.dp))
        }
    }
}

