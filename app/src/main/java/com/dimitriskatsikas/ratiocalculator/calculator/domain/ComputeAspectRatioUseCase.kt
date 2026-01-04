package com.dimitriskatsikas.ratiocalculator.calculator.domain

import javax.inject.Inject

class ComputeAspectRatioUseCase @Inject constructor() {

    operator fun invoke(
        originalWidth: String,
        originalHeight: String,
        newWidth: String,
        newHeight: String
    ): Result<String> {
        return runCatching {
            "Result is"
        }.recoverCatching {
            when (it) {
                is ZeroInputException -> throw it
                is TargetValuesAllFilledException -> throw it
                else -> throw it
            }
        }
    }

    class ZeroInputException() : Exception(
        "zero input is not allowed"
    )

    class TargetValuesAllFilledException() : Exception(
        "New dimensions section numbers should not all be filled"
    )
}
