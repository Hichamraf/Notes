package com.task.noteapp.utils

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Validators @Inject constructor() {

    fun isUrlValid(url: String): Boolean {
        val imageUrlRegex = "https?://.*\\.(?:png|jpg)".toRegex()
        return imageUrlRegex.matches(url)
    }
}