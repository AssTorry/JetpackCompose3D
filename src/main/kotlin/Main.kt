import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.window.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import data.MeshFactoryImpl
import data.ResourceManagerImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import models.*
import presentation.view.Sliders
import presentation.view.Viewport
import presentation.viewmodel.MainViewModel

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
    val viewModel = remember { createViewModel() }
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize()
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
            Viewport(Modifier.fillMaxSize(), meshList, zoom)
            Sliders(viewModel)
        }
    }
}

private fun createViewModel(): MainViewModel {
    val selectedColor = Color(0xFFFF9B00)

    val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    val jsonAdapter = moshi.adapter(MeshResource::class.java)
    val resourceManager = ResourceManagerImpl(jsonAdapter)
    val factory = MeshFactoryImpl(resourceManager)

    val gridRenderData = MeshRenderData(Color(0xFF4D4D4D), 2f)
    val monkeyRenderData = MeshRenderData(Color.Black, 3f)

    val grid = factory.create("Grid.json", gridRenderData)
    val monkey = factory.create("Monkey.json", monkeyRenderData)

    return MainViewModel(listOf(grid, OY, OZ, OX, monkey))
}

