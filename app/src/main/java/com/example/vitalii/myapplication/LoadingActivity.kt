package com.example.vitalii.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar

class LoadingActivity : AppCompatActivity() {

    private var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        val progressBar: ProgressBar = findViewById(R.id.progressBar2)
        progressBar.progress = 0
        progressBar.progress = 20
        handler.postDelayed({
            progressBar.progress = 70
        }, 2000)
        handler.postDelayed({
            progressBar.progress = 100
        }, 3000)
        handler.postDelayed({
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        },5000)
    }
}
