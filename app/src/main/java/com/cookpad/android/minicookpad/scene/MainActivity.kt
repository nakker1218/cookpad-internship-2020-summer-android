package com.cookpad.android.minicookpad.scene

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cookpad.android.minicookpad.R
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)
    }
}
