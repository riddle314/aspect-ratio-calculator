package com.dimitriskatsikas.info.ui.info

import app.cash.turbine.test
import com.dimitriskatsikas.common.dispatchers.AppDispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class InfoViewModelTest {

    private lateinit var testClass: InfoViewModel

    private val versionName = "1.0.0-TEST"

    @BeforeEach
    fun setUp() {
        val testDispatcher = UnconfinedTestDispatcher()
        val testDispatchers = object : AppDispatchers {
            override val io = testDispatcher
            override val default = testDispatcher
            override val main = testDispatcher
        }
        testClass = InfoViewModel(
            versionName = versionName,
            dispatchers = testDispatchers
        )
    }

    @Test
    fun `when viewModel is initialized, then state contains version name`() = runTest {
        testClass.state.test {
            val expectedState = InfoView.State(versionName = versionName)
            assertEquals(expectedState, awaitItem())
        }
    }

    @Test
    fun `when UiAction is OnBackClicked, then navigateBack`() = runTest {
        testClass.onUiAction(InfoView.UiAction.OnBackClicked)

        testClass.effect.test {
            assertEquals(InfoView.Effect.NavigateBack, awaitItem())
        }
    }

    @Test
    fun `when UiAction is OnPolicyClicked, then navigateToPolicy`() = runTest {
        val policyUrl = "https://example.com/policy"
        testClass.onUiAction(InfoView.UiAction.OnPolicyClicked(url = policyUrl))

        testClass.effect.test {
            assertEquals(InfoView.Effect.NavigateToPolicy(url = policyUrl), awaitItem())
        }
    }

    @Test
    fun `when UiAction is OnRateClicked, then navigateToRate`() = runTest {
        val rateUrl = "https://example.com/rate"
        testClass.onUiAction(InfoView.UiAction.OnRateClicked(url = rateUrl))

        testClass.effect.test {
            assertEquals(InfoView.Effect.NavigateToRate(url = rateUrl), awaitItem())
        }
    }
}
