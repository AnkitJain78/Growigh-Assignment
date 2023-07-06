package com.example.assignment.presentation.upload

import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.assignment.R
import com.example.assignment.databinding.ActivityUploadBinding
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File

class UploadActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks {
    private lateinit var binding: ActivityUploadBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var activityResultLauncher2: ActivityResultLauncher<Intent>
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_PICK_IMAGE = 2
    private var abPath = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSelect.setOnClickListener {
            showPictureDialog()
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
        register()
        register2()
    }

    private fun hasCameraPermission(): Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)
    }

    private fun hasStoragePermission(): Boolean {
        return EasyPermissions.hasPermissions(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(
            pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> {
                    openGallery()
                }

                1 -> {
                    openCamera()
                }
            }
        }
        pictureDialog.show()
    }

    private fun openCamera() {
        if (hasCameraPermission()) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val photoFile = File.createTempFile(
                "image",
                ".jpg",
                storageDir
            )
            photoFile.also {
                val photoURI =
                    FileProvider.getUriForFile(
                        this,
                        "com.example.assignment.fileprovider",
                        it
                    )
                abPath = photoFile.absolutePath
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                activityResultLauncher.launch(intent)
            }
        }
        else {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_camera),
                REQUEST_IMAGE_CAPTURE,
                Manifest.permission.CAMERA
            )
        }
    }

    private fun openGallery() {
        if (hasStoragePermission()) {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            activityResultLauncher2.launch(intent)
        } else {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.storage),
                REQUEST_PICK_IMAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    private fun register2() {
        activityResultLauncher2 =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                val resultCode = it.resultCode
                val data = it.data
                if (resultCode == RESULT_OK) {
                    val uri = data?.data
                    binding.uploadedImg.setImageURI(uri)
                }
            }
    }

    private fun register() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                val resultCode = it.resultCode
                if (resultCode == RESULT_OK) {
                    val myBitmap = BitmapFactory.decodeFile(abPath)
                    binding.uploadedImg.setImageBitmap(myBitmap)
                }
            }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == REQUEST_PICK_IMAGE)
            openGallery()
        else
            openCamera()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onRationaleAccepted(requestCode: Int) {

    }

    override fun onRationaleDenied(requestCode: Int) {

    }

    override fun finish() {
        val file = File(abPath)
        if (file.exists())
            file.delete()
        super.finish()
    }
}