package com.dimitriskatsikas.ratiocalculator.calculator.ui

private const val EMPTY_STRING = ""

object CalculatorView {

    data class State(
        val originalWidth: String = EMPTY_STRING,
        val originalHeight: String = EMPTY_STRING,
        val newWidth: String = EMPTY_STRING,
        val newHeight: String = EMPTY_STRING,
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
        data class OriginalWidthChange(val value: String) : UiAction
        data class OriginalHeightChange(val value: String) : UiAction
        data class NewWidthChange(val value: String) : UiAction
        data class NewHeightChange(val value: String) : UiAction
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
