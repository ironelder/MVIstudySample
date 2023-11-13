package com.example.mvistudysampleproject.base

import kotlinx.coroutines.flow.StateFlow

interface MviBaseViewModel<I : MviIntent, S : MviState> {
    val viewState: StateFlow<S>
    suspend fun processIntent(intent: I)
}
