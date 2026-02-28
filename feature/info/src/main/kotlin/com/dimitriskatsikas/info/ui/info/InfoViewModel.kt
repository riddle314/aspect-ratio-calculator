package com.dimitriskatsikas.info.ui.info

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import com.dimitriskatsikas.common.VersionName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    @VersionName versionName: String
) : ViewModel() {

    val state: StateFlow<InfoView.State> = MutableStateFlow(InfoView.State(versionName = versionName))

    private val _effect: Channel<InfoView.Effect> = Channel(Channel.BUFFERED)
    val effect: Flow<InfoView.Effect> = _effect.receiveAsFlow()

    fun onUiAction(action: InfoView.UiAction) {
        when (action) {
            InfoView.UiAction.OnBackClicked -> _effect.trySend(
                InfoView.Effect.NavigateBack
            )

            is InfoView.UiAction.OnPolicyClicked -> _effect.trySend(
                InfoView.Effect.NavigateToPolicy(url = action.url)
            )

            is InfoView.UiAction.OnRateClicked -> _effect.trySend(
                InfoView.Effect.NavigateToRate(url = action.url)
            )
        }
    }
}
