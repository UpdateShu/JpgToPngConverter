package com.geekbrains.jpgtopngconverter.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndStrategy::class)
interface MainViewImpl : MvpView {
    fun checkPermissions()
}