package com.dimitriskatsikas.ratiocalculator.calculator.domain

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import javax.inject.Inject

private const val EMPTY_STRING = ""

class AspectRatioCalculator @Inject constructor() {

    operator fun invoke(
        originalWidth: String,
        originalHeight: String,
        newWidth: String,
        newHeight: String
    ): Result<AspectRatioResult> {
        return runCatching {
            val originalWidthDecimal = originalWidth.toBigDecimalOrThrow()
            val originalHeightDecimal = originalHeight.toBigDecimalOrThrow()

            if (originalWidthDecimal == BigDecimal.ZERO ||
                originalHeightDecimal == BigDecimal.ZERO
            ) {
                throw ZeroInputException()
            }

            var result: AspectRatioResult
            if (newWidth.isEmpty()) {
                if (newHeight.isEmpty()) {
                    result = calculaterAspectRatio(
                        originalWidthDecimal = originalWidthDecimal,
                        originalHeightDecimal = originalHeightDecimal,
                        givenDimensionType = GivenDimensionType.None
                    )
                } else {
                    val newHeightDecimal = newHeight.toBigDecimalOrThrow()
                    if (newHeightDecimal == BigDecimal.ZERO) {
                        throw ZeroInputException()
                    }
                    result = calculaterAspectRatio(
                        originalWidthDecimal = originalWidthDecimal,
                        originalHeightDecimal = originalHeightDecimal,
                        givenDimensionType = GivenDimensionType.Height(newHeightDecimal)
                    )
                }
            } else {
                if (newHeight.isNotEmpty()) {
                    throw TargetValuesAllFilledException()
                } else {
                    val newWidthDecimal = newWidth.toBigDecimalOrThrow()
                    if (newWidthDecimal == BigDecimal.ZERO) {
                        throw ZeroInputException()
                    }
                    result = calculaterAspectRatio(
                        originalWidthDecimal = originalWidthDecimal,
                        originalHeightDecimal = originalHeightDecimal,
                        givenDimensionType = GivenDimensionType.Width(newWidthDecimal)
                    )
                }
            }

            result
        }.recoverCatching {
            when (it) {
                is ZeroInputException -> throw it
                is TargetValuesAllFilledException -> throw it
                is NoNumbersInputException -> throw it
                else -> throw it
            }
        }
    }

    private fun String.toBigDecimalOrThrow(): BigDecimal {
        return toBigDecimalOrNull() ?: throw NoNumbersInputException()
    }

    private fun calculaterAspectRatio(
        originalWidthDecimal: BigDecimal,
        originalHeightDecimal: BigDecimal,
        givenDimensionType: GivenDimensionType
    ): AspectRatioResult {
        val aspectRatio = originalWidthDecimal.divide(
            originalHeightDecimal,
            MathContext.DECIMAL128
        )
        val resultWidth: String
        val resultHeight: String
        when (givenDimensionType) {
            is GivenDimensionType.Height -> {
                // calculate new width
                resultWidth = formatAsWholeNumber(givenDimensionType.height * aspectRatio)
                resultHeight = formatAsWholeNumber(givenDimensionType.height)
            }

            is GivenDimensionType.Width -> {
                // calculate new height
                resultHeight = formatAsWholeNumber(
                    givenDimensionType.width.divide(
                        aspectRatio,
                        MathContext.DECIMAL128
                    )
                )
                resultWidth = formatAsWholeNumber(givenDimensionType.width)
            }

            GivenDimensionType.None -> {
                resultHeight = EMPTY_STRING
                resultWidth = EMPTY_STRING
            }
        }

        return AspectRatioResult(
            aspectRatio = formatBigDecimal(aspectRatio),
            width = resultWidth,
            height = resultHeight
        )
    }

    private sealed interface GivenDimensionType {
        data class Height(val height: BigDecimal) : GivenDimensionType
        data class Width(val width: BigDecimal) : GivenDimensionType
        data object None : GivenDimensionType
    }

    /**
     * This format ensures:
     * - Maximum 2 decimal places and rounding
     * - Removes trailing zeros (e.g., 5.50 -> 5.5)
     * - Get a plain string representation without scientific notation (e.g., 1.2E7)
     *
     * @param value The value to format
     * @return The formatted string
     */
    private fun formatBigDecimal(value: BigDecimal): String = value
        .setScale(2, RoundingMode.HALF_UP)
        .stripTrailingZeros()
        .toPlainString()

    /**
     * This format big decimal to integer number and returns it as a string
     */
    private fun formatAsWholeNumber(value: BigDecimal): String = value
        .setScale(0, RoundingMode.HALF_UP)
        .toPlainString()

    class NoNumbersInputException() : Exception(
        "Input fields must contain only valid numbers."
    )

    class ZeroInputException() : Exception(
        "zero numbers are not allowed"
    )

    class TargetValuesAllFilledException() : Exception(
        "New dimensions section numbers should not all be filled"
    )
}
