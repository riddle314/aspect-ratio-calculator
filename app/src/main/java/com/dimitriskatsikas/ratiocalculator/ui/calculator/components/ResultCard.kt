package com.dimitriskatsikas.ratiocalculator.ui.calculator.components

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AspectRatio
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dimitriskatsikas.ratiocalculator.R
import com.dimitriskatsikas.ratiocalculator.ui.calculator.CalculatorView
import com.dimitriskatsikas.ratiocalculator.ui.theme.RatioCalcTheme
import com.dimitriskatsikas.ratiocalculator.utils.ComponentPreviews

@Composable
fun ResultCard(
    result: CalculatorView.State.Result,
    originalWidth: String,
    originalHeight: String
) {
    val context = LocalContext.current
    val clipboardManager = context.getSystemService(ClipboardManager::class.java)
    val interactionSource = remember { MutableInteractionSource() }
    val haptic = LocalHapticFeedback.current

    val toastMessage = stringResource(id = R.string.calculator_copied_toast_message)
    val resolutionCopyLabel = stringResource(id = R.string.calculator_resolution_copied_label)
    val aspectRatioCopyLabel = stringResource(id = R.string.calculator_aspect_ratio_copied_label)

    val formattedResolution = remember(result.width, result.height) {
        if (result.width.isNotEmpty() && result.height.isNotEmpty()) {
            "${result.width} x ${result.height}"
        } else {
            ""
        }
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(),
                onClick = {
                    val textToCopy: String
                    val copyLabel: String
                    if (formattedResolution.isNotEmpty()) {
                        textToCopy = formattedResolution
                        copyLabel = resolutionCopyLabel
                    } else {
                        textToCopy = result.aspectRatio
                        copyLabel = aspectRatioCopyLabel
                    }
                    val toastText = "$textToCopy $toastMessage"

                    val clip = ClipData.newPlainText(copyLabel, textToCopy)
                    clipboardManager?.setPrimaryClip(clip)
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                        Toast.makeText(
                            context,
                            toastText,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            ),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderRow(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoLeftSide(
                    result = result,
                    formattedResolution = formattedResolution,
                    originalWidth = originalWidth,
                    originalHeight = originalHeight,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                ShapeRightSide(result)
            }
        }
    }
}

@Composable
private fun HeaderRow(modifier: Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.calculator_result_title),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Outlined.ContentCopy,
            contentDescription = stringResource(R.string.calculator_tap_to_copy_label),
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
        )
    }
}

@Composable
private fun InfoLeftSide(
    result: CalculatorView.State.Result,
    formattedResolution: String,
    originalWidth: String,
    originalHeight: String,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        if (result.width.isNotEmpty() && result.height.isNotEmpty()) {
            Text(
                text = stringResource(R.string.calculator_result_content_new_resolution),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
            )

            Text(
                text = formattedResolution,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.AspectRatio,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "$originalWidth:$originalHeight = ${result.aspectRatio}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                )
            }
        } else {
            Text(
                text = stringResource(R.string.calculator_result_content_aspect_ratio),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
            )
            Text(
                text = result.aspectRatio,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun ShapeRightSide(result: CalculatorView.State.Result) {
    Box(
        modifier = Modifier.size(80.dp),
        contentAlignment = Alignment.Center
    ) {
        Rectangle(
            aspectRatio = result.aspectRatio.toFloatOrNull() ?: 1f,
            modifier = Modifier.matchParentSize()
        )
    }
}

@Composable
private fun Rectangle(
    aspectRatio: Float,
    modifier: Modifier
) {
    val shapeColor = MaterialTheme.colorScheme.onPrimaryContainer
    Canvas(modifier = modifier) {
        val boxWidth = size.width
        val boxHeight = size.height

        // calculate the width and height of the box to draw
        var drawWidth = boxWidth
        var drawHeight = boxWidth / aspectRatio

        if (drawHeight > boxHeight) {
            drawHeight = boxHeight
            drawWidth = boxHeight * aspectRatio
        }

        // calculate the offset to center the box
        val offsetX = (boxWidth - drawWidth) / 2
        val offsetY = (boxHeight - drawHeight) / 2

        // draw the rectangle
        drawRoundRect(
            color = shapeColor.copy(alpha = 0.1f),
            topLeft = Offset(offsetX, offsetY),
            size = Size(drawWidth, drawHeight),
            cornerRadius = CornerRadius(8.dp.toPx())
        )
        drawRoundRect(
            style = Stroke(width = 2.dp.toPx()),
            color = shapeColor,
            topLeft = Offset(offsetX, offsetY),
            size = Size(drawWidth, drawHeight),
            cornerRadius = CornerRadius(8.dp.toPx())
        )
    }
}

@ComponentPreviews
@Composable
private fun ResultCardCase1Preview() {
    RatioCalcTheme {
        ResultCard(
            result = CalculatorView.State.Result(
                aspectRatio = "1.78",
                width = "1920",
                height = "1080"
            ),
            originalWidth = "16",
            originalHeight = "9"
        )
    }
}

@ComponentPreviews
@Composable
private fun ResultCardCase2Preview() {
    RatioCalcTheme {
        ResultCard(
            result = CalculatorView.State.Result(
                aspectRatio = "1.78",
                width = "",
                height = ""
            ),
            originalWidth = "16",
            originalHeight = "9"
        )
    }
}
