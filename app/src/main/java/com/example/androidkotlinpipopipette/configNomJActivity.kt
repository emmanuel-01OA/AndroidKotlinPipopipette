package com.example.androidkotlinpipopipette

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.coroutines.NonCancellable.start


class configNomJActivity : AppCompatActivity() {
    var activityState: Int = R.string.app_name
    var jeuPipopipetteView: jeuPipopipetteView? = null
    var yesterdayToast: Toast? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        // affiche l'interface xml select_nom_joueur.xml
        setContentView(R.layout.selection_nom_joueur)

        val selectionNom1 = findViewById<EditText>(R.id.selectj1)

        val selectionNom2 = findViewById<EditText>(R.id.selectj2)


        val retourkbtn: Button = findViewById(R.id.button_ret);

        retourkbtn.setOnClickListener {

            val intent: Intent = Intent(this, MenuPipopipette::class.java);
            this.startActivity(intent)



        }


      /*  val buttonDemarrerJeu = findViewById<Button>(R.id.btn_demarrer)
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
                intentj.putExtra("joueur1",reccupselect1)
                intentj.putExtra("joueur2", reccupselect2)
                 this.startActivity(intentj)
                finish()
            }*/
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
        @RequiresApi(Build.VERSION_CODES.O)
        fun demarrer(view: View) {
            jeuPipopipetteView = jeuPipopipetteView(this)
            setContentView(jeuPipopipetteView)
            activityState = R.string.app_name
        }



}