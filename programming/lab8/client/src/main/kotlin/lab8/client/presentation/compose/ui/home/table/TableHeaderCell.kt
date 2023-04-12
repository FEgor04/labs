package lab8.client.presentation.compose.ui.home.table

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.PointerMatcher
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerButton
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import lab8.client.presentation.compose.ui.home.filters.date.DateFilterDialogContent
import lab8.client.presentation.compose.ui.home.filters.enum.EnumFilterDialogContent
import lab8.client.presentation.compose.ui.home.filters.number.NumberFilterDialogContent
import lab8.client.presentation.compose.ui.home.filters.string.StringRegexFilterDialogContent
import lab8.client.presentation.compose.ui.i18n.Internationalization
import lab8.entities.vehicle.FuelType
import lab8.entities.vehicle.Vehicle
import lab8.entities.vehicle.VehicleType

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RowScope.TableHeaderCell(
    internationalization: Internationalization,
    text: String,
    weight: Float,
    sortStatus: HeaderSortStatus,
    isNumeric: Boolean,
    colNumber: Number,
    setFilterPredicate: ((Vehicle) -> Boolean) -> Unit,
    onClick: () -> Unit
) {
    var showFilterDialog by remember { mutableStateOf(false) }
    if (showFilterDialog) {
        Dialog(
            onCloseRequest = { showFilterDialog = false },
            title = "Filter",
        ) {
            when (colNumber) {
                0 -> {
                    NumberFilterDialogContent(
                        internationalization.numberFilterDialog,
                        { l, r ->
                            println("Predicate on ID in $l..$r")
                            setFilterPredicate {
                                it.id.toFloat() in l..r
                            }
                        },
                        { setFilterPredicate { true } })
                }

                1 -> {
                    StringRegexFilterDialogContent(
                        internationalization.stringDialog,
                        { reg ->
                            setFilterPredicate {
                                it.name.matches(Regex(reg))
                            }
                        },
                        { setFilterPredicate { true } }
                    )
                }

                2 -> {
                    NumberFilterDialogContent(
                        internationalization.numberFilterDialog, { l, r ->
                            setFilterPredicate {
                                it.coordinates.getLength() in l.toDouble()..r.toDouble()
                            }
                        },
                        {
                            setFilterPredicate { true }
                        }
                    )
                }


                3 -> {
                    DateFilterDialogContent(internationalization.dateFilterDialog, { l, r ->
                        setFilterPredicate {
                            l.isBefore(it.creationDate) && it.creationDate.isBefore(r)
                        }
                    }, { setFilterPredicate { true } })
                }

                4 -> {
                    NumberFilterDialogContent(
                        internationalization.numberFilterDialog,
                        { l, r ->
                            println("Predicate on ID in $l..$r")
                            setFilterPredicate {
                                it.enginePower.toFloat() in l..r
                            }
                        },
                        { setFilterPredicate { true } })
                }

                5 -> {
                    EnumFilterDialogContent<VehicleType>(internationalization.enumFilterDialog,
                        true, {
                            setFilterPredicate { vehicle ->
                                vehicle.type in it
                            }
                        }, { setFilterPredicate { true } })
                }

                6 -> {
                    EnumFilterDialogContent<FuelType>(internationalization.enumFilterDialog, false, {
                        setFilterPredicate { vehicle ->
                            vehicle.fuelType in it
                        }
                    }, { setFilterPredicate { true } })
                }

                7 -> {
                    NumberFilterDialogContent(internationalization.numberFilterDialog, { l, r ->
                        println("Predicate on ID in $l..$r")
                        setFilterPredicate {
                            it.authorID.toFloat() in l..r
                        }
                    }, { setFilterPredicate { true } })
                }
            }
        }
    }
    Box(
        modifier = Modifier.weight(weight).onClick(matcher = PointerMatcher.Primary, onClick = onClick)
            .onClick(matcher = PointerMatcher.mouse(PointerButton.Secondary)) { showFilterDialog = true }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)

        ) {
            if (isNumeric) {
                Spacer(Modifier.weight(1f))
                TableHeaderSortIcon(sortStatus)
            }
            Text(
                text,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.onClick(true, onClick = onClick),
                fontWeight = FontWeight.Bold,
            )
            if (!isNumeric) {
                TableHeaderSortIcon(sortStatus)
            }
        }
    }
}


@Composable
fun TableHeaderSortIcon(sortStatus: HeaderSortStatus) {
    when (sortStatus) {
        HeaderSortStatus.ASCENDING -> {
            Icon(Icons.Default.KeyboardArrowUp, "arrow down")
        }

        HeaderSortStatus.DESCENDING -> {
            Icon(Icons.Default.KeyboardArrowDown, "arrow up")
        }

        else -> {
            Box(modifier = Modifier.requiredSize(24.dp))
        }
    }
}

fun getHeaderSortStatus(colId: Int, sortKey: Int, asc: Boolean): HeaderSortStatus {
    return when (colId) {
        sortKey -> {
            if (asc) {
                HeaderSortStatus.ASCENDING
            } else {
                HeaderSortStatus.DESCENDING
            }
        }

        else -> {
            HeaderSortStatus.NOT_SORTED
        }
    }
}

enum class HeaderSortStatus {
    NOT_SORTED, ASCENDING, DESCENDING
}
