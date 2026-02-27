package com.dimitriskatsikas.ratiocalculator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.dimitriskatsikas.calculator.ui.calculator.CalculatorScreen
import com.dimitriskatsikas.info.ui.info.InfoScreen
import com.dimitriskatsikas.navigation.Route

@Composable
fun AppNavigation() {

    val backStack = remember { mutableStateListOf<Route>(Route.Calculator) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Route.Calculator> {
                CalculatorScreen(
                    viewModel = hiltViewModel(),
                    backStack = backStack,
                    adUnitId = BuildConfig.BANNER_AD_UNIT_ID
                )
            }
            entry<Route.Info> {
                InfoScreen(
                    viewModel = hiltViewModel(),
                    backStack = backStack
                )
            }
        }
    )
}
