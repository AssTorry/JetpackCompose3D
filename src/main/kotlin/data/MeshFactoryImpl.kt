package data

import models.Mesh
import models.MeshRenderData

class MeshFactoryImpl(
    private val resourceManager: ResourceManager
) : MeshFactory {
    override fun create(fileName: String, data: MeshRenderData): Mesh {
        val resource = resourceManager.getMeshResource(fileName)
        return Mesh(
            data.color,
            data.strokeWidth,
            resource.vertices,
            resource.edges
        )
    }
}