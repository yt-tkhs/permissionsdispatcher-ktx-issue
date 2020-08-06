package app.ytak.pdktxissue

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest

private const val TAG = "cameraPermissions"

fun Fragment.cameraPermissionsRequester(uri: Uri, requestCode: Int) =
    constructPermissionsRequest(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        onShowRationale = ::onCameraOrWriteExternalStorageShowRationale,
        onPermissionDenied = ::onCameraOrWriteExternalStorageDenied,
        onNeverAskAgain = ::onCameraOrWriteExternalStorageNeverAskAgain
    ) {
        Log.d(TAG, "requiresPermission")
        startActivityForResult(
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, uri)
                addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            },
            requestCode
        )
    }

private fun onCameraOrWriteExternalStorageDenied() {
    Log.d(TAG, "onCameraOrWriteExternalStorageDenied")
}

private fun onCameraOrWriteExternalStorageShowRationale(request: PermissionRequest) {
    request.proceed()
}

private fun onCameraOrWriteExternalStorageNeverAskAgain() {
    Log.d(TAG, "onCameraOrWriteExternalStorageNeverAskAgain")
}
