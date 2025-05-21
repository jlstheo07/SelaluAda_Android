package com.example.selaluada.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.selaluada.R
import com.example.selaluada.ui.profile.CapitalActivity
import com.example.selaluada.ui.profile.ChangePasswordActivity
import com.example.selaluada.ui.profile.IdentityActivity

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val btnLogout = view.findViewById<Button>(R.id.btnLogout)
        val layoutDetailAkun = view.findViewById<LinearLayout>(R.id.layoutDetailAkun)
        val layoutRekeningPencairan = view.findViewById<LinearLayout>(R.id.layoutRekeningPencairan)
        val layoutGantiPassword = view.findViewById<LinearLayout>(R.id.layoutGantiPassword)

        // Aksi ketika logout ditekan
        btnLogout.setOnClickListener {
            showLogoutDialog()
        }

        // Aksi ketika layout detail akun ditekan
        layoutDetailAkun.setOnClickListener {
            val intent = Intent(requireContext(), IdentityActivity::class.java)
            startActivity(intent)
        }

        // Aksi ketika layout rekening pencairan ditekan
        layoutRekeningPencairan.setOnClickListener {
            val intent = Intent(requireContext(), CapitalActivity::class.java)
            startActivity(intent)
        }

        // Aksi ketika layout ganti password ditekan
        layoutGantiPassword.setOnClickListener {
            val intent = Intent(requireContext(), ChangePasswordActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Logout")
        builder.setMessage("Apakah kamu yakin ingin logout?")
        builder.setPositiveButton("Ya") { _, _ ->
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        builder.setNegativeButton("Batal", null)
        builder.show()
    }
}
