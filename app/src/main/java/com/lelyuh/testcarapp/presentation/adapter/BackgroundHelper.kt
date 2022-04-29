package com.lelyuh.testcarapp.presentation.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import com.google.android.material.shape.MaterialShapeDrawable
import com.lelyuh.testcarapp.R

/**
 * Helper for use custom background for list item depends on it even/odd position
 * For even position uses gray stroke, for odd - purple
 *
 * @author Leliukh Aleksandr
 */
object BackgroundHelper {

    private const val CORNER_SIZE = 16f
    private const val STROKE_WITH = 2f

    fun coloredListBackground(context: Context, isEvenItem: Boolean): Drawable =
        MaterialShapeDrawable().apply {
            fillColor = ColorStateList.valueOf(Color.TRANSPARENT)
            setCornerSize(CORNER_SIZE)
            setStroke(
                STROKE_WITH,
                context.getColor(
                    if (isEvenItem) R.color.gray else R.color.purple_200
                )
            )
        }
}