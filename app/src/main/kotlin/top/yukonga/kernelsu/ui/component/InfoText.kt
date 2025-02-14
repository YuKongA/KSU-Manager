package top.yukonga.kernelsu.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.theme.MiuixTheme

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
            color = MiuixTheme.colorScheme.onSurface.copy(0.7f),
            modifier = Modifier.padding(bottom = bottomPadding)
        )
    }
}