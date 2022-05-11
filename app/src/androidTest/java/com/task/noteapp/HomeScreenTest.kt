package com.task.noteapp

import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @Test
    fun test_add_note_navigates_to_detailFragment() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

    }
}