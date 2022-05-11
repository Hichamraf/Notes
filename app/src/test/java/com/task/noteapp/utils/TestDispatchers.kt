package com.task.noteapp.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher

object TestDispatchers : Dispatchers {
    private val dispatcher = TestCoroutineDispatcher()
    override val io: CoroutineDispatcher
        get() = dispatcher
    override val main: CoroutineDispatcher
        get() = dispatcher
}