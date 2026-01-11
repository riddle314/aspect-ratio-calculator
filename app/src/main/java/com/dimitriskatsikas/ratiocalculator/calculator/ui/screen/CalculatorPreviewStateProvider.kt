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
            result = null,
            ctaState = CtaState.Disabled
        ),
        CalculatorView.State(
            originalWidth = "",
            originalHeight = "",
            newWidth = "",
            newHeight = "",
            result = null,
            ctaState = CtaState.Disabled,
            isExplainerDialogVisible = true
        ),
        CalculatorView.State(
            originalWidth = "1920",
            originalHeight = "",
            newWidth = "",
            newHeight = "",
            result = null,
            ctaState = CtaState.Disabled
        ),
        CalculatorView.State(
            originalWidth = "",
            originalHeight = "1080",
            newWidth = "",
            newHeight = "",
            result = null,
            ctaState = CtaState.Disabled
        ),
        CalculatorView.State(
            originalWidth = "16",
            originalHeight = "9",
            newWidth = "",
            newHeight = "",
            result = null,
            ctaState = CtaState.Enabled
        ),
        CalculatorView.State(
            originalWidth = "16",
            originalHeight = "9",
            newWidth = "1920",
            newHeight = "",
            result = null,
            ctaState = CtaState.Enabled
        ),
        CalculatorView.State(
            originalWidth = "16",
            originalHeight = "9",
            newWidth = "",
            newHeight = "",
            result = null,
            ctaState = CtaState.Loading
        ),
        CalculatorView.State(
            originalWidth = "16",
            originalHeight = "9",
            newWidth = "1920",
            newHeight = "",
            result = null,
            ctaState = CtaState.Loading
        ),
        CalculatorView.State(
            originalWidth = "16",
            originalHeight = "9",
            newWidth = "",
            newHeight = "1080",
            result = CalculatorView.State.Result(
                aspectRatio = "1.77",
                width = "1920",
                height = "1080"
            ),
            ctaState = CtaState.Enabled
        ),
        // previews for store screenshots
        CalculatorView.State(
            originalWidth = "16",
            originalHeight = "9",
            newWidth = "1920",
            newHeight = "",
            result = CalculatorView.State.Result(
                aspectRatio = "1.77",
                width = "1920",
                height = "1080"
            ),
            ctaState = CtaState.Enabled
        ),
        CalculatorView.State(
            originalWidth = "1920",
            originalHeight = "1080",
            newWidth = "",
            newHeight = "",
            result = CalculatorView.State.Result(
                aspectRatio = "1.77",
                width = "",
                height = ""
            ),
            ctaState = CtaState.Enabled
        ),
        CalculatorView.State(
            originalWidth = "4",
            originalHeight = "3",
            newWidth = "",
            newHeight = "3000",
            result = CalculatorView.State.Result(
                aspectRatio = "1.33",
                width = "4000",
                height = "3000"
            ),
            ctaState = CtaState.Enabled
        ),
        CalculatorView.State(
            originalWidth = "9",
            originalHeight = "16",
            newWidth = "1080",
            newHeight = "",
            result = CalculatorView.State.Result(
                aspectRatio = "0.56",
                width = "1080",
                height = "1920"
            ),
            ctaState = CtaState.Enabled
        ),
    )
}
