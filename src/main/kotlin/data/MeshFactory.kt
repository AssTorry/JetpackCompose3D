package data

import models.Mesh
import models.MeshRenderData

interface MeshFactory {
    fun create(fileName: String, data: MeshRenderData) : Mesh
}