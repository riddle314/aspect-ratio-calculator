---
name: scaffold-feature
description: Generates a new Android feature module under the feature/ directory following the MVVM, Jetpack Compose, and Hilt architecture guidelines.
metadata:
  author: Aspect Ratio Calculator Team
  keywords:
  - scaffolding
  - module
  - boilerplate
  - generate
  - feature
---
# Scaffold Feature Skill

This skill provides step-by-step instructions and templates for creating a new Android feature module inside the **Aspect Ratio Calculator** project. Use this skill when asked to create a new module, add a feature, or scaffold the initial files for a screen.

## Step 1: Create Directories
Create the following directory structure inside the `feature/` folder:
```
feature/<feature-name>/
├── build.gradle.kts
├── consumer-rules.pro
├── proguard-rules.pro
└── src/
    ├── main/
    │   ├── AndroidManifest.xml
    │   └── kotlin/
    │       └── com/
    │           └── dimitriskatsikas/
    │               └── <feature-name>/
    │                   ├── ui/
    │                   │   └── <feature-name>/
    │                   │       ├── <Feature>Screen.kt
    │                   │       ├── <Feature>View.kt
    │                   │       ├── <Feature>ViewModel.kt
    │                   │       └── components/
    │                   │           └── <Feature>Content.kt
    │                   └── domain/
    └── test/
        └── kotlin/
            └── com/
                └── dimitriskatsikas/
                    └── <feature-name>/
                        └── ui/
                            └── <feature-name>/
                                └── <Feature>ViewModelTest.kt
```

*(Note: `src/androidTest` is omitted per project preferences.)*

---

## Step 2: Configure build.gradle.kts
Create `feature/<feature-name>/build.gradle.kts` using this template:
```kotlin
plugins {
    id("com.dimitriskatsikas.plugins.android-library")
    id("com.dimitriskatsikas.plugins.android-compose")
    id("com.dimitriskatsikas.plugins.android-hilt")
}

android {
    namespace = "com.dimitriskatsikas.ratiocalculator.feature.<feature-name>"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.hilt.navigation.compose)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
    testImplementation(libs.turbine)

    implementation(project(":core:designsystem"))
    implementation(project(":core:navigation"))
    implementation(project(":core:common"))
}
```

Create empty Proguard and consumer files:
* `consumer-rules.pro` (empty)
* `proguard-rules.pro` containing standard library rules.

---

## Step 3: Configure AndroidManifest.xml
Create `feature/<feature-name>/src/main/AndroidManifest.xml` with:
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.dimitriskatsikas.ratiocalculator.feature.<feature-name>">
</manifest>
```

---

## Step 4: Include Module in Settings
Add the following line to `settings.gradle.kts`:
```kotlin
include(":feature:<feature-name>")
```

---

## Step 5: Implement MVVM + UDF Boilerplate

### 1. View / Contract Definitions (`<Feature>View.kt`)
Define state, side effects, and actions:
```kotlin
package com.dimitriskatsikas.<feature-name>.ui.<feature-name>

interface <Feature>View {
    data class State(
        val isLoading: Boolean = false,
        // Add other state variables here
    )

    sealed interface UiAction {
        data object OnBackClicked : UiAction
        // Add other UI actions here
    }

    sealed interface Effect {
        data object NavigateBack : Effect
        // Add other one-time side effects here
    }
}
```

### 2. ViewModel (`<Feature>ViewModel.kt`)
Annotate with `@HiltViewModel` and handle Actions:
```kotlin
package com.dimitriskatsikas.<feature-name>.ui.<feature-name>

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class <Feature>ViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(<Feature>View.State())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = <Feature>View.State()
    )

    private val _effect = Channel<<Feature>View.Effect>(Channel.CONFLATED)
    val effect: Flow<<Feature>View.Effect> = _effect.receiveAsFlow()

    fun onUiAction(action: <Feature>View.UiAction) {
        when (action) {
            <Feature>View.UiAction.OnBackClicked -> sendEffect(<Feature>View.Effect.NavigateBack)
        }
    }

    private fun sendEffect(effect: <Feature>View.Effect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}
```

### 3. Content Composable (`components/<Feature>Content.kt`)
Build the visual layout:
```kotlin
package com.dimitriskatsikas.<feature-name>.ui.<feature-name>.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dimitriskatsikas.<feature-name>.ui.<feature-name>.<Feature>View

@Composable
fun <Feature>Content(
    state: <Feature>View.State,
    onAction: (<Feature>View.UiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Hello from <Feature>")
    }
}
```

### 4. Screen Composable (`<Feature>Screen.kt`)
Observe state flow and process navigation/snackbar effects:
```kotlin
package com.dimitriskatsikas.<feature-name>.ui.<feature-name>

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dimitriskatsikas.<feature-name>.ui.<feature-name>.components.<Feature>Content
import com.dimitriskatsikas.navigation.Route

@Composable
fun <Feature>Screen(
    viewModel: <Feature>ViewModel,
    backStack: SnapshotStateList<Route>
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    <Feature>Content(
        state = state,
        onAction = viewModel::onUiAction
    )

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                <Feature>View.Effect.NavigateBack -> {
                    backStack.removeLastOrNull()
                }
            }
        }
    }
}
```

---

## Step 6: Navigation Setup
1. Define a type-safe route in `core/navigation/src/main/kotlin/com/dimitriskatsikas/navigation/Route.kt`:
   ```kotlin
   @Serializable
   data object <Feature> : Route
   ```
2. Integrate the new route in the app navigation graph (`app/src/main/kotlin/com/dimitriskatsikas/ratiocalculator/AppNavigation.kt`):
   ```kotlin
   entry<Route.<Feature>> {
       <Feature>Screen(
           viewModel = hiltViewModel(),
           backStack = backStack
       )
   }
   ```
3. Add the dependency to `app/build.gradle.kts`:
   ```kotlin
   implementation(project(":feature:<feature-name>"))
   ```
