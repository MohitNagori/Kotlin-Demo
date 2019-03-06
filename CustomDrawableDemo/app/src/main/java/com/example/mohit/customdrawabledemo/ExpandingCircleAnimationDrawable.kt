package com.example.mohit.customdrawabledemo

/**
 * Created by Mohit.
 */

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.view.animation.AnimationUtils

class ExpandingCircleAnimationDrawable(private val mRadius: Float) : Drawable(), Animatable, Runnable {

    private val mPaint: Paint

    private var mStartTicks: Long = 0
    private var mIsRunning = false

    init {

        mPaint = Paint()

        mPaint.style = Paint.Style.STROKE
        mPaint.color = Color.RED
        mPaint.isAntiAlias = true
        mPaint.strokeWidth = 2f
    }

    override fun draw(canvas: Canvas) {
        val loopPercent = calculateCurrentLoopPercent()

        val alpha = -(loopPercent * loopPercent) + 1

        mPaint.alpha = (255 * alpha).toInt()

        val radius = loopPercent * mRadius

        val bounds = bounds
        val x = (bounds.right - bounds.left) / 2f + bounds.left
        val y = (bounds.bottom - bounds.top) / 2f + bounds.top

        canvas.drawCircle(x, y, radius, mPaint)
    }

    private fun calculateCurrentLoopPercent(): Float {
        var loopPercent = 0.5f
        if (isRunning) {
            val loopMillis = 5000f
            loopPercent = (AnimationUtils.currentAnimationTimeMillis() - mStartTicks) / loopMillis
            while (loopPercent > 1) {
                loopPercent -= 1f
                mStartTicks += loopMillis.toLong()
            }
        }

        return loopPercent
    }

    override fun run() {
        invalidateSelf()
        scheduleSelf(this, AnimationUtils.currentAnimationTimeMillis() + 1000 / 60)
    }

    override fun isRunning(): Boolean {
        return mIsRunning
    }

    override fun start() {
        if (!isRunning) {
            mIsRunning = true
            mStartTicks = AnimationUtils.currentAnimationTimeMillis()
            run()
        }
    }

    override fun stop() {
        if (isRunning) {
            unscheduleSelf(this)
            mIsRunning = false
        }
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setAlpha(arg0: Int) {
        throw UnsupportedOperationException()
    }

    override fun setColorFilter(arg0: ColorFilter?) {
        throw UnsupportedOperationException()
    }

    override fun getIntrinsicHeight(): Int {
        return (2 * mRadius).toInt()
    }

    override fun getIntrinsicWidth(): Int {
        return (2 * mRadius).toInt()
    }

    override fun getMinimumHeight(): Int {
        return (2 * mRadius).toInt()
    }

    override fun getMinimumWidth(): Int {
        return (2 * mRadius).toInt()
    }
}