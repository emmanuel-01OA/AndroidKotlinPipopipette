package com.example.androidkotlinpipopipette


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.cardview.widget.CardView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val CardVbutton = findViewById<CardView>(R.id.comj)
        CardVbutton.setOnClickListener {

            val intentm = Intent(this, MenuPipopipette::class.java)
            this.startActivity(intentm)
            finish()
    }
/*
    fun menu(view: View) {



        }*/
    }

}