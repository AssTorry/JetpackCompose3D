package models

import androidx.compose.ui.graphics.Color
import kotlin.math.cos
import kotlin.math.sin

/**
 * 3Д модель для отрисовки в окне
 *
 * @property color цвет меша
 * @property strokeWidth жирность линий меша
 * @property vertices массив вершин меша
 * @property edges массив пар индексов вершин из [vertices] - задает набор линий
 */
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
fun Mesh.rotate(v: Vertex): Mesh {
    val cosX = cos(v.x)
    val sinX = sin(v.x)
    val cosY = cos(v.y)
    val sinY = sin(v.y)
    val cosZ = cos(v.z)
    val sinZ = sin(v.z)
    return copy(vertices = vertices.map { it.rotate(cosX, sinX, cosY, sinY, cosZ, sinZ) })
}

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


