package com.dimitriskatsikas.calculator.ui.calculator

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dimitriskatsikas.designsystem.R
import com.dimitriskatsikas.navigation.Route
import com.dimitriskatsikas.calculator.ui.calculator.components.CalculatorContent

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel,
    backStack: SnapshotStateList<Route>,
    adUnitId: String
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val zeroInputErrorMessage = stringResource(id = R.string.calculator_error_zero_input)
    val targetValuesAllFilledErrorMessage = stringResource(id = R.string.calculator_error_target_values_all_filled)
    val noNumberInputErrorMessage = stringResource(id = R.string.calculator_error_no_numbers_input)
    val unKnownErrorMessage = stringResource(id = R.string.calculator_error_unknown)

    CalculatorContent(
        state = state,
        snackbarHostState = snackbarHostState,
        adUnitId = adUnitId,
        onAction = viewModel::onUiAction
    )

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            handleEffect(
                effect = effect,
                backStack = backStack,
                snackbarHostState = snackbarHostState,
                zeroInputErrorMessage = zeroInputErrorMessage,
                targetValuesAllFilledErrorMessage = targetValuesAllFilledErrorMessage,
                noNumberInputErrorMessage = noNumberInputErrorMessage,
                unKnownErrorMessage = unKnownErrorMessage
            )
        }
    }
}

private suspend fun handleEffect(
    effect: CalculatorView.Effect,
    backStack: SnapshotStateList<Route>,
    snackbarHostState: SnackbarHostState,
    zeroInputErrorMessage: String,
    targetValuesAllFilledErrorMessage: String,
    noNumberInputErrorMessage: String,
    unKnownErrorMessage: String
) {
    when (effect) {
        is CalculatorView.Effect.OpenInfoScreen -> {
            backStack.add(Route.Info)
        }

        is CalculatorView.Effect.ShowErrorToast -> {
            when (effect.errorType) {
                CalculatorView.ErrorType.ZeroInput -> {
                    snackbarHostState.showSnackbar(zeroInputErrorMessage)
                }

                CalculatorView.ErrorType.TargetValuesAllFilled -> {
                    snackbarHostState.showSnackbar(targetValuesAllFilledErrorMessage)
                }

                CalculatorView.ErrorType.NoNumberInput -> {
                    snackbarHostState.showSnackbar(noNumberInputErrorMessage)
                }

                CalculatorView.ErrorType.Unknown -> {
                    snackbarHostState.showSnackbar(unKnownErrorMessage)
                }
            }
        }
    }
}
