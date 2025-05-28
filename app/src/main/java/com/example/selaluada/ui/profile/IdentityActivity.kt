package com.example.selaluada.ui.profile

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.selaluada.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class IdentityActivity : AppCompatActivity() {

    private lateinit var imageUri: Uri
    private lateinit var imagePreview: ImageView

    private lateinit var takePictureLauncher: ActivityResultLauncher<Uri>
    private lateinit var pickImageLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_identity)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // Setup spinner
        val spinnerGender: Spinner = findViewById(R.id.spinnerGender)
        val spinnerProvinsi: Spinner = findViewById(R.id.spinnerProvinsi)
        val genders = listOf("Pria", "Wanita")
        val provinsi = listOf("Aceh", "Bali", "Bangka Belitung", "Banten", "Bengkulu", "DI Yogyakarta",
            "DKI Jakarta", "Gorontalo", "Jambi", "Jawa Barat", "Jawa Tengah", "Jawa Timur",
            "Kalimantan Barat", "Kalimantan Selatan", "Kalimantan Tengah", "Kalimantan Timur",
            "Kalimantan Utara", "Kepulauan Riau", "Lampung", "Maluku", "Maluku Utara",
            "Nusa Tenggara Barat", "Nusa Tenggara Timur", "Papua", "Papua Barat", "Papua Barat Daya",
            "Papua Pegunungan", "Papua Selatan", "Papua Tengah", "Riau", "Sulawesi Barat",
            "Sulawesi Selatan", "Sulawesi Tengah", "Sulawesi Tenggara", "Sulawesi Utara",
            "Sumatera Barat", "Sumatera Selatan", "Sumatera Utara")

        spinnerGender.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
        spinnerProvinsi.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, provinsi)


        imagePreview = findViewById(R.id.imagePreview)

        // Register launcher
        takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) imagePreview.setImageURI(imageUri)
        }

        pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let { imagePreview.setImageURI(it) }
        }

        findViewById<Button>(R.id.btnCamera).setOnClickListener {
            imageUri = getImageUri()
            takePictureLauncher.launch(imageUri)
        }

        findViewById<Button>(R.id.btnGallery).setOnClickListener {
            pickImageLauncher.launch("image/*")
        }
    }

    private fun getImageUri(): Uri {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val file = File(getExternalFilesDir(null), "IMG_$timeStamp.jpg")
        return FileProvider.getUriForFile(this, "${packageName}.provider", file)
    }
}
