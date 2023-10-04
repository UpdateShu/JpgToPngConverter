package com.geekbrains.jpgtopngconverter.ui

import com.geekbrains.jpgtopngconverter.databinding.FragmentImgsBinding
import com.geekbrains.jpgtopngconverter.mvp.presenter.ImgsPresenter
import com.geekbrains.jpgtopngconverter.mvp.view.ImgsView
import com.geekbrains.jpgtopngconverter.mvp.view.MainViewImpl

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.app.Activity.RESULT_OK
import android.content.Intent

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.Toast

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import com.geekbrains.jpgtopngconverter.R

import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class ImgsFragment : MvpAppCompatFragment(), ImgsView {

    private var _binding: FragmentImgsBinding? = null
    private val binding
        get() = _binding!!

    val presenter: ImgsPresenter by moxyPresenter {
        ImgsPresenter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentImgsBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    private var openGallery: ActivityResultLauncher<Intent>? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSelectImg.setOnClickListener {
            presenter.imgSelectionListener?.invoke()
        }
        binding.buttonConvert.setOnClickListener {
            val path = presenter.getValidatePath(binding.textPathImagePicked.text)
            if (path.isNullOrEmpty())
                return@setOnClickListener

            (activity as MainViewImpl).checkPermissions()
            setConversionEnabled(false)
            Log.d("MyThread", Thread.currentThread().name)

            presenter.imgConversionListener?.invoke(
                (binding.imagePicked.drawable as BitmapDrawable).bitmap, path)
        }
        openGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                it.data?.data?.let { uri ->
                    binding.imagePicked.setImageURI(uri)
                    binding.textPathImagePicked.text = presenter.getPathFromUri(
                        requireContext().contentResolver, uri) as CharSequence
                }
            }
        }
    }

    override fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        openGallery?.launch(intent)
    }

    override fun setConvertedImage(bmp: Bitmap, path: String) {
        Toast.makeText(requireContext(), "${path} converted to png.", Toast.LENGTH_LONG).show()
        binding.textPathImageConverted.text = path
        binding.imageConverted.background = null
        binding.imageConverted.setImageBitmap(bmp)

        setConversionEnabled(true)
    }

    fun setConversionEnabled(isEnabled: Boolean) {
        binding.buttonSelectImg.isEnabled = isEnabled
        binding.buttonConvert.isEnabled = isEnabled
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}