package com.jfinex.collection.ui.dialog.tally

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfinex.collection.data.local.features.user.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TallyViewModel @Inject constructor(
    private val tallyRepo: TallyRepo,
    private val userRepo: UserRepo
) : ViewModel() {

    private val _exportState = MutableStateFlow<ExportState>(ExportState.Idle)
    val exportState: StateFlow<ExportState> = _exportState.asStateFlow()

    val user = userRepo.getUser().stateIn(
        viewModelScope, SharingStarted.Eagerly, null
    )

    fun export(uri: Uri, contentResolver: ContentResolver) {
        viewModelScope.launch {
            try {
                _exportState.value = ExportState.Loading
                val exportedUri = tallyRepo.exportTally(uri, contentResolver)
                _exportState.value = ExportState.ShareReady(exportedUri)
            } catch (e: Exception) {
                _exportState.value = ExportState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun resetState() {
        _exportState.value = ExportState.Idle
    }

}

sealed class ExportState {
    object Idle : ExportState()
    object Loading : ExportState()
    data class Error(val message: String) : ExportState()
    data class ShareReady(val uri: Uri) : ExportState()
}