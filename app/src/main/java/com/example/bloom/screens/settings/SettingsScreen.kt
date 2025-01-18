package com.example.bloom.screens.settings

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contrast
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bloom.R
import com.example.bloom.Theme
import com.example.bloom.screens.TopBar
import com.example.bloom.ui.theme.BloomTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navigateToChangeEmail: () -> Unit,
    navigateToChangePassword: () -> Unit,
    navigateToIntro: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: SettingsViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState
    val items = listOf(
        SettingsItem(
            Icons.Default.Email,
            "Change Your Email",
            null,
            onClick = navigateToChangeEmail
        ),
        SettingsItem(
            Icons.Default.Security,
            "Change Your Password",
            null,
            onClick = navigateToChangePassword
        ),
        SettingsItem(
            Icons.Default.Share,
            "Invite Your Friends",
            "Share this app with your Friends",
            onClick = { shareInvite(context) }
        ),
        SettingsItem(
            Icons.Default.Contrast,
            "Theme",
            "Change Theme",
            onClick = viewModel::onThemeClick
        )
    )
    Scaffold(
        topBar = {
            TopBar(
                title = "SETTINGS",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) {
        SettingsScreenContent(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            itemsList = items,
            onSignOutClick = navigateToIntro,
        )
        if (uiState.isThemeDialogVisible) {
            ThemeSelectionDialog(
                currentTheme = uiState.currentTheme,
                onDismiss = viewModel::onDismissThemeDialog,
                onThemeSelected = viewModel::onThemeSelected
            )
        }
    }
}

@Composable
fun SettingsScreenContent(
    modifier: Modifier = Modifier,
    itemsList: List<SettingsItem>,
    onSignOutClick: () -> Unit,
) {
    Column(modifier = modifier) {
        itemsList.forEach { item ->
            SettingsItemCard(
                icon = item.icon,
                title = item.title,
                subTitle = item.subTitle,
                onClick = item.onClick
            )
        }
        Button(
            onClick = onSignOutClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp)
        ) {
            Text(text = "Sign Out")
        }
    }
}

@Composable
fun SettingsItemCard(
    icon: ImageVector,
    title: String,
    subTitle: String?,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
            if (subTitle != null) {
                Text(
                    text = subTitle,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


private fun shareInvite(context: Context) {
    val downloadLink = ""
    val invitationMessage = context.getString(R.string.invitation_message, downloadLink)
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, invitationMessage)
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(intent, context.getString(R.string.invite_friends)))
}

@Composable
fun ThemeSelectionDialog(
    currentTheme: Theme,
    onDismiss: () -> Unit,
    onThemeSelected: (Theme) -> Unit
) {

    val options = listOf(Theme.LIGHT, Theme.DARK, Theme.SYSTEM)
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(currentTheme) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Select Theme") },
        text = {
            Column {
                options.forEach { theme ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onOptionSelected(theme) }
                    ) {
                        RadioButton(
                            selected = (theme == selectedOption),
                            onClick = { onOptionSelected(theme) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = theme.toString())
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onThemeSelected(selectedOption)
                    onDismiss()
                }
            ) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    )
}

@Immutable
data class SettingsItem(
    val icon: ImageVector,
    val title: String,
    val subTitle: String? = null,
    val onClick: () -> Unit
)

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    BloomTheme {
        SettingsScreenContent(
            onSignOutClick = {},
            itemsList = listOf(
                SettingsItem(
                    Icons.Default.Email,
                    "Change Your Email",
                    null,
                    onClick = {}
                ),
                SettingsItem(
                    Icons.Default.Security,
                    "Change Your Password",
                    null,
                    onClick = {}
                ),
                SettingsItem(
                    Icons.Default.Share,
                    "Invite Your Friends",
                    "Share this app with your Friends",
                    onClick = {}
                ),
                SettingsItem(
                    Icons.Default.Contrast,
                    "Theme",
                    "Change Theme",
                    onClick = {}
                )
            )
        )
    }
}