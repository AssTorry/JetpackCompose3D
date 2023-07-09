import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import presentation.view.Viewport
import presentation.viewmodel.MainViewModel
import models.*
import okio.buffer
import okio.source
import java.io.File


val moshi: Moshi = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory())
    .build()
val jsonAdapter: JsonAdapter<MeshResource> = moshi.adapter(MeshResource::class.java)
val gridResource =
    jsonAdapter.fromJson(File("/Users/20161794/IdeaProjects/Cube/src/main/resources/Grid.json").source().buffer())!!
val monkeyResource =
    jsonAdapter.fromJson(File("/Users/20161794/IdeaProjects/Cube/src/main/resources/Sphere.json").source().buffer())!!
val grid = Mesh(
    Color(0xFF4D4D4D),
    2f,
    gridResource.vertices,
    gridResource.edges
)

val selectedColor = Color(0xFFFF9B00)

val monkey = Mesh(
    Color.Black,
    3f,
    monkeyResource.vertices,
    monkeyResource.edges
)

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(
            placement = WindowPlacement.Fullscreen,
            position = WindowPosition.Aligned(Alignment.Center),
        )
    ) {
        App()
    }
}

@Composable
fun App() {
    val viewModel = remember { MainViewModel(listOf(grid, OY, OZ, OX, monkey)) }
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF3C3C3C))
                .pointerInput(Unit) {
                    coroutineScope {
                        while (true) {
                            awaitPointerEventScope {
                                drag(awaitFirstDown().id) { change ->
                                    launch(Dispatchers.Main) {
                                        viewModel.onDrag(change.positionChange())
                                    }
                                }
                            }
                        }
                    }
                }
        ) {
            val meshList by remember { viewModel.meshListState }
            val zoom by remember { viewModel.zoomState }
            val sensitivity by remember { viewModel.sensitivityState }

            Viewport(Modifier.fillMaxSize(), meshList, zoom)
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Slider(
                        modifier = Modifier.width(width = 256.dp),
                        value = sensitivity,
                        onValueChange = { viewModel.setSensitivity(it) },
                        valueRange = 1f..64f,
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF181818),
                            activeTrackColor = Color(0xFF2b2b2b),
                            inactiveTrackColor = Color(0xFF545454)
                        )
                    )
                    Text(
                        text = "sensitivity",
                        modifier = Modifier.padding(start = 8.dp),
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 12.sp
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Slider(
                        modifier = Modifier.width(width = 256.dp),
                        value = zoom,
                        onValueChange = { viewModel.setZoom(it) },
                        valueRange = 0f..2f,
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF181818),
                            activeTrackColor = Color(0xFF2b2b2b),
                            inactiveTrackColor = Color(0xFF545454)
                        )
                    )
                    Text(
                        text = "zoom",
                        modifier = Modifier.padding(start = 8.dp),
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

