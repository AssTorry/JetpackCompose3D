package models

import com.squareup.moshi.JsonClass

/**
 * Ребро
 */
@JsonClass(generateAdapter = true)
class Edge(
    val first: Int,
    val second: Int
)