package com.example.androidkotlinpipopipette

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class configNomJActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // affiche l'interface xml select_nom_joueur.xml
        setContentView(R.layout.selection_nom_joueur)

        val selectionNom1 = findViewById<EditText>(R.id.selectj1)

        val selectionNom2 = findViewById<EditText>(R.id.selectj2)


        val retourkbtn: Button = findViewById(R.id.button_ret);

        retourkbtn.setOnClickListener {

            val intent: Intent = Intent(this, MainActivity::class.java);
            this.startActivity(intent)
            finish()


        }


        val buttonDemarrerJeu = findViewById<Button>(R.id.btn_demarrer)
        buttonDemarrerJeu.setOnClickListener{
            val reccupselect1 = selectionNom1.text.toString();
            val reccupselect2 = selectionNom2.text.toString();

            if(reccupselect1.isEmpty() || reccupselect2.isEmpty()){

                Toast.makeText(applicationContext, "veuillez entrer le ou les identifiants joueurs", Toast.LENGTH_SHORT).show()



            }else{
                // si les noms sont entrées , on ouvre le jeu
                val intentj: Intent = Intent(this, InterfaceJeuPipopipetteActivity::class.java);
               // putExtra est comme une variable de session en php
                // il reccupere les données identifiants pour les envoyéesdans une autre activité
                intentj.putExtra("Joueur 1",reccupselect1)
                intentj.putExtra("Joueur 2", reccupselect2)
                this.startActivity(intentj)
                finish()
            }
        }

        /* methode d extanciation de action listener
        buttonDemarrerJeu.setOnClickListener{
            launchActivity<MainActivity>()
        }


    }
    inline fun<reified T> launchActivity(){

        val reccupselect1 = selectionNom1.getText().toString();
        val intent = Intent(this, T::class.java)
        startActivity(intent)
    }*/
    }

}