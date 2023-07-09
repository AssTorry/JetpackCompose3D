package presentation.view

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import models.Mesh

/**
 * Отрисовка всех 3д моделек
 */
@Composable
fun Viewport(
    modifier: Modifier,
    meshList: List<Mesh>,
    zoom: Float,
) = Canvas(modifier) {
    val viewportCenter = center
    val ratio = size.height * zoom / 4 //коэффициент отношения внутренних координат к координатам окна
    meshList.forEach { mesh ->
        // Проецируем 3д вершинки на 2д точки экрана
        val points = Array(mesh.vertices.size) { i ->
            Offset(
                (mesh.vertices[i].x * ratio + viewportCenter.x).toFloat(),
                (mesh.vertices[i].y * ratio + viewportCenter.y).toFloat(),
            )
        }
        // Используем 2д точки для отрисовки всех линий меша
        mesh.edges.forEach { edge ->
            drawLine(
                mesh.color,
                points[edge.first],
                points[edge.second],
                mesh.strokeWidth
            )
        }
    }
}