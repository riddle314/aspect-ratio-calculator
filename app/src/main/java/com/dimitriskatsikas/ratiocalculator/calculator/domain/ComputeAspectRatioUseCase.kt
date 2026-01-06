package com.dimitriskatsikas.ratiocalculator.calculator.domain

import java.math.BigDecimal
import javax.inject.Inject

class ComputeAspectRatioUseCase @Inject constructor() {

    operator fun invoke(
        originalWidth: String,
        originalHeight: String,
        newWidth: String,
        newHeight: String
    ): Result<String> {
        return runCatching {
            val originalWidthDecimal = originalWidth.toBigDecimalOrThrow()
            val originalHeightDecimal = originalHeight.toBigDecimalOrThrow()

            if (originalWidthDecimal == BigDecimal.ZERO ||
                originalHeightDecimal == BigDecimal.ZERO
            ) {
                throw ZeroInputException()
            }

            var result: String
            if (newWidth.isEmpty()) {
                if (newHeight.isEmpty()) {
                    result = calculateAspectRatio(originalWidthDecimal, originalHeightDecimal)
                } else {
                    val newHeightDecimal = newHeight.toBigDecimalOrThrow()
                    if (newHeightDecimal == BigDecimal.ZERO) {
                        throw ZeroInputException()
                    }
                    result = calculateWidthForAspectRatio(originalWidthDecimal, originalHeightDecimal, newHeightDecimal)
                }
            } else {
                if (newHeight.isNotEmpty()) {
                    throw TargetValuesAllFilledException()
                } else {
                    val newWidthDecimal = newWidth.toBigDecimalOrThrow()
                    if (newWidthDecimal == BigDecimal.ZERO) {
                        throw ZeroInputException()
                    }
                    result = calculateHeightForAspectRatio(originalWidthDecimal, originalHeightDecimal, newWidthDecimal)
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

    private fun calculateWidthForAspectRatio(
        originalWidthDecimal: BigDecimal,
        originalHeightDecimal: BigDecimal,
        newHeightDecimal: BigDecimal
    ): String {
        return "calculateWidthForAspectRatio"
    }

    private fun calculateAspectRatio(
        originalWidthDecimal: BigDecimal,
        originalHeightDecimal: BigDecimal
    ): String {
        return "calculateAspectRatio"
    }

    private fun calculateHeightForAspectRatio(
        originalWidthDecimal: BigDecimal,
        originalHeightDecimal: BigDecimal,
        newWidthDecimal: BigDecimal
    ): String {
        return "calculateHeightForAspectRatio"
    }

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
