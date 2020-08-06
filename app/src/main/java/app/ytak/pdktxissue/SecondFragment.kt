package app.ytak.pdktxissue

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import app.ytak.pdktxissue.databinding.FragmentSecondBinding

class SecondFragment : Fragment(R.layout.fragment_second) {

    companion object {
        private const val REQUEST_CAMERA = 1

        fun newInstance() = SecondFragment()
    }

    private val viewModel by activityViewModels<CameraViewModel>()

    private lateinit var binding: FragmentSecondBinding

    private var photoUri: Uri? = null

    private val cameraPermissionsRequester by lazy {
        val uri = FileProviderUtils.createPhotoUri(requireContext()).also { photoUri = it }
            ?: run { return@lazy null }
        cameraPermissionsRequester(uri, REQUEST_CAMERA)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSecondBinding.bind(view)

        binding.retakeButton.setOnClickListener {
            cameraPermissionsRequester?.launch()
        }

        viewModel.pictureUri.observe(viewLifecycleOwner) { pictureUri ->
            binding.previewImage.setImageURI(pictureUri)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {
            viewModel.updatePictureUri(photoUri ?: return)
        }
    }
}
