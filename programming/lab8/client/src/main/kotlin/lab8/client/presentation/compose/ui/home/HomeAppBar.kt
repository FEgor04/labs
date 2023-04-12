package lab8.client.presentation.compose.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import lab8.client.presentation.compose.ui.i18n.Internationalization
import lab8.client.presentation.compose.ui.i18n.InternationalizationScreen
import lab8.entities.user.User
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeAppBar(
    localization: Internationalization.HomeScreenAppBar,
    supportedLocales: Iterable<Locale>,
    isDeleteEnabled: Boolean,
    currentUser: User,
    onLogout: () -> Unit,
    onDelete: () -> Unit,
    onRefresh: () -> Unit,
    onAddButton: () -> Unit,
    onRemoveLowerButton: () -> Unit,
    onRemoveGreaterButton: () -> Unit,
    onReplaceIfLowerButton: () -> Unit,
    onVisualizeButton: () -> Unit,
    changeLocale: (Locale) -> Unit
) {
    val navigator = LocalNavigator.currentOrThrow
    val chipColors = ChipDefaults.chipColors(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary
    )

    TopAppBar(
        backgroundColor = Color.White,
    ) {
        Text(
            "${currentUser.name}#${currentUser.id}",
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        IconButton({ onLogout() }) {
            Icon(Icons.Default.Logout, "logout")
        }

        Chip(
            { onDelete() },
            enabled = isDeleteEnabled,
            modifier = Modifier.padding(horizontal = 16.dp),
            colors = ChipDefaults.chipColors(
                backgroundColor = MaterialTheme.colors.error,
                leadingIconContentColor = MaterialTheme.colors.onError
            )
        ) {
            Icon(Icons.Default.Delete, localization.deleteButton, tint = MaterialTheme.colors.onError)
            Text(localization.deleteButton, style = TextStyle.Default.copy(color = MaterialTheme.colors.onError))
        }

        // Spacer(Modifier.width(16.dp))
        // Divider(Modifier.width(1.dp).fillMaxHeight(0.8f))
        // Spacer(Modifier.width(16.dp))

        Chip(
            { onRefresh() },
            modifier = Modifier.padding(horizontal = 16.dp),
            colors = chipColors
        ) {
            Icon(Icons.Default.Refresh, localization.refreshButton)
            Text(localization.refreshButton)
        }

        Chip(
            { onAddButton() },
            modifier = Modifier.padding(horizontal = 16.dp),
            colors = chipColors
        ) {
            Icon(Icons.Default.Add, localization.addButton)
            Text(localization.addButton)
        }

        Chip(
            { onRemoveLowerButton() },
            modifier = Modifier.padding(horizontal = 16.dp),
            colors = chipColors
        ) {
            Icon(Icons.Default.Delete, localization.removeLowerButton)
            Text(localization.removeLowerButton)
        }

        Chip(
            { onRemoveGreaterButton() },
            modifier = Modifier.padding(horizontal = 16.dp),
            colors = chipColors
        ) {
            Icon(Icons.Default.Delete, localization.removeGreaterButton)
            Text(localization.removeGreaterButton)
        }

        Chip(
            { onReplaceIfLowerButton() },
            modifier = Modifier.padding(horizontal = 16.dp),
            colors = chipColors
        ) {
            Icon(Icons.Default.FindReplace, localization.replaceIfLowerButton)
            Text(localization.replaceIfLowerButton)
        }
        Chip(
            { onVisualizeButton() },
            modifier = Modifier.padding(horizontal = 16.dp),
            colors = chipColors
        ) {
            Icon(Icons.Default.Preview, localization.visualizeButton)
            Text(localization.visualizeButton)
        }


        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                navigator.push(InternationalizationScreen(supportedLocales, changeLocale))
            },
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(localization.changeLanguage)
        }
    }
}