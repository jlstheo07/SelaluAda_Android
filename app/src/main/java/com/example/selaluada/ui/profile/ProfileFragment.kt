package com.example.selaluada.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.selaluada.R
import com.example.selaluada.data.model.LogoutResponse
import com.example.selaluada.data.model.LogoutRequest
import com.example.selaluada.data.network.AuthApiClient
import com.example.selaluada.data.service.AuthApiService
import com.example.selaluada.ui.auth.LoginActivity
import com.example.selaluada.ui.main.LocationActivity
import com.example.selaluada.ui.profile.CapitalActivity
import com.example.selaluada.ui.profile.ChangePasswordActivity
import com.example.selaluada.ui.profile.IdentityActivity
import com.example.selaluada.util.SharedPreferenceUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private lateinit var btnLogout: Button

    private lateinit var listProfile: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        initViews(view)
        setupView()
        return view
    }

    private fun initViews(view: View) {
        btnLogout = view.findViewById(R.id.btnLogout)

        listProfile = view.findViewById(R.id.listprofile)

        val layoutDetailAkun = view.findViewById<LinearLayout>(R.id.layoutDetailAkun)
        val layoutLocation = view.findViewById<LinearLayout>(R.id.layoutLocationNow)
        val layoutRekeningPencairan = view.findViewById<LinearLayout>(R.id.layoutRekeningPencairan)
        val layoutGantiPassword = view.findViewById<LinearLayout>(R.id.layoutGantiPassword)

        layoutDetailAkun.setOnClickListener {
            startActivity(Intent(requireContext(), IdentityActivity::class.java))
        }

        layoutLocation.setOnClickListener {
            startActivity(Intent(requireContext(), LocationActivity::class.java))
        }

        layoutRekeningPencairan.setOnClickListener {
            startActivity(Intent(requireContext(), CapitalActivity::class.java))
        }

        layoutGantiPassword.setOnClickListener {
            startActivity(Intent(requireContext(), ChangePasswordActivity::class.java))
        }



        btnLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun setupView() {
        val isLoggedIn = SharedPreferenceUtil.isLoggedIn(requireContext())
        btnLogout.visibility = if (isLoggedIn) View.VISIBLE else View.GONE
        listProfile.visibility = if (isLoggedIn) View.VISIBLE else View.GONE
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Apakah kamu yakin ingin logout?")
            .setPositiveButton("Ya") { _, _ -> performLogout() }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun performLogout() {
        val context = requireContext()
        val token = SharedPreferenceUtil.getAuthToken(context)
        Log.d("ProfileFragment", "Saved token: ${SharedPreferenceUtil.getAuthToken(requireContext())}")

        if (token.isNullOrEmpty()) {
            // Jika belum login, langsung kembali ke login
            Toast.makeText(context, "Belum login", Toast.LENGTH_SHORT).show()
            SharedPreferenceUtil.clearAuthToken(context)
            startActivity(Intent(context, LoginActivity::class.java))
            requireActivity().finish()
            return
        }

        val apiService = AuthApiClient.getInstance(context).create(AuthApiService::class.java)
        val request = LogoutRequest(null) // kirim token FCM jika perlu
        val bearerToken = "Bearer $token"

        apiService.logout(request).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    SharedPreferenceUtil.clearAuthToken(context)
                    Toast.makeText(context, "Logout berhasil", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(context, LoginActivity::class.java))
                    requireActivity().finish()
                } else {
                    Log.e("Logout", "Gagal logout: ${response.code()} - ${response.message()}")
                    Toast.makeText(context, "Gagal logout", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Logout", "Error logout: ${t.message}", t)
                Toast.makeText(context, "Terjadi kesalahan saat logout", Toast.LENGTH_SHORT).show()
            }
        })


    }
}
