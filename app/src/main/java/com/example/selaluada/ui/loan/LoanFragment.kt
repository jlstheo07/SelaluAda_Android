package com.example.selaluada.ui.loan

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.selaluada.R
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

        val spinnerTenor: Spinner = view.findViewById(R.id.spinnertenorpengajuan)
        val btnSubmit: Button = view.findViewById(R.id.btnSubmitLoan)
        val btnUpload: Button = view.findViewById(R.id.btnUploadData)
        val inputPengajuan: EditText = view.findViewById(R.id.CusPengajuan)

        // Tambahkan "Pilih Tenor" sebagai hint
        val tenorOptions = listOf("Pilih Tenor", "3", "6", "12")
        val tenorAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tenorOptions)
        tenorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTenor.adapter = tenorAdapter

        // Format angka ribuan saat mengetik
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

        // Tombol Ajukan Pinjaman
        btnSubmit.setOnClickListener {
            val nominal = inputPengajuan.text.toString().trim()
            val tenor = spinnerTenor.selectedItem.toString()

            if (nominal.isEmpty()) {
                Toast.makeText(requireContext(), "Masukkan jumlah pinjaman.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (tenor == "Pilih Tenor") {
                Toast.makeText(requireContext(), "Silakan pilih tenor.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(requireContext(), "Pinjaman Rp $nominal dengan tenor $tenor bulan diajukan.", Toast.LENGTH_SHORT).show()
        }

        // Tombol Upload Data
        btnUpload.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur upload data belum diimplementasikan.", Toast.LENGTH_SHORT).show()
        }
    }
}
