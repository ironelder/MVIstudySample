package com.example.mvistudysampleproject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

public fun <T> defer(flowFactory: suspend () -> Flow<T>): Flow<T> = flow { emitAll(flowFactory()) }
