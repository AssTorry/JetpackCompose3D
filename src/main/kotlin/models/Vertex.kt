package models

import com.squareup.moshi.JsonClass
import kotlin.math.cos
import kotlin.math.sin

/**
 * Обычная вершинка в 3д
 */
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
fun Vertex.rotate(v: Vertex): Vertex = rotate(cos(v.x), sin(v.x), cos(v.y), sin(v.y), cos(v.z), sin(v.z))

/**
 * Повернуть вершинку
 */
fun Vertex.rotate(cosX: Double, sinX: Double, cosY: Double, sinY: Double, cosZ: Double, sinZ: Double): Vertex {
    val rX = cosY * cosZ * x - sinZ * cosY * y + sinY * z
    val rY = (sinX * sinY * cosZ + sinZ * cosX) * x + (cosX * cosZ - sinX * sinY * sinZ) * y - sinX * cosY * z
    val rZ = (sinX * sinZ - sinY * cosX * cosZ) * x + (sinX * cosZ + sinY * sinZ * cosX) * y + cosX * cosY * z
    return Vertex(rX, rY, rZ)
}

/**
 * Вершинка в центре координат
 */
val VERTEX_CENTER = Vertex(0.0, 0.0, 0.0)