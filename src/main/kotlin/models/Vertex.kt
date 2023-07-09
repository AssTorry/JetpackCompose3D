package models

import androidx.compose.runtime.Immutable
import com.squareup.moshi.JsonClass
import kotlin.math.cos
import kotlin.math.sin

/**
 * Обычная вершинка в 3д
 */
@Immutable
@JsonClass(generateAdapter = true)
data class Vertex(
    val x: Double,
    val y: Double,
    val z: Double,
)

/**
 * Повернуть вершинку
 *
 * @param v три угла в радианах
 */
fun Vertex.rotate(v: Vertex): Vertex {
    val cosA = cos(v.x)
    val sinA = sin(v.x)
    val cosB = cos(v.y)
    val sinB = sin(v.y)
    val cosY = cos(v.z)
    val sinY = sin(v.z)
    val rX = cosB * cosY * x - sinY * cosB * y + sinB * z
    val rY = (sinA * sinB * cosY + sinY * cosA) * x + (cosA * cosY - sinA * sinB * sinY) * y - sinA * cosB * z
    val rZ = (sinA * sinY - sinB * cosA * cosY) * x + (sinA * cosY + sinB * sinY * cosA) * y + cosA * cosB * z
    return Vertex(rX, rY, rZ)
}

/**
 * Вершинка в центре координат
 */
val VERTEX_CENTER = Vertex(0.0, 0.0, 0.0)