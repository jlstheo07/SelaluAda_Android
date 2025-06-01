package com.example.selaluada.ui.loan

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.selaluada.R
import com.example.selaluada.data.model.CreatePengajuanRequest
import com.example.selaluada.data.network.PengajuanApiClient
import com.example.selaluada.data.service.PengajuanApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*

class LoanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputPengajuan: EditText = view.findViewById(R.id.CusPengajuan)
        val spinnerTenor: Spinner = view.findViewById(R.id.spinnertenorpengajuan)
        val btnSimulasiHitungan: Button = view.findViewById(R.id.btnSimulasiHitungan)
        val btnUpload: Button = view.findViewById(R.id.btnuploadpinjaman)

        val txtTotalPinjaman: TextView = view.findViewById(R.id.txtTotalPinjaman)
        val txtBunga: TextView = view.findViewById(R.id.txtBunga)
        val txtTotalBayar: TextView = view.findViewById(R.id.txtTotalBayar)
        val txtBayarPerBulan: TextView = view.findViewById(R.id.txtBayarPerBulan)
        val resultSection: LinearLayout = view.findViewById(R.id.resultSection)

        // Setup spinner
        val tenorOptions = listOf("Pilih Tenor", "3", "6", "12")
        val tenorAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tenorOptions)
        tenorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTenor.adapter = tenorAdapter

        // Format input nominal
        inputPengajuan.addTextChangedListener(object : TextWatcher {
            private var current = ""

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s.toString() != current) {
                    inputPengajuan.removeTextChangedListener(this)

                    val cleanString = s.toString().replace("[^\\d]".toRegex(), "")
                    if (cleanString.isNotEmpty()) {
                        val parsed = cleanString.toDouble()
                        val formatted = NumberFormat.getNumberInstance(Locale("in", "ID")).format(parsed)

                        current = formatted
                        inputPengajuan.setText(formatted)
                        inputPengajuan.setSelection(formatted.length)
                    }

                    inputPengajuan.addTextChangedListener(this)
                }
            }
        })

        // Simulasi button click
        btnSimulasiHitungan.setOnClickListener {
            val nominalText = inputPengajuan.text.toString().replace("[^\\d]".toRegex(), "")
            val tenorText = spinnerTenor.selectedItem.toString()

            if (nominalText.isEmpty()) {
                Toast.makeText(requireContext(), "Masukkan jumlah pinjaman.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nominal = nominalText.toDouble()
            if (nominal < 500_000) {
                Toast.makeText(requireContext(), "Nominal minimal pengajuan adalah Rp500.000.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (tenorText == "Pilih Tenor") {
                Toast.makeText(requireContext(), "Silakan pilih tenor.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val tenor = tenorText.toInt()
            val bunga = nominal * 0.05
            val totalBayar = nominal + bunga
            val cicilanBulanan = totalBayar / tenor

            val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))

            txtTotalPinjaman.text = "Total Pinjaman: ${formatter.format(nominal)}"
            txtBunga.text = "Bunga (5%): ${formatter.format(bunga)}"
            txtTotalBayar.text = "Total Pembayaran: ${formatter.format(totalBayar)}"
            txtBayarPerBulan.text = "Cicilan per Bulan: ${formatter.format(cicilanBulanan)}"

            resultSection.visibility = View.VISIBLE
        }

        btnUpload.setOnClickListener {
            val nominalText = inputPengajuan.text.toString().replace("[^\\d]".toRegex(), "")
            val tenorText = spinnerTenor.selectedItem.toString()

            if (nominalText.isEmpty()) {
                Toast.makeText(requireContext(), "Masukkan jumlah pinjaman.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nominal = nominalText.toInt()
            if (nominal < 500_000) {
                Toast.makeText(requireContext(), "Nominal minimal pengajuan adalah Rp500.000.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (tenorText == "Pilih Tenor") {
                Toast.makeText(requireContext(), "Silakan pilih tenor.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = CreatePengajuanRequest(
                amount = nominal,
                tenor = tenorText.toInt()
            )

            PengajuanApiClient.getInstance(requireContext()).createPengajuan(request)
                .enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            Toast.makeText(requireContext(), "Pengajuan berhasil dikirim.", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "Gagal mengirim pengajuan: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        }



    }
}
