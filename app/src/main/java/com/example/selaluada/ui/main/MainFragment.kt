package com.example.selaluada.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.selaluada.R
import com.example.selaluada.ui.main.PlafondActivity

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<LinearLayout>(R.id.promolocation)?.setOnClickListener {
            val intent = Intent(requireContext(), LocationActivity::class.java)
            startActivity(intent)
        }

        // Navigasi ke PlafondActivity saat tombol "Level" ditekan
        view.findViewById<LinearLayout>(R.id.plafondlevel)?.setOnClickListener {
            val intent = Intent(requireContext(), PlafondActivity::class.java)
            startActivity(intent)
        }

        // TODO: Ganti dengan Intent atau Navigation ke Fragment/Activity sesuai fitur
        view.findViewById<LinearLayout>(R.id.location)?.setOnClickListener {
            val intent = Intent(requireContext(), LocationActivity::class.java)
            startActivity(intent)
        }

        view.findViewById<LinearLayout>(R.id.riwayatpinjaman)?.setOnClickListener {
            val intent = Intent(requireContext(), HistoryActivity::class.java)
            startActivity(intent)
        }

        view.findViewById<LinearLayout>(R.id.DataKonsumen)?.setOnClickListener {
            val intent = Intent(requireContext(), DataActivity::class.java)
            startActivity(intent)
        }
    }
}
