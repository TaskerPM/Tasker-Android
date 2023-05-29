package com.tasker.android.common.util

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat

fun getDrawableFrom(context: Context, @DrawableRes Id: Int) =
    ResourcesCompat.getDrawable(context.resources, Id, null)

fun getColorFrom(context: Context, @ColorRes Id: Int) =
    context.resources.getColor(Id, null)