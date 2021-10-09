package com.example.santagame

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.util.Log
import com.example.santagame.GameView.Companion.screenRatioX
import com.example.santagame.GameView.Companion.screenRatioY

class Doge internal constructor(private val gameView: GameView, screenY: Int, res: Resources){
    var width: Int
    var height: Int
    var temp1: Float
    var temp2: Float
    var x: Float
    var y: Float
    private var doge1: Bitmap = BitmapFactory.decodeResource(res, R.drawable.doge)
    private var doge2: Bitmap = BitmapFactory.decodeResource(res, R.drawable.doge)

    var isGoingUp = false

    var wingCounter = 0
    var shootCounter = 1

    var shoot1: Bitmap
    var shoot2: Bitmap
    var shoot3: Bitmap
    var shoot4: Bitmap
    var shoot5: Bitmap

    var dead: Bitmap

    var toShoot = 0

    init {

        width = (doge1.width/1.5).toInt()
        height = (doge1.height/1.5).toInt()
        Log.d("screen", "$width    $height")

        width /= 4
        height /= 4
        Log.d("screen 2", "$width    $height")

        temp1 = width.toFloat()
        temp2 = height.toFloat()
        Log.d("screen", "$temp1    $temp2")

        temp1 *= screenRatioX
        temp2 *= screenRatioY
        Log.d("screen", "$temp1    $temp2")
        Log.d("screen rat", "$screenRatioX    $screenRatioY")

        doge1 = Bitmap.createScaledBitmap(doge1, temp1.toInt(), temp2.toInt(), false)
        doge2 = Bitmap.createScaledBitmap(doge2, temp1.toInt(), temp2.toInt(), false)

        y = screenY/2.toFloat()
        x = screenRatioX.toFloat() * 64

        shoot1 = BitmapFactory.decodeResource(res, R.drawable.doge)
        shoot2 = BitmapFactory.decodeResource(res, R.drawable.doge)
        shoot3 = BitmapFactory.decodeResource(res, R.drawable.doge)
        shoot4 = BitmapFactory.decodeResource(res, R.drawable.doge)
        shoot5 = BitmapFactory.decodeResource(res, R.drawable.doge)
        shoot1 = Bitmap.createScaledBitmap(shoot1, temp1.toInt(), temp2.toInt(), false)
        shoot2 = Bitmap.createScaledBitmap(shoot2, temp1.toInt(), temp2.toInt(), false)
        shoot3 = Bitmap.createScaledBitmap(shoot3, temp1.toInt(), temp2.toInt(), false)
        shoot4 = Bitmap.createScaledBitmap(shoot4, temp1.toInt(), temp2.toInt(), false)
        shoot5 = Bitmap.createScaledBitmap(shoot5, temp1.toInt(), temp2.toInt(), false)

        dead = BitmapFactory.decodeResource(res, R.drawable.doge)
        dead = Bitmap.createScaledBitmap(dead, temp1.toInt(), temp2.toInt(), false)
    }

    val collisionShape: Rect
        get() = Rect(x.toInt(), y.toInt(), (x + temp1).toInt(), (y + temp2).toInt())



    val doge: Bitmap
        get() {
            if (toShoot != 0) {
                if (shootCounter == 1) {
                    shootCounter++
                    return shoot1
                }
                if (shootCounter == 2) {
                    shootCounter++
                    return shoot2
                }
                if (shootCounter == 3) {
                    shootCounter++
                    return shoot3
                }
                if (shootCounter == 4) {
                    shootCounter++
                    return shoot4
                }
                shootCounter = 1
                toShoot--
                gameView.newSnowBall()
                return shoot5
            }
            if (wingCounter == 0) {
                wingCounter++
                return doge1
            }
            wingCounter--
            return doge2
        }


}