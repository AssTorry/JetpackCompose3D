package models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MeshResource(
    val vertices: List<Vertex>,
    val edges: List<Edge>,
)