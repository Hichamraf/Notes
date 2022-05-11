package com.task.noteapp.utils

import kotlinx.coroutines.CoroutineDispatcher

object DispatchersImpl : Dispatchers {

    override val io: CoroutineDispatcher
        get() = kotlinx.coroutines.Dispatchers.IO

    override val main: CoroutineDispatcher
        get() = kotlinx.coroutines.Dispatchers.Main
}