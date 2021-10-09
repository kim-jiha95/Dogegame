package com.example.santagame

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.example.santagame.GameView.Companion.screenRatioX
import com.example.santagame.GameView.Companion.screenRatioY

class Coin internal constructor(res: Resources) {
    var x = 0
    var y = 0
    var width: Float
    var height: Float
    var coin: Bitmap

    init {
        coin = BitmapFactory.decodeResource(res, R.drawable.coin)
        width = coin.width.toFloat()/8
        height = coin.height.toFloat()/8

        width /= 4
        height /= 4

        width *= screenRatioX
        height *= screenRatioY

        coin = Bitmap.createScaledBitmap(coin, width.toInt(), height.toInt(), false)

    }

    val collisionShape: Rect
        get() = Rect(x, y, x + width.toInt(), y + height.toInt())
}