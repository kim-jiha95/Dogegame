package com.example.santagame

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView
import java.util.*
import kotlin.collections.ArrayList

class GameView(private val activity: GameActivity, screenX: Int, screenY: Int) : SurfaceView(activity), Runnable {

    var thread: Thread? = null
    var isPlaying = false
    var isGameOver = false
    val screenX: Int
    val screenY: Int
    val paint: Paint
    val background1: Background
    val background2: Background
    val doge: Doge
    val coins: MutableList<Coin>
    val musks: Array<Musk?>
    val random: Random
//코루틴, async task

    private var b1: Float
    private var b2: Float

    companion object{
        var screenRatioX: Float = 0.0f
        var screenRatioY: Float = 0.0f
    }

    init {
        this.screenX = screenX
        this.screenY = screenY
        screenRatioX = 1920f / screenX
        screenRatioY = 1080f / screenY
        background1 = Background(screenX, screenY, resources)
        background2 = Background(screenX, screenY, resources)
        background2.x = screenX

        paint = Paint()

        b1 = background1.x.toFloat()
        b2 = background2.x.toFloat()

        doge = Doge(this, screenY, resources)
        Log.d("screen", screenY.toString())

        coins = ArrayList()

        musks = arrayOfNulls<Musk>(4)
        for(i in 0..3){
            val musk = Musk(resources)
            musks[i] = musk
        }

        random = Random()


    }

    override fun run() {
        while(isPlaying){
            update()
            draw()
            sleep()
        }

    }

    private fun update(){

        b1 -= 10 * screenRatioX!!
        b2 -= 10 * screenRatioX!!

        if (b1 + background1.background.width < 0) {
            b1 = screenX!!.toFloat()
            Log.d("LOG UPDATE", "UPDATE 1: $b1")
        }
        if (b2 + background2.background.width < 0) {
            b2 = screenX!!.toFloat()
            Log.d("LOG UPDATE", "UPDATE 2: $b2")
        }
        if(doge.isGoingUp){
            doge.y -= 30 * screenRatioY
        }else{
            doge.y += 30 * screenRatioY
        }
        if(doge.y < 0)
            doge.y = 0F
        if(doge.y >= screenY - doge.height)
            doge.y = (screenY - doge.height).toFloat()

        val trashes: MutableList<Coin> = ArrayList()
        for(coin in coins){
            if(coin.x > screenX)    trashes.add(coin)
            coin.x += (50 * screenRatioX).toInt()
            Log.d("snowball", "${coin.x}")

            for(musk in musks){
                if(Rect.intersects(
                        musk!!.collisionShape,
                        coin.collisionShape
                    )){

                    musk.x = -100000
                    coin.x = screenX + 500
                    musk.wasShot = true
                }
            }
        }
        for (coin in trashes) coins.remove(coin)

        for(musk in musks){
            musk!!.x -= musk!!.speed

            if(musk.x + musk.width < 0){

//                if(!musk.wasShot){
//                    isGameOver = true
//                    return
//                }

                val bound = (30* screenRatioX).toInt()
                musk.speed = random.nextInt(bound)

                if(musk.speed < 10 * screenRatioX)
                    musk.speed = (10 * screenRatioX).toInt()
                musk.x = screenX
                musk.y = random.nextInt((screenY - musk.height).toInt())

                musk.wasShot = false
            }

//            if(Rect.intersects(musk.collisionShape, doge.collisionShape)){
//                isGameOver = true
//                return
//            }
        }
    }

    private fun draw(){
        if (holder.surface.isValid) {
            val canvas: Canvas = holder.lockCanvas()
            canvas.drawBitmap(background1.background, b1, background1.y.toFloat(), paint)
            canvas.drawBitmap(background2.background, b2, background2.y.toFloat(), paint)

            if(isGameOver){
                isPlaying = false
                canvas.drawBitmap(doge.dead, doge.x, doge.y, paint)
                holder.unlockCanvasAndPost(canvas)
                return
            }

            for(musk in musks)
                canvas.drawBitmap(musk!!.musk, musk.x.toFloat(), musk.y.toFloat(), paint)

            canvas.drawBitmap(doge.doge, doge.x, doge.y, paint)

            for(snowball in coins)
                canvas.drawBitmap(snowball.coin, snowball.x.toFloat(), snowball.y.toFloat(), paint)

            holder.unlockCanvasAndPost(canvas)
        }
    }

    private fun sleep(){
        Thread.sleep(17)
    }

    fun resume(){
        isPlaying = true
        thread = Thread(this)
        thread!!.start()
    }

    fun pause(){
        isPlaying = false
        thread!!.join()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.action){
            MotionEvent.ACTION_DOWN -> if(event.x < screenX / 2){
                doge.isGoingUp = true
            }
            MotionEvent.ACTION_UP -> {
                doge.isGoingUp = false
                if (event.x > screenX / 2) doge.toShoot++
            }
        }

        return true
    }

    fun newSnowBall(){
        val snowball = Coin(resources)
        snowball.x = (doge.x + doge.width).toInt()
        snowball.y = (doge.y + doge.height / 2).toInt()
        coins.add(snowball)
    }
}