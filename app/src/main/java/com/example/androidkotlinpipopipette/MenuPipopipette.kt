package com.example.androidkotlinpipopipette

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView

class MenuPipopipette : AppCompatActivity() {

    private lateinit var ActionInstruction: CardView
    private lateinit var ActionJouerPipopipette : CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_pipopipette)

        ActionInstruction = findViewById(R.id.btninstruction)
        ActionInstruction.setOnClickListener {

            // ouvrir le menu principal de notre apps pipopipette
            val intentinfo: Intent = Intent(this, Menuconfig::class.java)
            startActivity(intentinfo)

        }

        // aller dans la page de configuration de nom

       ActionJouerPipopipette = findViewById(R.id.btnjconfigNom)
        val inten: Intent = Intent(this, configNomJActivity::class.java)
        startActivity(inten)

    }

    // fonction qui permet de quitter l app lorsqu on appuis sur le bouton quitter
     fun finishAffinity(view: View) {
        super.finishAffinity()
    }

}