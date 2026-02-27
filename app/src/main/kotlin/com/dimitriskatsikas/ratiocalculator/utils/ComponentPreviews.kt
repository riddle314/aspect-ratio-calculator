package com.dimitriskatsikas.ratiocalculator.utils

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

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
annotation class ComponentPreviews
