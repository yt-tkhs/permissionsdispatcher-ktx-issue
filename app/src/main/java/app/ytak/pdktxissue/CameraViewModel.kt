package app.ytak.pdktxissue

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CameraViewModel : ViewModel() {

    private val _pictureUri = MutableLiveData<Uri>()
    val pictureUri: LiveData<Uri> get() = _pictureUri

    fun updatePictureUri(uri: Uri) {
        _pictureUri.postValue(uri)
    }
}
