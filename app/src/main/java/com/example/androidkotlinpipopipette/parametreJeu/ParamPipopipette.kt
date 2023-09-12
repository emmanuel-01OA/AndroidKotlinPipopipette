package com.example.androidkotlinpipopipette.parametreJeu



import android.content.Context
import android.graphics.Color
import android.preference.PreferenceManager
import com.example.androidkotlinpipopipette.Player
import com.example.androidkotlinpipopipette.Point
import com.example.androidkotlinpipopipette.R


class ParamPipopipette(val mapSize: Int, val context: Context) {
    var turn: Int = 0
    var mapPoint: Array<Array<Point>>
    val squaresClosed: MutableList<Point> = mutableListOf()
    var joueur: Array<Player>
    var winner: Player? = null
    val maxSquares: Int = (mapSize - 1) * (mapSize - 1)
    var gameState: Boolean = true
    val editor = PreferenceManager.getDefaultSharedPreferences(context)

    init {
        //initialise les dimensions du jeu
        mapPoint = Array(mapSize) { row ->
            Array(mapSize) { col ->
                Point(row, col, context)
            }
        }

        //init joueur
        val joueur1 = editor.getString(
            context.resources.getString(R.string.strjoueur1),
            "joueur1"
        )!!
        val joueur1style = editor.getInt(
            context.resources.getString(R.string.strjoueur1),
            Color.rgb(0,145,150)
        )
        val joueur2= editor.getString(
            context.resources.getString(R.string.strjoueur2),
            "joueur2"
        )!!
        val joueur2style = editor.getInt(
            context.resources.getString(R.string.strjoueur2),
            context.resources.getColor(R.color.purple_700)
        )

        joueur = arrayOf(Player(joueur1, joueur1style), Player(joueur2, joueur2style))


    }
}