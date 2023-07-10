package presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.viewmodel.MainViewModel

@Composable
fun Sliders(viewModel: MainViewModel) = Column {
    val sensitivity by remember { viewModel.sensitivityState }
    val zoom by remember { viewModel.zoomState }
    SliderWithText(
        sensitivity,
        { viewModel.setSensitivity(it) },
        1f..64f,
        "sensitivity"
    )
    SliderWithText(
        zoom,
        { viewModel.setZoom(it) },
        0f..2f,
        "zoom"
    )
}

@Composable
fun SliderWithText(
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    text : String
) = Row(verticalAlignment = Alignment.CenterVertically) {
    Slider(
        modifier = Modifier.width(width = 256.dp),
        value = value,
        onValueChange = onValueChange,
        valueRange = valueRange,
        colors = SliderDefaults.colors(
            thumbColor = Color(0xFF181818),
            activeTrackColor = Color(0xFF2b2b2b),
            inactiveTrackColor = Color(0xFF545454)
        )
    )
    Text(
        text = text,
        modifier = Modifier.padding(start = 8.dp),
        color = Color.White.copy(alpha = 0.6f),
        fontSize = 12.sp
    )
}
