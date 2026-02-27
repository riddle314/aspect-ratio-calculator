package com.dimitriskatsikas.calculator.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AspectRatioCalculatorTest {

    private lateinit var testClass: AspectRatioCalculator

    @BeforeEach
    fun setUp() {
        testClass = AspectRatioCalculator()
    }

    @Test
    fun `given original dimensions, when no new dimensions provided, then return aspect ratio and empty new dimensions`() {
        val result = testClass(
            originalWidth = "4",
            originalHeight = "3",
            newWidth = "",
            newHeight = ""
        )

        assertTrue(result.isSuccess)
        val value = result.getOrThrow()
        Assertions.assertEquals("1.33", value.aspectRatio)
        Assertions.assertEquals("", value.width)
        Assertions.assertEquals("", value.height)
    }

    @Test
    fun `given original dimensions and new width, then calculate new height`() {
        val result = testClass(
            originalWidth = "16",
            originalHeight = "9",
            newWidth = "1920",
            newHeight = ""
        )

        assertTrue(result.isSuccess)
        val value = result.getOrThrow()
        Assertions.assertEquals("1.78", value.aspectRatio)
        Assertions.assertEquals("1920", value.width)
        Assertions.assertEquals("1080", value.height)
    }

    @Test
    fun `given original dimensions and new height, then calculate new width`() {
        val result = testClass(
            originalWidth = "4",
            originalHeight = "3",
            newWidth = "",
            newHeight = "600"
        )

        assertTrue(result.isSuccess)
        val value = result.getOrThrow()
        Assertions.assertEquals("1.33", value.aspectRatio)
        Assertions.assertEquals("800", value.width)
        Assertions.assertEquals("600", value.height)
    }

    @Test
    fun `given original dimensions with decimals and new width, then calculate new height`() {
        val result = testClass(
            originalWidth = "1.5",
            originalHeight = "1",
            newWidth = "300",
            newHeight = ""
        )

        assertTrue(result.isSuccess)
        val value = result.getOrThrow()
        Assertions.assertEquals("1.5", value.aspectRatio)
        Assertions.assertEquals("300", value.width)
        Assertions.assertEquals("200", value.height)
    }

    @Test
    fun `given original dimensions and new width decimal, then calculate new height that is not decimal and give back width as no decimal`() {
        val result = testClass(
            originalWidth = "16",
            originalHeight = "9",
            newWidth = "1980.1",
            newHeight = ""
        )

        assertTrue(result.isSuccess)
        val value = result.getOrThrow()
        Assertions.assertEquals("1.78", value.aspectRatio)
        Assertions.assertEquals("1980", value.width)
        Assertions.assertEquals("1114", value.height)
    }

    @Test
    fun `given original dimensions and new width, then calculate new height that is not decimal`() {
        val result = testClass(
            originalWidth = "16",
            originalHeight = "9",
            newWidth = "1980",
            newHeight = ""
        )

        assertTrue(result.isSuccess)
        val value = result.getOrThrow()
        Assertions.assertEquals("1.78", value.aspectRatio)
        Assertions.assertEquals("1980", value.width)
        Assertions.assertEquals("1114", value.height)
    }

    @Test
    fun `given original dimensions and new height, then calculate new width that is not decimal`() {
        val result = testClass(
            originalWidth = "16",
            originalHeight = "9",
            newWidth = "",
            newHeight = "1114"
        )

        assertTrue(result.isSuccess)
        val value = result.getOrThrow()
        Assertions.assertEquals("1.78", value.aspectRatio)
        Assertions.assertEquals("1980", value.width)
        Assertions.assertEquals("1114", value.height)
    }

    @Test
    fun `given original dimensions and new height decimal, then calculate new width that is not decimal and give back height as no decimal`() {
        val result = testClass(
            originalWidth = "16",
            originalHeight = "9",
            newWidth = "",
            newHeight = "1113.89"
        )

        assertTrue(result.isSuccess)
        val value = result.getOrThrow()
        Assertions.assertEquals("1.78", value.aspectRatio)
        Assertions.assertEquals("1980", value.width)
        Assertions.assertEquals("1114", value.height)
    }

    @Test
    fun `given original width is zero, then return failure with ZeroInputException`() {
        val result = testClass(
            originalWidth = "0",
            originalHeight = "100",
            newWidth = "100",
            newHeight = ""
        )

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is AspectRatioCalculator.ZeroInputException)
    }

    @Test
    fun `given original height is zero, then return failure with ZeroInputException`() {
        val result = testClass(
            originalWidth = "100",
            originalHeight = "0",
            newWidth = "100",
            newHeight = ""
        )

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is AspectRatioCalculator.ZeroInputException)
    }

    @Test
    fun `given new width is zero, then return failure with ZeroInputException`() {
        val result = testClass(
            originalWidth = "4",
            originalHeight = "3",
            newWidth = "0",
            newHeight = ""
        )

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is AspectRatioCalculator.ZeroInputException)
    }

    @Test
    fun `given new height is zero, then return failure with ZeroInputException`() {
        val result = testClass(
            originalWidth = "4",
            originalHeight = "3",
            newWidth = "",
            newHeight = "0"
        )

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is AspectRatioCalculator.ZeroInputException)
    }

    @Test
    fun `given both new width and new height are provided, then return failure with TargetValuesAllFilledException`() {
        val result = testClass(
            originalWidth = "4",
            originalHeight = "3",
            newWidth = "800",
            newHeight = "600"
        )

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is AspectRatioCalculator.TargetValuesAllFilledException)
    }

    @Test
    fun `given non-numeric input for original width, then return failure with NoNumbersInputException`() {
        val result = testClass(
            originalWidth = "abc",
            originalHeight = "3",
            newWidth = "800",
            newHeight = ""
        )

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is AspectRatioCalculator.NoNumbersInputException)
    }

    @Test
    fun `given non-numeric input for original height, then return failure with NoNumbersInputException`() {
        val result = testClass(
            originalWidth = "3",
            originalHeight = "abc",
            newWidth = "800",
            newHeight = ""
        )

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is AspectRatioCalculator.NoNumbersInputException)
    }

    @Test
    fun `given non-numeric input for new width, then return failure with NoNumbersInputException`() {
        val result = testClass(
            originalWidth = "4",
            originalHeight = "3",
            newWidth = "def",
            newHeight = ""
        )

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is AspectRatioCalculator.NoNumbersInputException)
    }

    @Test
    fun `given non-numeric input for new height, then return failure with NoNumbersInputException`() {
        val result = testClass(
            originalWidth = "4",
            originalHeight = "3",
            newWidth = "",
            newHeight = "def"
        )

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is AspectRatioCalculator.NoNumbersInputException)
    }

    @Test
    fun `given original dimensions, when aspect ratio has many decimals, then round it correctly`() {
        val result = testClass(
            originalWidth = "10",
            originalHeight = "3",
            newWidth = "",
            newHeight = ""
        )

        assertTrue(result.isSuccess)
        val value = result.getOrThrow()
        Assertions.assertEquals("3.33", value.aspectRatio)
    }
}
