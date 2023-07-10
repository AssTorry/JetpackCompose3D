package data

import models.MeshResource

interface ResourceManager {
    fun getMeshResource(fileName: String): MeshResource
}