package com.example.androidkotlinpipopipette


import android.content.Context
import android.graphics.Paint
import androidx.core.content.res.ResourcesCompat
import kotlin.math.abs

class Point(var xCoodinate: Int, var yCoordinate: Int, context: Context) {
    var isClicked: Boolean = false
    var paint: Paint = Paint()
    var pointRadius: Float = 25F
    private var colorNodeSelected =
        ResourcesCompat.getColor(context.resources, R.color.couleur_selectionPoint, null)
    private val colorNodeNotSelected =
        ResourcesCompat.getColor(context.resources, R.color.couleur_Point, null)


    init {
        paint.color = colorNodeNotSelected
    }

    /*
    condition : si le joueur appuis sur le point ou noeud du jeu pipopipette
     le point s'agrandit et change de couleur
  */
    fun clicked() {
        if (isClicked) {
            isClicked = false
            paint.color = colorNodeNotSelected
            pointRadius = 20F
        } else {
            isClicked = true
            paint.color = colorNodeSelected
            pointRadius = 40F
        }
    }

    //
    // vérifie si y a pas de ligne dejà occupé
    //
    fun isLegalNeighbour(point: Point): Boolean {
        if (xCoodinate == point.xCoodinate) {
            return abs((yCoordinate - point.yCoordinate)) == 1
        }
        if (yCoordinate == point.yCoordinate) {
            return abs((xCoodinate - point.xCoodinate)) == 1
        }
        return false
    }

    //

    override fun equals(other: Any?): Boolean {
        val otherAsPoint = other as Point
        return (xCoodinate == otherAsPoint.xCoodinate) && (yCoordinate == otherAsPoint.yCoordinate)
    }
}

//
class Line(var begin: Point, var end: Point) {
    val VERTICAL: Int = 1
    val HORIZONTAL: Int = 0
    var orientation: Int

    init {
        orientation = specifyOrientation()
    }

    // met la position des lignes
    private fun specifyOrientation(): Int {
        return if (begin.xCoodinate == end.xCoodinate) {
            HORIZONTAL
        } else {
            VERTICAL
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other is Line) {
            return (begin == other.begin || begin == other.end) &&
                    (end == other.begin || end == other.end)
        }
        return super.equals(other)
    }
}

// ici on a le constructeur du parametre du score, couleur ligne du joueur
class Player(val name: String, var color: Int) {
    var paint: Paint = Paint()
    var score: Int = 0
    var lines: MutableList<Line> = mutableListOf()

    // config initial des valeurs du score
    init {
        paint.color = color
        paint.strokeWidth = 22F
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = 200F
    }

}