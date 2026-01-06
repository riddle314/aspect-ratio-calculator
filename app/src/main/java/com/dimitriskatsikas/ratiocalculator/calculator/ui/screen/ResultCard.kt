package com.dimitriskatsikas.ratiocalculator.calculator.ui.screen

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dimitriskatsikas.ratiocalculator.R
import com.dimitriskatsikas.ratiocalculator.calculator.ui.CalculatorView
import com.dimitriskatsikas.ratiocalculator.ui.theme.RatioCalcTheme

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
    val toastMessage = stringResource(id = R.string.calculator_result_copied_toast_message)
    val resultLabel = stringResource(id = R.string.calculator_copied_result_label)
    val formattedResolution = "${result.width} x ${result.height}"
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

                    val clip = ClipData.newPlainText(resultLabel, formattedResolution)
                    clipboardManager?.setPrimaryClip(clip)
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                        Toast.makeText(
                            context,
                            toastMessage,
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.calculator_result_title),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Outlined.ContentCopy,
                    contentDescription = stringResource(R.string.calculator_tap_to_copy_label),
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
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
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.AspectRatio,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            // formatted string showing the ratio source
                            text = stringResource(
                                R.string.calculator_result_content_aspect_ratio,
                                "$originalWidth:$originalHeight",
                                result.aspectRatio
                            ),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .size(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // TODO image placeholder
                    Image(
                        painter = painterResource(id = R.drawable.ic_graph),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
        }
    }
}

@Preview(
    device = Devices.DEFAULT,
    fontScale = 1f
)
@Preview(
    device = Devices.PIXEL_2,
    fontScale = 2f
)
@Preview(
    device = Devices.NEXUS_7,
    fontScale = 1f
)
@Composable
private fun ResultCardCase1Preview() {
    RatioCalcTheme {
        ResultCard(
            result = CalculatorView.State.Result(
                aspectRatio = "1.77",
                width = "1920",
                height = "1080"
            ),
            originalWidth = "16",
            originalHeight = "9"
        )
    }
}

@Preview(
    device = Devices.DEFAULT,
    fontScale = 1f
)
@Preview(
    device = Devices.PIXEL_2,
    fontScale = 2f
)
@Preview(
    device = Devices.NEXUS_7,
    fontScale = 1f
)
@Composable
private fun ResultCardCase2Preview() {
    RatioCalcTheme {
        ResultCard(
            result = CalculatorView.State.Result(
                aspectRatio = "1.77",
                width = "",
                height = ""
            ),
            originalWidth = "16",
            originalHeight = "9"
        )
    }
}
