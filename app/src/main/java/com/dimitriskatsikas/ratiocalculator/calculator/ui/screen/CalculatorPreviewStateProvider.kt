package com.dimitriskatsikas.ratiocalculator.calculator.ui.screen

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.dimitriskatsikas.ratiocalculator.calculator.ui.CalculatorView
import com.dimitriskatsikas.ratiocalculator.calculator.ui.CalculatorView.State.CtaState

class CalculatorPreviewStateProvider : PreviewParameterProvider<CalculatorView.State> {

    override val values = sequenceOf(
        CalculatorView.State(
            sourceWidth = "",
            sourceHeight = "",
            targetWidth = "",
            targetHeight = "",
            result = "",
            ctaState = CtaState.Disabled
        ),
        CalculatorView.State(
            sourceWidth = "",
            sourceHeight = "",
            targetWidth = "",
            targetHeight = "",
            result = "",
            ctaState = CtaState.Disabled,
            isExplainerDialogVisible = true
        ),
        CalculatorView.State(
            sourceWidth = "1920",
            sourceHeight = "",
            targetWidth = "",
            targetHeight = "",
            result = "",
            ctaState = CtaState.Disabled
        ),
        CalculatorView.State(
            sourceWidth = "",
            sourceHeight = "1080",
            targetWidth = "",
            targetHeight = "",
            result = "",
            ctaState = CtaState.Disabled
        ),
        CalculatorView.State(
            sourceWidth = "16",
            sourceHeight = "9",
            targetWidth = "",
            targetHeight = "",
            result = "",
            ctaState = CtaState.Enabled
        ),
        CalculatorView.State(
            sourceWidth = "16",
            sourceHeight = "9",
            targetWidth = "1920",
            targetHeight = "",
            result = "",
            selectedRatioPreset = CalculatorView.State.AspectRatioPreset.RATIO_16_9,
            ctaState = CtaState.Enabled
        ),
        CalculatorView.State(
            sourceWidth = "16",
            sourceHeight = "9",
            targetWidth = "",
            targetHeight = "",
            result = "",
            ctaState = CtaState.Loading
        ),
        CalculatorView.State(
            sourceWidth = "16",
            sourceHeight = "9",
            targetWidth = "1920",
            targetHeight = "",
            result = "",
            ctaState = CtaState.Loading
        ),
        CalculatorView.State(
            sourceWidth = "16",
            sourceHeight = "9",
            targetWidth = "1920",
            targetHeight = "",
            result = "The outcome is 1080",
            selectedRatioPreset = CalculatorView.State.AspectRatioPreset.RATIO_16_9,
            ctaState = CtaState.Enabled
        ),
        CalculatorView.State(
            sourceWidth = "16",
            sourceHeight = "9",
            targetWidth = "",
            targetHeight = "1080",
            result = "The outcome is 1920",
            selectedRatioPreset = CalculatorView.State.AspectRatioPreset.RATIO_16_9,
            ctaState = CtaState.Enabled
        ),
        CalculatorView.State(
            sourceWidth = "1920",
            sourceHeight = "1080",
            targetWidth = "",
            targetHeight = "",
            result = "The outcome is only aspect ratio",
            ctaState = CtaState.Enabled
        )
    )
}
