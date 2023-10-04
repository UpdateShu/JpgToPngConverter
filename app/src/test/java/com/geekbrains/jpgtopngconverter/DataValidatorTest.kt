package com.geekbrains.jpgtopngconverter

import com.geekbrains.jpgtopngconverter.mvp.presenter.DataUtils
import org.junit.Assert
import org.junit.Test

class DataValidatorTest {

    @Test
    fun dataValidator_pathIsValid_ReturnsNotNull() {
        Assert.assertNotNull(DataUtils.getValidatePath("Picture.jpg"))
    }

    @Test
    fun dataValidator_pathIsInvalid_ReturnsNull() {
        Assert.assertNull(DataUtils.getValidatePath(""))
    }

    @Test
    fun dataValidator_formatIsValid_ReturnEquals() {
        Assert.assertEquals(DataUtils.getImageFormat("Picture.jpg"), "jpg")
    }

    @Test
    fun dataValidator_formatIsInValid_ReturnNotEquals() {
        Assert.assertNotEquals(DataUtils.getImageFormat("Picture.png"), "jpg")
    }
}