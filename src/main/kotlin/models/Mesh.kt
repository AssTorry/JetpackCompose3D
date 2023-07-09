package models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * 3Д модель для отрисовки в окне
 *
 * @property color цвет меша
 * @property strokeWidth жирность линий меша
 * @property vertices массив вершин меша
 * @property edges массив пар индексов вершин из [vertices] - задает набор линий
 */
@Immutable
data class Mesh(
    val color: Color,
    val strokeWidth: Float,
    val vertices: List<Vertex>,
    val edges: List<Edge>,
)

/**
 * Повернуть меш
 *
 * @param v три угла в радианах
 */
fun Mesh.rotate(v: Vertex) = copy(vertices = vertices.map { it.rotate(v) })

/**
 * Ось ОX на экране
 */
val OX = Mesh(
    Color.Red,
    5f,
    listOf(VERTEX_CENTER, Vertex(0.5, 0.0, 0.0)),
    listOf(Edge(0, 1)),
)

/**
 * Ось ОY на экране
 */
val OY = Mesh(
    Color.Green,
    5f,
    listOf(VERTEX_CENTER, Vertex(0.0, 0.5, 0.0)),
    listOf(Edge(0, 1)),
)

/**
 *  Ось ОZ на экране
 */
val OZ = Mesh(
    Color.Blue,
    5f,
    listOf(VERTEX_CENTER, Vertex(0.0, 0.0, 0.5)),
    listOf(Edge(0, 1)),
)


