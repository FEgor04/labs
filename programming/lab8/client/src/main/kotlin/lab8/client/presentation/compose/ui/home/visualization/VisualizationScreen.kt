package lab8.client.presentation.compose.ui.home.visualization

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import kotlinx.coroutines.launch
import lab8.entities.vehicle.Vehicle
import org.koin.core.component.getScopeId
import kotlin.math.absoluteValue

@Composable
fun VisualziationScreen(vehicles: List<Vehicle>, clickHandler: (Int) -> Unit, modifier: Modifier = Modifier) {
    val lightning = remember {
        useResource("кчау-1.jpg", ::loadImageBitmap)
    }

    val angle = remember {
        Animatable(0f)
    }
    LaunchedEffect(angle) {
        launch {
            angle.animateTo(2 * 360f, animationSpec = tween(3000))
        }
    }

    val mappedCars = HashSet<Pair<Int, Offset>>()

    Canvas(
        modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(1f)
            .pointerInput(Unit) {
                detectTapGestures {offset ->
                    val car = mappedCars.find {
                        Rect(it.second, Size(lightning.width.toFloat(), lightning.height.toFloat())).contains(offset)
                    }
                    if(car == null) {
                        return@detectTapGestures
                    }
                    println("Click on car #${car}")
                    clickHandler(car.first)
                }
            }
    ) {
        if (vehicles.isEmpty()) {
            return@Canvas
        }
        val canvasWidth = size.width
        val canvasHeight = size.height
        val neededWidth: Float = 2 * vehicles
            .maxBy { (it.coordinates.x.absoluteValue) }
            .coordinates.x.absoluteValue.toFloat()

        val neededHeight: Float = 2 * vehicles
            .maxBy { (it.coordinates.y?.absoluteValue ?: 0) }
            .coordinates.y?.absoluteValue?.toFloat()!!

        val xMapper: (Float) -> Float = {
            val k = neededWidth / canvasWidth
            (it / k + canvasWidth / 2)
        }
        val yMapper: (Float) -> Float = {
            val k = neededHeight / canvasHeight
            canvasHeight / 2 - it / k
        }

        drawLine(
            Color.Black,
            start = Offset(canvasWidth / 2, 0f),
            end = Offset(canvasWidth / 2, canvasHeight)
        )
        drawLine(
            Color.Black,
            start = Offset(0f, canvasHeight / 2),
            end = Offset(canvasWidth, canvasHeight / 2)
        )

        vehicles.map {
            val x = xMapper(it.coordinates.x.toFloat())
            val y = yMapper(it.coordinates.y?.toFloat() ?: 0f)
            // drawCircle(
            //     it.getColor(),
            //     radius = 20.dp.toPx(),
            //     center = Offset(x = x, y = y),
            // )
            mappedCars.add(Pair(it.id, Offset(x, y)))


            rotate(angle.value, pivot = Offset(x + lightning.width / 2, y + lightning.height / 2)) {
                drawImage(
                    lightning,
                    topLeft = Offset(x, y),
                    colorFilter = ColorFilter.tint(it.getColor(), blendMode = BlendMode.Multiply),
                )
            }
        }
    }
}

@Composable
@Preview
fun VisualizationScreenPreview() {
    VisualziationScreen((1..10).map { Vehicle.generateRandomVehicle() }, {})
}

fun Vehicle.getColor(): Color {
    return Color((50 * this.authorID) % 193, (75 * this.authorID) % 127, (100 * this.authorID) % 101)
}
