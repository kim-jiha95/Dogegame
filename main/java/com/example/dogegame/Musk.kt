package com.example.santagame

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.example.santagame.GameView.Companion.screenRatioX
import com.example.santagame.GameView.Companion.screenRatioY

class Musk internal constructor(res: Resources){
    var speed = 20
    var musk1: Bitmap
    var x = 0
    var y = 0
    var width: Float
    var height: Float
    var wasShot = true

    val musk: Bitmap
        get() {
            return musk1
        }

    init {
        musk1 = BitmapFactory.decodeResource(res, R.drawable.musk)
        width = musk1.width.toFloat()
        height = musk1.width.toFloat()
        width /= 10
        height /= 12
        width *= screenRatioX
        height *= screenRatioY

        musk1 = Bitmap.createScaledBitmap(musk1, width.toInt(), height.toInt(), false)
        y = -height.toInt()


    }

    val collisionShape: Rect
        get() = Rect(x, y, x + width.toInt(), y + height.toInt())

}