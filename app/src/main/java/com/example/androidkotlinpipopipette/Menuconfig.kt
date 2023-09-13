package com.example.androidkotlinpipopipette

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class Menuconfig : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menuconfig)

        val retourkbtn =  findViewById<Button>(R.id.buttonRetinstruction)
        retourkbtn.setOnClickListener {

            // bouton retour qui mene au menu principale.
            val intenretour = Intent(this, MenuPipopipette::class.java)
            this.startActivity(intenretour)
            finish()



        }


    }
}