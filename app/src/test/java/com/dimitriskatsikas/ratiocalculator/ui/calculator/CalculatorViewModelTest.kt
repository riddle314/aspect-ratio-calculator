package com.dimitriskatsikas.ratiocalculator.ui.calculator

import app.cash.turbine.test
import com.dimitriskatsikas.ratiocalculator.domain.AspectRatioCalculator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@OptIn(ExperimentalCoroutinesApi::class)
class CalculatorViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var testClass: CalculatorViewModel

    @BeforeAll
    fun setupAll() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterAll
    fun tearDownAll() {
        Dispatchers.resetMain()
    }

    @BeforeEach
    fun setUp() {
        testClass = CalculatorViewModel(
            aspectRatioCalculator = AspectRatioCalculator()
        )
    }

    @Test
    fun `when viewModel is initialized, then state is empty`() =
        runTest {
            testClass.state.test {
                assertEquals(CalculatorView.State(), awaitItem())
            }
        }

    @Test
    fun `when UiAction is OpenInfoScreen, then openInfoScreen effect is sent`() =
        runTest {
            testClass.effect.test {
                testClass.onUiAction(CalculatorView.UiAction.OpenInfoScreen)
                assertEquals(CalculatorView.Effect.OpenInfoScreen, awaitItem())
            }
        }

    @Test
    fun `when UiAction is DismissExplainerDialog, then isExplainerDialogVisible is false`() =
        runTest {
            testClass.onUiAction(CalculatorView.UiAction.ShowExplainerDialog)
            testClass.onUiAction(CalculatorView.UiAction.DismissExplainerDialog)

            testClass.state.test {
                assertFalse(expectMostRecentItem().isExplainerDialogVisible)
            }
        }

    @Test
    fun `when UiAction is ShowExplainerDialog, then isExplainerDialogVisible is true`() =
        runTest {
            testClass.onUiAction(CalculatorView.UiAction.ShowExplainerDialog)

            testClass.state.test {
                assertTrue(expectMostRecentItem().isExplainerDialogVisible)
            }
        }

    @Test
    fun `when UiAction is OriginalWidthChange, then update originalWidth and ctaState`() =
        runTest {
            testClass.onUiAction(CalculatorView.UiAction.OriginalWidthChange("1920"))

            testClass.state.test {
                val state = expectMostRecentItem()
                Assertions.assertEquals("1920", state.originalWidth)
                assertEquals(CalculatorView.State.CtaState.Disabled, state.ctaState)
            }
        }

    @Test
    fun `when UiAction is OriginalHeightChange, then update originalHeight and ctaState`() =
        runTest {
            testClass.onUiAction(CalculatorView.UiAction.OriginalHeightChange("1080"))

            testClass.state.test {
                val state = expectMostRecentItem()
                Assertions.assertEquals("1080", state.originalHeight)
                assertEquals(CalculatorView.State.CtaState.Disabled, state.ctaState)
            }
        }

    @Test
    fun `when UiAction is OriginalWidthChange and OriginalHeightChange, then update originalWidth, originalHeight and ctaState to enabled`() =
        runTest {
            testClass.onUiAction(CalculatorView.UiAction.OriginalWidthChange("1920"))

            testClass.state.test {
                val state = expectMostRecentItem()
                Assertions.assertEquals("1920", state.originalWidth)
                assertEquals(CalculatorView.State.CtaState.Disabled, state.ctaState)
            }

            testClass.onUiAction(CalculatorView.UiAction.OriginalHeightChange("1080"))
            testClass.state.test {
                val state = expectMostRecentItem()
                Assertions.assertEquals("1080", state.originalHeight)
                assertEquals(CalculatorView.State.CtaState.Enabled, state.ctaState)
            }
        }

    @Test
    fun `when OriginalWidthChange with no number and OriginalHeightChange with number, then cta remains disabled`() =
        runTest {
            testClass.onUiAction(CalculatorView.UiAction.OriginalWidthChange("abc"))
            testClass.onUiAction(CalculatorView.UiAction.OriginalHeightChange("1080"))

            testClass.state.test {
                assertEquals(CalculatorView.State.CtaState.Disabled, expectMostRecentItem().ctaState)
            }
        }

    @Test
    fun `when OriginalWidthChange with number and OriginalHeightChange with no number, then cta remains disabled`() =
        runTest {
            testClass.onUiAction(CalculatorView.UiAction.OriginalWidthChange("1920"))
            testClass.onUiAction(CalculatorView.UiAction.OriginalHeightChange("abc"))

            testClass.state.test {
                assertEquals(CalculatorView.State.CtaState.Disabled, expectMostRecentItem().ctaState)
            }
        }

    @Test
    fun `given original dimensions are empty, when UiAction is NewHeightChange, then update newHeight, clear newWidth and cta remains disabled`() =
        runTest {
            testClass.onUiAction(CalculatorView.UiAction.NewHeightChange("720"))

            testClass.state.test {
                val state = expectMostRecentItem()
                Assertions.assertEquals("720", state.newHeight)
                Assertions.assertEquals("", state.newWidth)
                assertEquals(CalculatorView.State.CtaState.Disabled, state.ctaState)
            }
        }

    @Test
    fun `given original dimensions are empty, when UiAction is NewWidthChange, then update newWidth, clear newHeight and and cta remains disabled`() =
        runTest {
            testClass.onUiAction(CalculatorView.UiAction.NewWidthChange("1280"))

            testClass.state.test {
                val state = expectMostRecentItem()
                Assertions.assertEquals("", state.newHeight)
                Assertions.assertEquals("1280", state.newWidth)
                assertEquals(CalculatorView.State.CtaState.Disabled, state.ctaState)
            }
        }

    @Test
    fun `given original width, height and new height are filled, when UiAction is NewWidthChange, then update newWidth, clear newHeight and ctaState is enabled`() =
        runTest {
            testClass.onUiAction(CalculatorView.UiAction.OriginalWidthChange("1920"))
            testClass.onUiAction(CalculatorView.UiAction.OriginalHeightChange("1080"))
            testClass.onUiAction(CalculatorView.UiAction.NewHeightChange("720"))

            testClass.onUiAction(CalculatorView.UiAction.NewWidthChange("1280"))

            testClass.state.test {
                val state = expectMostRecentItem()
                Assertions.assertEquals("1280", state.newWidth)
                Assertions.assertEquals("", state.newHeight)
                assertEquals(CalculatorView.State.CtaState.Enabled, state.ctaState)
            }
        }

    @Test
    fun `given original width, height and new width are filled, when UiAction is NewHeightChange, then update newHeight, clear newWidth and ctaState is enabled`() =
        runTest {
            testClass.onUiAction(CalculatorView.UiAction.OriginalWidthChange("1920"))
            testClass.onUiAction(CalculatorView.UiAction.OriginalHeightChange("1080"))
            testClass.onUiAction(CalculatorView.UiAction.NewWidthChange("1280"))

            testClass.onUiAction(CalculatorView.UiAction.NewHeightChange("720"))

            testClass.state.test {
                val state = expectMostRecentItem()
                Assertions.assertEquals("", state.newWidth)
                Assertions.assertEquals("720", state.newHeight)
                assertEquals(CalculatorView.State.CtaState.Enabled, state.ctaState)
            }
        }

    @Test
    fun `given original width and height filled, when NewWidthChange with no number, then cta remains disabled`() =
        runTest {
            testClass.onUiAction(CalculatorView.UiAction.OriginalWidthChange("1920"))
            testClass.onUiAction(CalculatorView.UiAction.OriginalHeightChange("1080"))
            testClass.onUiAction(CalculatorView.UiAction.NewWidthChange("abc"))

            testClass.state.test {
                assertEquals(CalculatorView.State.CtaState.Disabled, expectMostRecentItem().ctaState)
            }
        }

    @Test
    fun `given original width and height filled, when NewHeightChange with no number, then cta remains disabled`() =
        runTest {
            testClass.onUiAction(CalculatorView.UiAction.OriginalWidthChange("1920"))
            testClass.onUiAction(CalculatorView.UiAction.OriginalHeightChange("1080"))
            testClass.onUiAction(CalculatorView.UiAction.NewHeightChange("abc"))

            testClass.state.test {
                assertEquals(CalculatorView.State.CtaState.Disabled, expectMostRecentItem().ctaState)
            }
        }

    @Test
    fun `when UiAction is SelectAspectRatio, then update originalWidth, originalHeight and ctaState`() =
        runTest {
            val preset = CalculatorView.State.AspectRatioPreset.RATIO_16_9
            testClass.onUiAction(CalculatorView.UiAction.SelectAspectRatio(preset))

            testClass.state.test {
                val state = expectMostRecentItem()
                assertEquals(preset.width, state.originalWidth)
                assertEquals(preset.height, state.originalHeight)
                assertEquals(CalculatorView.State.CtaState.Enabled, state.ctaState)
            }
        }

    @Test
    fun `when UiAction is Clear, then clear state`() =
        runTest {
            testClass.onUiAction(CalculatorView.UiAction.OriginalWidthChange("1920"))
            testClass.onUiAction(CalculatorView.UiAction.OriginalHeightChange("1080"))
            testClass.onUiAction(CalculatorView.UiAction.NewWidthChange("1280"))
            testClass.onUiAction(CalculatorView.UiAction.Clear)

            testClass.state.test {
                val state = expectMostRecentItem()
                Assertions.assertEquals("", state.originalWidth)
                Assertions.assertEquals("", state.originalHeight)
                Assertions.assertEquals("", state.newWidth)
                Assertions.assertEquals("", state.newHeight)
                Assertions.assertNull(state.result)
                assertEquals(CalculatorView.State.CtaState.Disabled, state.ctaState)
            }
        }

    @Test
    fun `when UiAction is Calculate and it is successful, then update result`() =
        runTest {
            testClass.onUiAction(CalculatorView.UiAction.OriginalWidthChange("4"))
            testClass.onUiAction(CalculatorView.UiAction.OriginalHeightChange("3"))
            testClass.onUiAction(CalculatorView.UiAction.NewWidthChange("800"))

            testClass.state.test {
                skipItems(1) // Skip state after inputs
                testClass.onUiAction(CalculatorView.UiAction.Calculate)

                assertEquals(CalculatorView.State.CtaState.Loading, awaitItem().ctaState)

                val successState = awaitItem()
                Assertions.assertEquals("1.33", successState.result?.aspectRatio)
                Assertions.assertEquals("800", successState.result?.width)
                Assertions.assertEquals("600", successState.result?.height)
                assertEquals(CalculatorView.State.CtaState.Enabled, successState.ctaState)
            }
        }

    @Test
    fun `when UiAction is Calculate and it fails with ZeroInput, then show error toast`() =
        runTest {
            testClass.onUiAction(CalculatorView.UiAction.OriginalWidthChange("0"))
            testClass.onUiAction(CalculatorView.UiAction.OriginalHeightChange("3"))

            testClass.effect.test {
                testClass.onUiAction(CalculatorView.UiAction.Calculate)
                assertEquals(
                    CalculatorView.Effect.ShowErrorToast(CalculatorView.ErrorType.ZeroInput),
                    awaitItem()
                )
            }
        }

    // this test should not happen because the cta should be disabled,
    // but if calculate could run the result will be the following
    @Test
    fun `when UiAction is Calculate and it fails with NoNumbersInput, then show error toast`() =
        runTest {
            testClass.onUiAction(CalculatorView.UiAction.OriginalWidthChange("abc"))
            testClass.onUiAction(CalculatorView.UiAction.OriginalHeightChange("3"))

            testClass.effect.test {
                testClass.onUiAction(CalculatorView.UiAction.Calculate)
                assertEquals(
                    CalculatorView.Effect.ShowErrorToast(CalculatorView.ErrorType.NoNumberInput),
                    awaitItem()
                )
            }
        }
}
