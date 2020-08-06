package app.ytak.pdktxissue

import android.annotation.TargetApi
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException

object FileProviderUtils {

  fun createPhotoUri(context: Context): Uri? {
    val fileName = "IMG_${System.currentTimeMillis()}.jpg"
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
      createPhotoUriApi29(context, fileName)
    } else {
      createPhotoUri(context, fileName)
    }
  }

  private fun createPhotoUri(context: Context, fileName: String): Uri? {
    val storageDir = File(Environment.getExternalStorageDirectory().toURI())
    if (!storageDir.exists()) {
      try {
        storageDir.mkdirs()
      } catch (e: IOException) {
        return null
      }
    }
    val file = File("${storageDir.path}${File.separator}$fileName")
    val authority = "${context.packageName}.provider"
    return FileProvider.getUriForFile(context, authority, file)
  }

  @TargetApi(Build.VERSION_CODES.Q)
  private fun createPhotoUriApi29(context: Context, fileName: String): Uri? {
    return context.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        ContentValues().apply {
          put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
          put(MediaStore.MediaColumns.TITLE, fileName)
          put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        }
    )
  }
}
