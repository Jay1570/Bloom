package com.example.bloom.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bloom.ui.theme.orange
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermissionDialog(
    permission: String,
    title: String,
) {
    val permissionState =
        rememberPermissionState(permission = permission)

    if (!permissionState.status.isGranted) {
        if (permissionState.status.shouldShowRationale) RationaleDialog(
            title = title,
            onRequestPermission = { permissionState.launchPermissionRequest() })
        else PermissionDialog(
            title = title,
            onRequestPermission = { permissionState.launchPermissionRequest() })
    }
}

@Composable
fun RationaleDialog(title: String, onRequestPermission: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(true) }

    if (showWarningDialog) {
        AlertDialog(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            title = { Text(text = title) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showWarningDialog = false
                        onRequestPermission()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 8.dp, 16.dp, 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = orange,
                        contentColor = Color.White
                    )
                ) { Text(text = "ok") }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}

@Composable
fun PermissionDialog(title: String, onRequestPermission: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(true) }

    if (showWarningDialog) {
        AlertDialog(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            title = { Text(text = title) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showWarningDialog = false
                        onRequestPermission()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 8.dp, 16.dp, 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = orange,
                        contentColor = Color.White
                    )
                ) { Text(text = "ok") }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}