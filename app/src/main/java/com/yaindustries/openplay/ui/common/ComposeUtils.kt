package com.yaindustries.openplay.ui.common

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.yaindustries.openplay.utils.Utilities

@Composable
fun checkOrRequestPermissions(permissions: Array<String>): State<Map<String, Boolean>> {
    val context = LocalContext.current
    val isPermissionAvailable: MutableState<Map<String, Boolean>> =
        remember { mutableStateOf(Utilities.arePermissionsAvailable(context, *permissions)) }

    var permissionGranted = true
    val permissionsMap = isPermissionAvailable.value
    for (permissionEntry in permissionsMap) {
        permissionGranted = permissionEntry.value
        if (!permissionGranted) break
    }

    if (!permissionGranted) {
        val permissionLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                isPermissionAvailable.value = it
            }

        SideEffect {
            permissionLauncher.launch(permissions)
        }
    }

    return isPermissionAvailable
}

@Composable
fun checkPermission(permission: String): Boolean {
    return Utilities.isPermissionAvailable(LocalContext.current, permission)
}