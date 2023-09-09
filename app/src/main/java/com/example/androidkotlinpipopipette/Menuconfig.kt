package com.example.androidkotlinpipopipette

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class Menuconfig : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menuconfig)

      // val RelativeLayout: RelativeLayout = findViewById(R.id.menupipo)


        val retourkbtn: Button =  findViewById(R.id.button_ret);

        retourkbtn.setOnClickListener {

            val intent: Intent = Intent(this, MainActivity::class.java);
            this.startActivity(intent)
            finish()


        }

    }
}