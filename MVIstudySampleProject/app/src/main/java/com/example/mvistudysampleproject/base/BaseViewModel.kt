package com.example.mvistudysampleproject.base

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseViewModel<I : MviIntent, S : MviState> : ViewModel(), MviBaseViewModel<I, S> {
    private val intentMutableFlow = MutableSharedFlow<I>()
    final override suspend fun processIntent(intent: I) = intentMutableFlow.emit(intent)
    protected val intentFlow: SharedFlow<I> get() = intentMutableFlow
}
