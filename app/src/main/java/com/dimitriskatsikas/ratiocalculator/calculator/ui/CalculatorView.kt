package com.dimitriskatsikas.ratiocalculator.calculator.ui

private const val EMPTY_STRING = ""

object CalculatorView {

    data class State(
        val sourceWidth: String = EMPTY_STRING,
        val sourceHeight: String = EMPTY_STRING,
        val targetWidth: String = EMPTY_STRING,
        val targetHeight: String = EMPTY_STRING,
        val selectedRatioPreset: AspectRatioPreset = AspectRatioPreset.NONE,
        val result: String = EMPTY_STRING,
        val ctaState: CtaState = CtaState.Disabled,
        val isExplainerDialogVisible: Boolean = false
    ) {

        enum class AspectRatioPreset {
            NONE,
            RATIO_1_1,
            RATIO_4_3,
            RATIO_16_9,
            RATIO_21_9
        }

        sealed interface CtaState {
            data object Enabled : CtaState
            data object Disabled : CtaState
            data object Loading : CtaState
        }
    }

    sealed interface UiAction {
        data class SourceWidthChange(val value: String) : UiAction
        data class SourceHeightChange(val value: String) : UiAction
        data class TargetWidthChange(val value: String) : UiAction
        data class TargetHeightChange(val value: String) : UiAction
        data class SelectRatioPreset(val aspectRatioPreset: State.AspectRatioPreset) : UiAction
        data object Calculate : UiAction
        data object Clear : UiAction
        data object OpenInfoScreen : UiAction
        data object ShowExplainerDialog : UiAction
        data object DismissExplainerDialog : UiAction
    }

    sealed interface Effect {
        data object OpenInfoScreen : Effect
        data class ShowErrorToast(val errorType: ErrorType) : Effect
    }

    sealed interface ErrorType {
        data object ZeroInput : ErrorType
        data object TargetValuesAllFilled : ErrorType
    }
}
