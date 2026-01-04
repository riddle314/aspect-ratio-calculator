package com.dimitriskatsikas.ratiocalculator.calculator.ui.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dimitriskatsikas.ratiocalculator.R
import com.dimitriskatsikas.ratiocalculator.Route
import com.dimitriskatsikas.ratiocalculator.calculator.ui.CalculatorView
import com.dimitriskatsikas.ratiocalculator.calculator.ui.CalculatorViewModel

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel,
    backStack: SnapshotStateList<Route>
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val zeroInputErrorMessage = stringResource(id = R.string.error_zero_input)
    val targetValuesAllFilledErrorMessage = stringResource(id = R.string.error_target_values_all_filled)


    CalculatorContent(
        state = state,
        snackbarHostState = snackbarHostState,
        onAction = viewModel::onUiAction
    )

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            handleEffect(
                effect = effect,
                backStack = backStack,
                snackbarHostState = snackbarHostState,
                zeroInputErrorMessage = zeroInputErrorMessage,
                targetValuesAllFilledErrorMessage = targetValuesAllFilledErrorMessage
            )
        }
    }
}

private suspend fun handleEffect(
    effect: CalculatorView.Effect,
    backStack: SnapshotStateList<Route>,
    snackbarHostState: SnackbarHostState,
    zeroInputErrorMessage: String,
    targetValuesAllFilledErrorMessage: String
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
            }
        }
    }
}
