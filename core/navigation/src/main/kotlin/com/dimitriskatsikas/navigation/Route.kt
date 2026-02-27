package com.dimitriskatsikas.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Calculator : Route

    @Serializable
    data object Info : Route
}
