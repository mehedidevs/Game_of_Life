package com.mehedi.game_of_life.view.custom_views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.WindowManager
import com.mehedi.game_of_life.hilt.services.World
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameView : SurfaceView, Runnable {
    private var thread: Thread? = null
    var isRunning = false
    var columnWidth = 1
    var rowHeight = 1
    var nbColumns = 1
    var nbRow = 1
    var world: World? = null
    var r = Rect()
    var p = Paint()

    constructor(context: Context?) : super(context) {
        intiWorld()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        intiWorld()
    }

    override fun run() {
        while (isRunning) {
            if (!holder.surface.isValid) {
                continue
            }
            try {
                Thread.sleep(500)
            } catch (e: Exception) {
            }
            val canvas = holder.lockCanvas()

           CoroutineScope(Dispatchers.IO).launch {
               world!!.nextGeneration()
           }


            drawCells(canvas)
            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun start() {
        isRunning = true
        thread = Thread(this)
        thread!!.start()
    }

    fun stop() {
        isRunning = false
        while (true) {
            try {
                thread!!.join()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            break
        }
    }

    private fun intiWorld() {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val point = Point()
        display.getSize(point)
        nbColumns = point.x / DEFAULT_SIZE
        nbRow = point.y / DEFAULT_SIZE
        columnWidth = point.x / nbColumns
        rowHeight = point.y / nbRow
        world = World(nbColumns, nbRow)
    }

    private fun drawCells(canvas: Canvas) {
        for (i in 0 until nbColumns) {
            for (j in 0 until nbRow) {
                val cell = world!![i, j]
                r[cell!!.x * columnWidth - 1, cell.y * rowHeight - 1, cell.x * columnWidth + columnWidth] =
                    cell.y * rowHeight + rowHeight
                p.color = if (cell.alive) ALIVE_COLOR else DEAD_COLOR
                canvas.drawRect(r, p)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)
        if (event.action == MotionEvent.ACTION_DOWN) {
            val i = (event.x / columnWidth).toInt()
            val j = (event.y / rowHeight).toInt()
            val cell = world!![i, j]
            cell!!.invert()
            invalidate()
            return true
        }
        return false
    }

    companion object {
        const val DEFAULT_SIZE = 50
        const val ALIVE_COLOR = Color.WHITE
        const val DEAD_COLOR = Color.BLACK
    }
}