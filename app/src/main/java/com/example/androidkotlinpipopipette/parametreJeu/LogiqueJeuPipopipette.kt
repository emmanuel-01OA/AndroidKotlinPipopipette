package com.example.androidkotlinpipopipette.parametreJeu

import android.view.MotionEvent
import com.example.androidkotlinpipopipette.*


class logiqueJeuPipopipette(val game: ParamPipopipette, private val dims: ScreenDimensions) {
    var previousSelectedNode: Point? = null


    fun actionPerformed(event: MotionEvent?): Boolean {
        if (event != null) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val pointClicked: Point? = getNodeClicked(event.x, event.y)
                    if (pointClicked != null) {
                        return evaluateMove(pointClicked)
                    }
                    return false
                }
            }
        }
        return false
    }

    private fun getNodeClicked(x: Float, y: Float): Point? {
        val touchTolerance: Float = (dims.boardSize / (game.mapSize * 2)).toFloat()

        for (row in game.mapPoint) {
            for (node in row) {
                if (x > dims.horizontalMargain.toFloat() + (node.xCoodinate * dims.nodeToNodeDistance) - touchTolerance
                    && x < dims.horizontalMargain.toFloat() + (node.xCoodinate * dims.nodeToNodeDistance) + touchTolerance
                    && y > dims.verticalMargin.toFloat() + (node.yCoordinate * dims.nodeToNodeDistance) - touchTolerance
                    && y < dims.verticalMargin.toFloat() + (node.yCoordinate * dims.nodeToNodeDistance) + touchTolerance
                ) {
                    return node
                }
            }
        }
        return null
    }

    fun evaluateMove(nowSelectedNode: Point): Boolean {
        if (previousSelectedNode != null) {
            val newLine = Line(nowSelectedNode, previousSelectedNode!!)
            if (nowSelectedNode.isLegalNeighbour(previousSelectedNode!!)
                && !checkIfLineExists(newLine)
            ) {
                game.joueur[game.turn % 2].lines.add(newLine)
                previousSelectedNode!!.clicked()
                previousSelectedNode = null
                val squareClosed = checkIfSquareIsClosed()
                if (squareClosed == 0) {
                    game.turn++

                } else {
                    game.joueur[game.turn % 2].score += squareClosed
                }

                if (game.squaresClosed.size == game.maxSquares) {
                    game.gameState = false
                    game.winner = evaluateWinner()
                }
                return true
            } else {
                previousSelectedNode!!.clicked()
                previousSelectedNode = null
                return false
            }
        } else {
            nowSelectedNode.clicked()
            previousSelectedNode = nowSelectedNode
            return true
        }
        return false
    }

    fun evaluateWinner(): Player {
        val joueur1Score = game.joueur[0].score
        val joueur2Score = game.joueur[1].score
        return if (joueur1Score > joueur2Score) game.joueur[0] else game.joueur[1]
    }

    private fun checkPointsClickState(): Point? {
        game.mapPoint.forEach { col ->
            run {
                col.forEach { node ->
                    if (node.isClicked) return node
                }
            }
        }
        return null
    }

    // verifie si la ligne est presente et dans le cas contraire on trace la ligne
    private fun checkIfLineExists(newLine: Line): Boolean {
        for (player in game.joueur) {
            player.lines.forEach { line ->
                run {
                    if (line == newLine)
                        return true
                }
            }
        }
        return false
    }

// logique de marquage de point ( si la forme dessine un carré alors le joueur marque 1 point et rejoue
    private fun checkIfSquareIsClosed(): Int {
        var size = game.squaresClosed.size
        for (x in 0..game.mapSize) {
            for (y in 0..game.mapSize) {
                if (checkIfLineExists(
                        Line(
                            Point(x, y, game.context),
                            Point(x + 1, y, game.context)
                        )
                    ) &&
                    checkIfLineExists(
                        Line(
                            Point(x + 1, y, game.context),
                            Point(x + 1, y - 1, game.context)
                        )
                    ) &&
                    checkIfLineExists(
                        Line(
                            Point(x + 1, y - 1, game.context),
                            Point(x, y - 1, game.context)
                        )
                    ) &&
                    checkIfLineExists(
                        Line(
                            Point(x, y - 1, game.context),
                            Point(x, y, game.context)
                        )
                    )
                ) {
                    val topRightNodeOfSquare = Point(x, y, game.context)
                    if (!game.squaresClosed.contains(topRightNodeOfSquare)) {
                        game.squaresClosed.add(topRightNodeOfSquare)
                    }
                }
            }
        }
        return game.squaresClosed.size - size
    }
}