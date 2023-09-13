package com.example.androidkotlinpipopipette

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.preference.PreferenceManager
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.androidkotlinpipopipette.parametreJeu.ParamPipopipette
import com.example.androidkotlinpipopipette.parametreJeu.Pipopipetteprocess
import com.example.androidkotlinpipopipette.parametreJeu.logiqueJeuPipopipette


@RequiresApi(Build.VERSION_CODES.O)
class jeuPipopipetteView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    private val dimensions = ScreenDimensions(context)
    private val myPreferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)
    private val game =
        ParamPipopipette(myPreferences.getInt(context.resources.getString(R.string.app_name), 7), context)
    private val gameController = logiqueJeuPipopipette(game, dimensions)
    lateinit var thread: Pipopipetteprocess



    init {
        processus()
        focusable = View.FOCUSABLE
        holder.addCallback(this)
    }

    // mettre un processus au cas ou il y a interruption de la partie de jeu
    fun processus() {
        thread = Pipopipetteprocess(holder, this)
    }


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (canvas != null) {
            canvas.drawColor(Color.MAGENTA)
            drawTurnIndicator(canvas)
            drawLines(canvas)
            drawSquares(canvas)
            drawNodes(canvas)
            drawName(canvas)
            drawScore(canvas)
            if (!game.gameState)
                EtatFinJeu(canvas)
        }
    }

    private fun drawSquares(canvas: Canvas) {
        game.squaresClosed.forEach { square ->
            run {
                val paint: Paint = Paint()
               // paint.color = Color.rgb(25, 25, 25)
                paint.color = Color.DKGRAY
                paint.strokeWidth = 70F
                var position = getNodePosition(square)
                canvas.drawLine(
                    position[0],
                    position[1],
                    position[0] + dimensions.nodeToNodeDistance,
                    position[1] - dimensions.nodeToNodeDistance,
                    paint
                )
                canvas.drawLine(
                    position[0] + dimensions.nodeToNodeDistance,
                    position[1],
                    position[0],
                    position[1] - dimensions.nodeToNodeDistance,
                    paint
                )
            }
        }
    }

    private fun drawLines(canvas: Canvas) {
        var paint: Paint?
        for (joueur in game.joueur) {
            paint = joueur.paint
            joueur.lines.forEach { line ->
                run {
                    val firstPointPosition: Array<Float> = getNodePosition(line.begin)
                    val secondPointPosition: Array<Float> = getNodePosition(line.end)
                    canvas.drawLine(
                        firstPointPosition[0],
                        firstPointPosition[1],
                        secondPointPosition[0],
                        secondPointPosition[1],
                        paint
                    )
                }
            }
        }
    } //OK

    private fun drawTurnIndicator(canvas: Canvas) {
        var paint: Paint = game.joueur[game.turn % 2].paint

        // couleur de selection du joueur en cour ( en train de jouer )
        canvas.drawRoundRect(
            RectF(
                0F, 0F,
                dimensions.screenWidth.toFloat(), dimensions.screenHeight.toFloat()
            ), dimensions.indicatorRadius, dimensions.indicatorRadius, paint
        )
        paint = Paint()
        paint.color = Color.WHITE
        // couleur du background
//        paint.color = Color.rgb(0, 0, 0)

        canvas.drawRoundRect(
            RectF(
                dimensions.indicatorWidth, dimensions.indicatorWidth,
                dimensions.screenWidth.toFloat() - 6, dimensions.screenHeight.toFloat() - 6
            ), dimensions.indicatorRadius, dimensions.indicatorRadius, paint
        )
    }

    // permet de creer le score des joueur
    private fun drawScore(canvas: Canvas) {

        var xPos = dimensions.scoreXPosition
        var yPos = dimensions.scoreYPosition
        canvas.drawText(game.joueur[0].score.toString(), xPos, yPos, game.joueur[0].paint)

        xPos = dimensions.screenWidth - dimensions.scoreXPosition
        canvas.drawText(game.joueur[1].score.toString(), xPos, yPos, game.joueur[1].paint)
    }

    private fun drawName(canvas: Canvas) {
        val paint = Paint()
        paint.color = game.joueur[game.turn % 2].paint.color
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = 80F

        canvas.drawText(
            game.joueur[game.turn % 2].name,
            dimensions.nameXPosition,
            dimensions.nameYPosition,
            paint
        )
    } //OK

    private fun drawNodes(canvas: Canvas) {
        for (row in game.mapPoint) {
            for (node in row) {
                canvas.drawCircle(
                    dimensions.horizontalMargain.toFloat() + (node.xCoodinate * dimensions.nodeToNodeDistance),
                    dimensions.verticalMargin.toFloat() + (node.yCoordinate * dimensions.nodeToNodeDistance),
                    node.pointRadius,
                    node.paint
                )
            }
        }

    }

    private fun getNodePosition(node: Point): Array<Float> {
        return arrayOf(
            dimensions.horizontalMargain.toFloat() + (node.xCoodinate * dimensions.nodeToNodeDistance),
            dimensions.verticalMargin.toFloat() + (node.yCoordinate * dimensions.nodeToNodeDistance)
        )
    }

    private fun EtatFinJeu(canvas: Canvas) {
        var paint: Paint? = game.winner?.paint
        val name: String? = game.winner?.name

        if (paint != null) {
            canvas.drawRoundRect(
                RectF(
                    dimensions.horizontalMargain.toFloat(),
                    dimensions.screenHeight / 2 + 200F,
                    dimensions.screenWidth - dimensions.horizontalMargain.toFloat(),
                    dimensions.screenHeight / 2 - 200F
                ),
                50F, 50F, paint
            )
        }
        paint = Paint()
        paint.color = Color.rgb(0, 0, 0)
        canvas.drawRoundRect(
            RectF(
                dimensions.horizontalMargain.toFloat() + dimensions.messageBorderWidth,
                dimensions.screenHeight / 2 + 195F,
                dimensions.screenWidth - dimensions.horizontalMargain.toFloat() - dimensions.messageBorderWidth,
                dimensions.screenHeight / 2 - 195F
            ),
            50F, 50F, paint
        )

        //Text
        paint.color = game.winner?.paint?.color!!
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = dimensions.textSize

        val xPos = dimensions.screenWidth.toFloat() / 2
        val yPos = dimensions.screenHeight / 2 - 40F

        canvas.drawText("GAGNANT!", xPos, yPos, paint)
        canvas.drawText(name!!, xPos, yPos + 120F, paint)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                thread.running = false
                thread.join()
            } catch (exception: InterruptedException) {
                exception.printStackTrace()
            }
            retry = false
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        thread.running = true
        thread.start()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if( ! gameController.actionPerformed(event)) {
            Toast.makeText(this.context, "impossible de placer la ligne dans cette direction", Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }

    fun update() {

    }
}

// position de la matrice des points du jeu pipopipette
data class ScreenDimensions(val context: Context) {

    val screenHeight: Int = context.resources.displayMetrics.heightPixels
    val screenWidth: Int = context.resources.displayMetrics.widthPixels
    val horizontalMargain: Int = 170
    val boardSize: Int = screenWidth - 2 * horizontalMargain
    val verticalMargin: Int = (screenHeight - boardSize) / 2
    val nodeToNodeDistance = boardSize / (PreferenceManager.getDefaultSharedPreferences(context)
        .getInt(context.resources.getString(R.string.app_name), 7) - 1)


    val scoreYPosition = 160F
    val scoreXPosition = 100F

    val indicatorRadius = 0F
    val indicatorWidth = 6F
    val lineStrokeWidth = 8F

    val nameYPosition = 180F
    val nameXPosition = screenWidth / 2F

    val messageBorderWidth = 5F

    val textSize = 190F

}