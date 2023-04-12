package lab8.client.presentation.compose.ui.home.table

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import lab8.client.presentation.compose.ui.i18n.Internationalization
import lab8.entities.vehicle.Vehicle

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LazyItemScope.TableRow(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    var active by remember { mutableStateOf(false) }
    Row(modifier = modifier.background(color = if (active) Color.LightGray else Color.White)
        .onPointerEvent(PointerEventType.Enter) { active = true }
        .onPointerEvent(PointerEventType.Exit) { active = false }, verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.TableDataRow(
    i18n: Internationalization,
    vehicle: Vehicle,
    isDeletable: Boolean,
    isCheckedToDelete: Boolean,
    onChecked: (Boolean) -> Unit,
    onEdit: (() -> Unit)?,
    isNumeric: (Int) -> Boolean
) {
    val headersNumber = vehicle.getAllFields().size
    TableRow(
        Modifier.height(52.dp)
    ) {
        Checkbox(isCheckedToDelete, {
            onChecked(it)
        }, enabled = isDeletable)
        if(onEdit != null) {
            IconButton({ onEdit() }) {
                Icon(Icons.Default.Edit, "edit")
            }
        } else {
            Spacer(Modifier.width(48.dp))
        }
        vehicle.getAllFields(i18n.getLocale()).mapIndexed { colId, it ->
            Box(
                Modifier.weight(1f / headersNumber)
            ) {
                TooltipArea(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                        .align(if (isNumeric(colId)) Alignment.CenterEnd else Alignment.CenterStart),
                    tooltip = {
                        Surface(elevation = 4.dp, shape = RoundedCornerShape(4.dp)) {
                            Text(it, Modifier.padding(8.dp))
                        }
                    }
                ) {
                    Text(
                        it,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = true,
                    )
                }
            }
        }
    }
}