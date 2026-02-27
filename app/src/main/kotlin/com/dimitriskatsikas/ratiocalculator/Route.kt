package com.dimitriskatsikas.ratiocalculator

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Calculator : Route

    @Serializable
    data object Info : Route
}
