package com.geekbrains.jpgtopngconverter.mvp.presenter

import android.graphics.Bitmap

interface IImgsPresenter {
    var imgSelectionListener: (() -> Unit)?
    var imgConversionListener: ((Bitmap, String) -> Unit)?
}