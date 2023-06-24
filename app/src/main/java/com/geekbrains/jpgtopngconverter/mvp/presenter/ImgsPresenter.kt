package com.geekbrains.jpgtopngconverter.mvp.presenter

import moxy.MvpPresenter
import com.geekbrains.jpgtopngconverter.mvp.ImageConverter
import com.geekbrains.jpgtopngconverter.mvp.view.ImgsView
import android.graphics.Bitmap

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ImgsPresenter(): MvpPresenter<ImgsView>(), IImgsPresenter {

    override var imgSelectionListener: (() -> Unit)? = null
    override var imgConversionListener: ((Bitmap, String) -> Unit)? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        imgSelectionListener = {
            viewState.pickImage()
        }
        imgConversionListener = { bmp, path ->
            val converterDisposable = CompositeDisposable()
            converterDisposable.add(
                ImageConverter.convertJpgToPng(bmp, path)
                    .delay(3, TimeUnit.SECONDS)
                    .cache()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        viewState.setConvertedImage(it.second, it.first)
                    }, {
                        viewState.showError(it.message.toString())
                    })
            )
        }
    }
}