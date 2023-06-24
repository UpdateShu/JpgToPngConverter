package com.geekbrains.jpgtopngconverter.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

import android.graphics.Bitmap

@StateStrategyType(AddToEndStrategy::class)
interface ImgsView : MvpView {
    fun pickImage()
    fun setConvertedImage(bmp: Bitmap, path: String)
    fun showError(message: String)
}