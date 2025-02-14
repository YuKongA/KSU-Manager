package top.yukonga.kernelsu.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.CardDefaults
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.SmoothRoundedCornerShape

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
            .clip(SmoothRoundedCornerShape(CardDefaults.CornerRadius))
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
