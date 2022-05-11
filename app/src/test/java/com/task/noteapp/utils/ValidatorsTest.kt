package com.task.noteapp.utils

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ValidatorsTest {

    private lateinit var subject: Validators

    @Before
    fun setUp() {
        subject = Validators()
    }

    @Test
    fun `test isUrlValid return true when url is valid`() {
        val validUrl = "https://www.google.com/example.png"
        assertTrue(subject.isUrlValid(validUrl))
    }

    @Test
    fun `test isUrlValid return false when url is invalid`() {
        val validUrl = "https://www.google.com"
        assertFalse(subject.isUrlValid(validUrl))
    }
}