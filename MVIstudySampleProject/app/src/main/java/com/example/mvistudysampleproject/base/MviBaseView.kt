package com.example.mvistudysampleproject.base

import kotlinx.coroutines.flow.Flow

interface MviBaseView<I : MviIntent, in S : MviState> {
    fun viewIntents(): Flow<I>
    fun render(state: S)
}
