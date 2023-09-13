package com.example.androidkotlinpipopipette.parametreJeu

import android.graphics.Canvas
import android.os.Build
import android.view.SurfaceHolder
import androidx.annotation.RequiresApi
import com.example.androidkotlinpipopipette.jeuPipopipetteView


class Pipopipetteprocess(var surfaceHolder: SurfaceHolder, var jeuPipopipetteView: jeuPipopipetteView) : Thread() {
    var running: Boolean = false
    var canvas: Canvas? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun run() {
        var startTime: Long
        var timeMillis: Long
        var waitTime: Long
        val targetFPS: Long = 60
        val targetTime: Long = 1000 / targetFPS

        while (running) {
            startTime = System.nanoTime()
            canvas = Canvas()

            try {
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    this.jeuPipopipetteView.update()
                    this.jeuPipopipetteView.draw(canvas)
                }
            } catch (exception: Exception) {
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    } catch (exception: Exception) {
                        exception.printStackTrace()
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime) / 1000000
            waitTime = targetTime - timeMillis

            try {
                sleep(waitTime)
            } catch (exception: Exception) {
            }


        }


    }


}