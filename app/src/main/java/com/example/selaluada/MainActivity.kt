package com.example.selaluada


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.selaluada.ui.LoginActivity
import com.example.selaluada.ui.ProfileFragment
import com.example.selaluada.ui.loan.LoanFragment
import com.example.selaluada.ui.main.MainFragment
import com.example.selaluada.util.SharedPreferenceUtil

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private val mainFragment = MainFragment()
    private val loanFragment = LoanFragment()
    private val profileFragment = ProfileFragment  ()
    private var isLogin =false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getFCMToken()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, mainFragment)
            .commit()

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(mainFragment)
                    true
                }
                R.id.nav_loan -> {
                    replaceFragment(loanFragment)
                    true
                }


                R.id.nav_profile -> {
                    isLogin = SharedPreferenceUtil.isLoggedIn(this)
                    if (!isLogin) {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        return@setOnItemSelectedListener false
                    }
                    replaceFragment(profileFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Log.d("FCM", "FCM Token: $token")
                // Here you can save the token, send it to your server, etc.
            } else {
                Log.w("FCM", "Fetching FCM token failed", task.exception)
            }
        }
    }


}
