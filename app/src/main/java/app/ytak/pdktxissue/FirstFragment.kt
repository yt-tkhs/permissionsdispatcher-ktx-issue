package app.ytak.pdktxissue

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import app.ytak.pdktxissue.databinding.FragmentFirstBinding

class FirstFragment : Fragment(R.layout.fragment_first) {

    companion object {
        private const val REQUEST_CAMERA = 1

        fun newInstance() = FirstFragment()
    }

    private val viewModel by activityViewModels<CameraViewModel>()

    private lateinit var binding: FragmentFirstBinding

    private var photoUri: Uri? = null

    private val cameraPermissionsRequester by lazy {
        val uri = FileProviderUtils.createPhotoUri(requireContext()).also { photoUri = it }
            ?: run { return@lazy null }
        cameraPermissionsRequester(uri, REQUEST_CAMERA)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentFirstBinding.bind(view)

        binding.takePictureButton.setOnClickListener {
            cameraPermissionsRequester?.launch()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {
            Log.d("First", "onActivityResult")
            viewModel.updatePictureUri(photoUri ?: return)
            parentFragmentManager.commit {
                replace(android.R.id.content, SecondFragment.newInstance())
                addToBackStack("second")
            }
        }
    }
}
