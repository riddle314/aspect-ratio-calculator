package com.dimitriskatsikas.calculator.ui.calculator.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.dimitriskatsikas.designsystem.R
import com.dimitriskatsikas.calculator.ui.calculator.CalculatorView
import com.dimitriskatsikas.calculator.ui.calculator.CalculatorView.UiAction
import com.dimitriskatsikas.designsystem.theme.RatioCalcTheme
import com.dimitriskatsikas.common.previews.Previews


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorContent(
    state: CalculatorView.State,
    snackbarHostState: SnackbarHostState,
    adUnitId: String,
    onAction: (UiAction) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.full_app_name)) },
                actions = {
                    IconButton(onClick = { onAction(UiAction.ShowExplainerDialog) }) {
                        Icon(
                            imageVector = Icons.Outlined.Lightbulb,
                            contentDescription = stringResource(
                                id = R.string.calculator_help_icon_content_description
                            )
                        )
                    }
                    IconButton(onClick = { onAction(UiAction.OpenInfoScreen) }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(
                                id = R.string.calculator_info_icon_content_description
                            )
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            AdBanner(
                modifier = Modifier.padding(
                    WindowInsets.navigationBars.asPaddingValues()
                ),
                adUnitId = adUnitId
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { paddingValues ->
            Surface(modifier = Modifier.fillMaxSize()) {
                if (state.isExplainerDialogVisible) {
                    ExplainerDialog(
                        onDismissRequest = { onAction(UiAction.DismissExplainerDialog) },
                        modifier = Modifier
                    )
                }
                MainContent(
                    state = state,
                    paddingValues = paddingValues,
                    onAction = onAction
                )
            }
        }
    )
}

@Composable
fun MainContent(
    state: CalculatorView.State,
    paddingValues: PaddingValues,
    onAction: (UiAction) -> Unit
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
            .pointerInput(Unit) {
                detectTapGestures { focusManager.clearFocus() }
            }
    ) {
        Spacer(Modifier.height(24.dp))
        SourceSection(state, onAction)
        Spacer(Modifier.height(24.dp))
        TargetSection(state, onAction)
        Spacer(Modifier.height(24.dp))
        if (state.result != null) {
            ResultCard(
                result = state.result,
                originalWidth = state.originalWidth,
                originalHeight = state.originalHeight
            )
            Spacer(Modifier.height(24.dp))
        }
        CalculateButton(
            state = state,
            onAction = onAction
        )
        Spacer(Modifier.height(12.dp))
        ClearButton(onAction)
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun SourceSection(
    state: CalculatorView.State,
    onAction: (UiAction) -> Unit
) {
    SectionHeader(text = stringResource(R.string.calculator_section_a_header))
    RatioPresets(
        state = state,
        onAction = onAction
    )
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        SourceInputField(
            value = state.originalWidth,
            onValueChange = { newText ->
                onAction(UiAction.OriginalWidthChange(newText))
            },
            label = stringResource(R.string.calculator_input_label_width),
            modifier = Modifier.weight(1f)
        )
        SourceInputField(
            value = state.originalHeight,
            onValueChange = { newText ->
                onAction(UiAction.OriginalHeightChange(newText))
            },
            label = stringResource(R.string.calculator_input_label_height),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun RatioPresets(
    state: CalculatorView.State,
    onAction: (UiAction) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        items(state.aspectRatioPresets) { aspectRatio ->
            AssistChip(
                onClick = { onAction(UiAction.SelectAspectRatio(aspectRatio)) },
                label = {
                    Text(
                        text = "${aspectRatio.width}:${aspectRatio.height}",
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                shape = RoundedCornerShape(8.dp)
            )
        }
    }
}

@Composable
private fun TargetSection(
    state: CalculatorView.State,
    onAction: (UiAction) -> Unit
) {
    SectionHeader(text = stringResource(R.string.calculator_section_b_header))
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        TargetInputField(
            value = state.newWidth,
            onValueChange = { newText ->
                onAction(UiAction.NewWidthChange(newText))
            },
            onDone = {
                if (state.ctaState == CalculatorView.State.CtaState.Enabled) {
                    onAction(UiAction.Calculate)
                }
            },
            label = stringResource(R.string.calculator_input_label_width),
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Outlined.Link,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
        )
        TargetInputField(
            value = state.newHeight,
            onValueChange = { newText ->
                onAction(UiAction.NewHeightChange(newText))
            },
            onDone = {
                if (state.ctaState == CalculatorView.State.CtaState.Enabled) {
                    onAction(UiAction.Calculate)
                }
            },
            label = stringResource(R.string.calculator_input_label_height),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ClearButton(onAction: (UiAction) -> Unit) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = { onAction(UiAction.Clear) },
        enabled = true
    ) {
        Text(text = stringResource(R.string.calculator_clear_fields))
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun CalculateButton(
    state: CalculatorView.State,
    onAction: (UiAction) -> Unit
) {
    val buttonHeight = 50.dp
    val keyboardController = LocalSoftwareKeyboardController.current
    when (state.ctaState) {
        CalculatorView.State.CtaState.Disabled -> Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(buttonHeight),
            onClick = { },
            enabled = false
        ) {
            Text(text = stringResource(R.string.calculator_calculate))
        }

        CalculatorView.State.CtaState.Enabled -> Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(buttonHeight),
            onClick = {
                keyboardController?.hide()
                onAction(UiAction.Calculate)
            }
        ) {
            Text(text = stringResource(R.string.calculator_calculate))
        }

        CalculatorView.State.CtaState.Loading -> Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(buttonHeight),
            onClick = { }
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 3.dp
            )
        }
    }
}

@Composable
private fun SourceInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newText ->
            onValueChange(newText)
        },
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Next
        ),
        modifier = modifier,
        shape = RoundedCornerShape(12.dp)
    )
}

@Composable
private fun TargetInputField(
    value: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit,
    label: String,
    modifier: Modifier
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = value,
        onValueChange = { newText ->
            onValueChange(newText)
        },
        label = { Text(label) },
        placeholder = { Text(stringResource(R.string.calculator_target_input_field_placeholder)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                onDone()
            }
        ),
        modifier = modifier,
        shape = RoundedCornerShape(12.dp)
    )
}

@Previews
@Composable
private fun CalculatorContentPreview(
    @PreviewParameter(CalculatorPreviewStateProvider::class) state: CalculatorView.State
) {
    RatioCalcTheme {
        CalculatorContent(
            state = state,
            snackbarHostState = SnackbarHostState(),
            adUnitId = "dummy",
            onAction = {}
        )
    }
}
