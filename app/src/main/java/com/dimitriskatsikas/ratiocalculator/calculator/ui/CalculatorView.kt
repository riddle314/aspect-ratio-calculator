package com.dimitriskatsikas.ratiocalculator.calculator.ui

private const val EMPTY_STRING = ""

object CalculatorView {

    data class State(
        val aspectRatioPresets: List<AspectRatioPreset> = AspectRatioPreset.entries,
        val originalWidth: String = EMPTY_STRING,
        val originalHeight: String = EMPTY_STRING,
        val newWidth: String = EMPTY_STRING,
        val newHeight: String = EMPTY_STRING,
        val result: Result? = null,
        val ctaState: CtaState = CtaState.Disabled,
        val isExplainerDialogVisible: Boolean = false
    ) {

        enum class AspectRatioPreset(val width: String, val height: String) {
            RATIO_1_1(width = "1", height = "1"),
            RATIO_4_3(width = "4", height = "3"),
            RATIO_5_4(width = "5", height = "4"),
            RATIO_16_9(width = "16", height = "9"),
            RATIO_21_9(width = "21", height = "9")
        }

        sealed interface CtaState {
            data object Enabled : CtaState
            data object Disabled : CtaState
            data object Loading : CtaState
        }

        data class Result(
            val aspectRatio: String,
            val width: String,
            val height: String
        )
    }

    sealed interface UiAction {
        data class OriginalWidthChange(val value: String) : UiAction
        data class OriginalHeightChange(val value: String) : UiAction
        data class NewWidthChange(val value: String) : UiAction
        data class NewHeightChange(val value: String) : UiAction
        data class SelectAspectRatio(val aspectRatioPreset: State.AspectRatioPreset) : UiAction
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
        data object NoNumberInput : ErrorType
        data object Unknown : ErrorType
    }
}
