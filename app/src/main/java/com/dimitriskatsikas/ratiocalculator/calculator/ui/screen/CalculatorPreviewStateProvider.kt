package com.dimitriskatsikas.ratiocalculator.calculator.ui.screen

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.dimitriskatsikas.ratiocalculator.calculator.ui.CalculatorView
import com.dimitriskatsikas.ratiocalculator.calculator.ui.CalculatorView.State.CtaState

class CalculatorPreviewStateProvider : PreviewParameterProvider<CalculatorView.State> {

    override val values = sequenceOf(
        CalculatorView.State(
            originalWidth = "",
            originalHeight = "",
            newWidth = "",
            newHeight = "",
            result = "",
            ctaState = CtaState.Disabled
        ),
        CalculatorView.State(
            originalWidth = "",
            originalHeight = "",
            newWidth = "",
            newHeight = "",
            result = "",
            ctaState = CtaState.Disabled,
            isExplainerDialogVisible = true
        ),
        CalculatorView.State(
            originalWidth = "1920",
            originalHeight = "",
            newWidth = "",
            newHeight = "",
            result = "",
            ctaState = CtaState.Disabled
        ),
        CalculatorView.State(
            originalWidth = "",
            originalHeight = "1080",
            newWidth = "",
            newHeight = "",
            result = "",
            ctaState = CtaState.Disabled
        ),
        CalculatorView.State(
            originalWidth = "16",
            originalHeight = "9",
            newWidth = "",
            newHeight = "",
            result = "",
            ctaState = CtaState.Enabled
        ),
        CalculatorView.State(
            originalWidth = "16",
            originalHeight = "9",
            newWidth = "1920",
            newHeight = "",
            result = "",
            ctaState = CtaState.Enabled
        ),
        CalculatorView.State(
            originalWidth = "16",
            originalHeight = "9",
            newWidth = "",
            newHeight = "",
            result = "",
            ctaState = CtaState.Loading
        ),
        CalculatorView.State(
            originalWidth = "16",
            originalHeight = "9",
            newWidth = "1920",
            newHeight = "",
            result = "",
            ctaState = CtaState.Loading
        ),
        CalculatorView.State(
            originalWidth = "16",
            originalHeight = "9",
            newWidth = "1920",
            newHeight = "",
            result = "The outcome is 1080",
            ctaState = CtaState.Enabled
        ),
        CalculatorView.State(
            originalWidth = "16",
            originalHeight = "9",
            newWidth = "",
            newHeight = "1080",
            result = "The outcome is 1920",
            ctaState = CtaState.Enabled
        ),
        CalculatorView.State(
            originalWidth = "1920",
            originalHeight = "1080",
            newWidth = "",
            newHeight = "",
            result = "The outcome is only aspect ratio",
            ctaState = CtaState.Enabled
        )
    )
}
