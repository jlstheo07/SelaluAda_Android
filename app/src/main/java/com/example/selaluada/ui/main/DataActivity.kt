package com.example.selaluada.ui.main

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.selaluada.R
import com.example.selaluada.data.model.CustomerRequestDTO
import com.example.selaluada.data.network.CustomerApiClient
import com.example.selaluada.data.service.PengajuanApiService
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class DataActivity : AppCompatActivity() {

    private lateinit var imagePreview: ImageView
    private var imageUri: Uri? = null
    private lateinit var takePictureLauncher: ActivityResultLauncher<Uri>
    private lateinit var pickImageLauncher: ActivityResultLauncher<String>
    private var imageFile: File? = null
    private lateinit var editTanggalLahir: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        // Tombol back
        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Spinner Gender
        setupSpinner(R.id.spinnerCreateGender, listOf("Pilih Gender", "Pria", "Wanita"))

        // Spinner Provinsi
        setupSpinner(R.id.spinnerCreateProvinsi, listOf(
            "Pilih Provinsi", "Aceh", "Bali", "Bangka Belitung", "Banten", "Bengkulu", "DI Yogyakarta",
            "DKI Jakarta", "Gorontalo", "Jambi", "Jawa Barat", "Jawa Tengah", "Jawa Timur",
            "Kalimantan Barat", "Kalimantan Selatan", "Kalimantan Tengah", "Kalimantan Timur",
            "Kalimantan Utara", "Kepulauan Riau", "Lampung", "Maluku", "Maluku Utara",
            "Nusa Tenggara Barat", "Nusa Tenggara Timur", "Papua", "Papua Barat", "Papua Barat Daya",
            "Papua Pegunungan", "Papua Selatan", "Papua Tengah", "Riau", "Sulawesi Barat",
            "Sulawesi Selatan", "Sulawesi Tengah", "Sulawesi Tenggara", "Sulawesi Utara",
            "Sumatera Barat", "Sumatera Selatan", "Sumatera Utara"
        ))

        imagePreview = findViewById(R.id.imageCreatePreview)
        editTanggalLahir = findViewById(R.id.CusCreateTanggalLahir)

        takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            val localUri = imageUri
            if (success && localUri != null) {
                imagePreview.setImageURI(localUri)
            }
        }


        pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                imageUri = it
                imagePreview.setImageURI(it)
                imageFile = File(it.path ?: "")
            }
        }

        findViewById<Button>(R.id.btnCreateCamera).setOnClickListener {
            imageFile = createImageFile()
            if (imageFile != null) {
                imageUri = FileProvider.getUriForFile(this, "$packageName.provider", imageFile!!)
                takePictureLauncher.launch(imageUri!!)
            }
        }

        findViewById<Button>(R.id.btnCreateGallery).setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        editTanggalLahir.setOnClickListener { showDatePickerDialog() }

        findViewById<Button>(R.id.btnCreateSave).setOnClickListener { saveCustomerData() }
    }

    private fun setupSpinner(spinnerId: Int, items: List<String>) {
        val spinner = findViewById<Spinner>(spinnerId)
        val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items) {
            override fun isEnabled(position: Int) = position != 0
            override fun getDropDownView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                view.setTextColor(if (position == 0) android.graphics.Color.GRAY else android.graphics.Color.BLACK)
                return view
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(0)
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(this, { _, y, m, d ->
            editTanggalLahir.setText("%04d-%02d-%02d".format(y, m + 1, d))
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imagesDir = File(getExternalFilesDir(null), "images").apply { if (!exists()) mkdirs() }
        return File(imagesDir, "IMG_$timeStamp.jpg")
    }

    private fun saveCustomerData() {
        val nik = findViewById<EditText>(R.id.CusCreateNIK).text.toString()
        val tempatLahir = findViewById<EditText>(R.id.CusCreateTempatLahir).text.toString()
        val tanggalLahir = editTanggalLahir.text.toString()
        val gender = findViewById<Spinner>(R.id.spinnerCreateGender).selectedItem.toString()
        val pekerjaan = findViewById<EditText>(R.id.CusCreatePekerjaan).text.toString()
        val gaji = findViewById<EditText>(R.id.CusCreateGaji).text.toString().toLongOrNull() ?: 0L
        val bank = findViewById<EditText>(R.id.CusCreateBank).text.toString()
        val rekening = findViewById<EditText>(R.id.CusCreateRekening).text.toString()
        val noHp = findViewById<EditText>(R.id.CusCreateTelepon).text.toString()
        val ibuKandung = findViewById<EditText>(R.id.CusCreateIbu).text.toString()
        val alamat = findViewById<EditText>(R.id.CusCreateAlamat).text.toString()
        val provinsi = findViewById<Spinner>(R.id.spinnerCreateProvinsi).selectedItem.toString()

        val customer = CustomerRequestDTO(
            nik, tempatLahir, tanggalLahir, gender, pekerjaan,
            gaji, bank, rekening.toLongOrNull() ?: 0L, noHp, ibuKandung, alamat, provinsi
        )

        // Ubah ke JSON string
        val json = Gson().toJson(customer)
        val dataPart = json.toRequestBody("application/json".toMediaTypeOrNull())

        // Foto KTP sebagai file part
        val fotoPart = imageFile?.let {
            val requestFile = it.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("fotoKtp", it.name, requestFile)
        }

        val PengajuanApiService = CustomerApiClient.getInstance(this)

        PengajuanApiService.registerCustomer(dataPart, fotoPart).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@DataActivity, "Berhasil disimpan", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@DataActivity, "Gagal: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@DataActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

}
