package com.example.selaluada.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.selaluada.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tambahkan listener ke fitur
        view.findViewById<LinearLayout>(R.id.ajukanpinjaman)?.setOnClickListener {
            // Navigasi ke fragment Ajukan Pinjaman
        }

        view.findViewById<LinearLayout>(R.id.tagihanpinjaman)?.setOnClickListener {
            // Navigasi ke fragment Tagihan
        }

        view.findViewById<LinearLayout>(R.id.riwayatpinjaman)?.setOnClickListener {
            // Navigasi ke fragment Riwayat
        }
    }
}
