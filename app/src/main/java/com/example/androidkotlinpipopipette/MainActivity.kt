package com.example.androidkotlinpipopipette


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.btnJouer)
        button.setOnClickListener {

                val intent: Intent = Intent(this, configNomJActivity::class.java);


            this.startActivity(intent)
             finish()


        }
    }


}