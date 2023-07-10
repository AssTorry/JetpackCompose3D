package data

import com.squareup.moshi.JsonAdapter
import models.MeshResource
import okio.buffer
import okio.source
import java.io.File


class ResourceManagerImpl(
    private val jsonAdapter: JsonAdapter<MeshResource>
) : ResourceManager {
    override fun getMeshResource(fileName: String): MeshResource {
        val resourceFile = File(javaClass.classLoader.getResource(fileName)!!.path)
        return jsonAdapter.fromJson(resourceFile.source().buffer())!!
    }
}