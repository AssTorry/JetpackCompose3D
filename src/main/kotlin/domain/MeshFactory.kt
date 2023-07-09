package domain

import com.squareup.moshi.JsonAdapter
import models.MeshResource

class MeshFactory(
    val jsonAdapter: JsonAdapter<MeshResource>
) {
    fun create(fileName: String) {

    }
}