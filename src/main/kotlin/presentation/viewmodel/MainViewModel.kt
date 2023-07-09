package presentation.viewmodel

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import models.Mesh
import models.Vertex
import models.rotate

class MainViewModel(
    private val meshList: List<Mesh>
) {
    private var angle = Vertex(0.0, 0.0, 0.0)

    private val _meshListState = mutableStateOf(meshList.rotate(angle))
    private val _sensitivity = mutableStateOf(START_SENSITIVITY)
    private val _zoomState = mutableStateOf(START_ZOOM)

    val meshListState: State<List<Mesh>> = _meshListState
    val sensitivityState: State<Float> = _sensitivity
    val zoomState: State<Float> = _zoomState

    fun onDrag(positionChange: Offset) {
        angle = Vertex(
            (angle.x - positionChange.y / _sensitivity.value) % 360,
            (angle.y + positionChange.x / _sensitivity.value) % 360,
            angle.z
        )
        _meshListState.value = meshList.rotate(angle)
    }

    fun setSensitivity(value: Float) {
        _sensitivity.value = value
    }

    fun setZoom(value: Float) {
        _zoomState.value = value
    }

    private fun List<Mesh>.rotate(angle: Vertex): List<Mesh> = map {
        it.rotate(
            Vertex(
                Math.toRadians(angle.x),
                Math.toRadians(angle.y),
                Math.toRadians(angle.z)
            )
        )
    }

    private companion object {
        const val START_SENSITIVITY = 12f
        const val START_ZOOM = 1f
    }
}