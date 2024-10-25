package com.example.mvinoteapptests_ny.note_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvinoteapptests_ny.core.domain.model.NoteItem
import com.example.mvinoteapptests_ny.note_list.domain.use_case.DeleteNoteUseCase
import com.example.mvinoteapptests_ny.note_list.domain.use_case.LoadNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val loadNotesUseCase: LoadNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    private val _noteListState = MutableStateFlow<List<NoteItem>>(emptyList())
    val noteListState = _noteListState.asStateFlow()

    private val _orderNotesByTitle = MutableStateFlow(false)
    val orderNotesByTitle = _orderNotesByTitle.asStateFlow()


    fun loadNotes() {
        viewModelScope.launch {
            _noteListState.update {
                loadNotesUseCase.invoke(isOrderByTitle = orderNotesByTitle.value)
            }
        }
    }

    fun deleteNote(noteItem: NoteItem) {
        viewModelScope.launch {
            deleteNoteUseCase.invoke(noteItem)
            loadNotes()
        }
    }

    fun changeOrder() {
        viewModelScope.launch {
            _orderNotesByTitle.update { !it }
            loadNotes()
        }
    }
}