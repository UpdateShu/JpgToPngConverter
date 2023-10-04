package com.geekbrains.jpgtopngconverter

import com.geekbrains.jpgtopngconverter.ui.DataUtils
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
}