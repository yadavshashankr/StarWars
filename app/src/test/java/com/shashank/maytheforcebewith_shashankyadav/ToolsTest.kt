package com.shashank.maytheforcebewith_shashankyadav

import com.shashank.maytheforcebewith_shashankyadav.utils.Tools.Companion.isSpaceInString
import junit.framework.TestCase
import org.junit.Test

class ToolsTest {
    @Test
    fun spaceRemovalCharTest() {
        TestCase.assertTrue(isSpaceInString("abcd"))
    }

    @Test
    fun spaceRemovalNumTest() {
        TestCase.assertTrue(isSpaceInString("123456"))
    }

    @Test
    fun spaceRemovalStringTest() {
        TestCase.assertFalse(isSpaceInString("n m j k l"))
    }
}