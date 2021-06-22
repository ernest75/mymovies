package com.example.mymovies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mymovies.R
import com.example.mymovies.ui.main.MainViewModel
import org.koin.androidx.viewmodel.compat.ScopeCompat.viewModel

class NavHostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_host)
    }
}