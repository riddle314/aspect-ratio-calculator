package com.dimitriskatsikas.ratiocalculator.calculator.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimitriskatsikas.ratiocalculator.calculator.domain.ComputeAspectRatioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val EMPTY_STRING = ""

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val computeAspectRatioUseCase: ComputeAspectRatioUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CalculatorView.State())

    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CalculatorView.State()
    )

    private val _effect: Channel<CalculatorView.Effect> = Channel(Channel.BUFFERED)
    val effect: Flow<CalculatorView.Effect> = _effect.receiveAsFlow()

    fun onUiAction(action: CalculatorView.UiAction) {
        when (action) {
            is CalculatorView.UiAction.Calculate -> calculate()
            CalculatorView.UiAction.Clear -> clearState()
            CalculatorView.UiAction.OpenInfoScreen -> {
                _effect.trySend(CalculatorView.Effect.OpenInfoScreen)
            }

            CalculatorView.UiAction.DismissExplainerDialog -> setExplainerDialogVisibility(false)
            CalculatorView.UiAction.ShowExplainerDialog -> setExplainerDialogVisibility(true)
            is CalculatorView.UiAction.SelectRatioPreset -> onSelectPreset(action.aspectRatioPreset)
            is CalculatorView.UiAction.SourceHeightChange -> onSourceHeightChange(action.value)
            is CalculatorView.UiAction.SourceWidthChange -> onSourceWidthChange(action.value)
            is CalculatorView.UiAction.TargetHeightChange -> onTargetHeightChange(action.value)
            is CalculatorView.UiAction.TargetWidthChange -> onTargetWidthChange(action.value)
        }
    }

    private fun calculate() {
        _state.update {
            it.copy(
                result = EMPTY_STRING,
                ctaState = CalculatorView.State.CtaState.Loading
            )
        }
        viewModelScope.launch {
            val result = withContext(Dispatchers.Default) {
                val currentState = _state.value
                computeAspectRatioUseCase(
                    sourceWidth = currentState.sourceWidth,
                    sourceHeight = currentState.sourceHeight,
                    targetWidth = currentState.targetWidth,
                    targetHeight = currentState.targetHeight
                )
            }

            result.onSuccess { value ->
                _state.update {
                    it.copy(
                        result = value,
                        ctaState = CalculatorView.State.CtaState.Enabled
                    )
                }
            }.onFailure { exception ->
                when (exception) {
                    is ComputeAspectRatioUseCase.ZeroInputException -> handleZeroInputException()
                    is ComputeAspectRatioUseCase.TargetValuesAllFilledException -> handleTargetValuesAllFilledException()
                    else -> Unit
                }
            }
        }
    }

    private fun handleTargetValuesAllFilledException() {
        _state.update {
            it.copy(
                targetWidth = EMPTY_STRING,
                targetHeight = EMPTY_STRING,
                result = EMPTY_STRING
            )
        }
        _effect.trySend(
            CalculatorView.Effect.ShowErrorToast(
                CalculatorView.ErrorType.TargetValuesAllFilled
            )
        )
    }

    private fun handleZeroInputException() {
        _state.update {
            it.copy(
                result = EMPTY_STRING,
            )
        }
        _effect.trySend(
            CalculatorView.Effect.ShowErrorToast(
                CalculatorView.ErrorType.ZeroInput
            )
        )
    }

    private fun onSelectPreset(preset: CalculatorView.State.AspectRatioPreset) {

    }

    private fun onSourceWidthChange(value: String) {
        val areSectionAFieldsFilledWithNumbers = _state.value.sourceHeight.toBigDecimalOrNull() != null &&
                value.toBigDecimalOrNull() != null

        _state.update {
            it.copy(
                sourceWidth = value,
                selectedRatioPreset = CalculatorView.State.AspectRatioPreset.NONE,
                ctaState = if (areSectionAFieldsFilledWithNumbers) {
                    CalculatorView.State.CtaState.Enabled
                } else {
                    CalculatorView.State.CtaState.Disabled
                },
                result = EMPTY_STRING
            )
        }
    }

    private fun onSourceHeightChange(value: String) {
        val areSectionAFieldsFilledWithNumbers = _state.value.sourceWidth.toBigDecimalOrNull() != null &&
                value.toBigDecimalOrNull() != null

        _state.update {
            it.copy(
                sourceHeight = value,
                selectedRatioPreset = CalculatorView.State.AspectRatioPreset.NONE,
                ctaState = if (areSectionAFieldsFilledWithNumbers) {
                    CalculatorView.State.CtaState.Enabled
                } else {
                    CalculatorView.State.CtaState.Disabled
                },
                result = EMPTY_STRING
            )
        }
    }

    private fun onTargetWidthChange(value: String) {
        _state.update {
            it.copy(
                targetWidth = value,
                targetHeight = "",
                result = EMPTY_STRING
            )
        }
    }

    private fun onTargetHeightChange(value: String) {
        _state.update {
            it.copy(
                targetWidth = "",
                targetHeight = value,
                result = EMPTY_STRING
            )
        }
    }

    private fun clearState() {
        _state.update {
            it.copy(
                sourceWidth = EMPTY_STRING,
                sourceHeight = EMPTY_STRING,
                targetWidth = EMPTY_STRING,
                targetHeight = EMPTY_STRING,
                selectedRatioPreset = CalculatorView.State.AspectRatioPreset.NONE,
                result = EMPTY_STRING,
                ctaState = CalculatorView.State.CtaState.Disabled
            )
        }
    }

    private fun setExplainerDialogVisibility(visible: Boolean) {
        _state.update {
            it.copy(
                isExplainerDialogVisible = visible
            )
        }
    }
}
