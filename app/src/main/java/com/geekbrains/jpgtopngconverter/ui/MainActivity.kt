package com.geekbrains.jpgtopngconverter.ui

import com.geekbrains.jpgtopngconverter.databinding.ActivityMainBinding
import com.geekbrains.jpgtopngconverter.mvp.view.MainViewImpl

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import android.os.Bundle

import moxy.MvpAppCompatActivity

class MainActivity : MvpAppCompatActivity(), MainViewImpl {

    private var vb: ActivityMainBinding? = null

    companion object {
        const val PERMISSION_REGISTRY_KEY = "permission"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb?.root)
    }

    override fun checkPermissions() {

        val requestPermissionLauncher: ActivityResultLauncher<String> =
            activityResultRegistry.register(PERMISSION_REGISTRY_KEY,
                ActivityResultContracts.RequestPermission())
            {
                shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

        requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}