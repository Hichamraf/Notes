package com.task.noteapp.di

import android.content.Context
import androidx.room.Room
import com.task.noteapp.data.NoteRepository
import com.task.noteapp.data.local.AppDatabase
import com.task.noteapp.data.local.NoteDao
import com.task.noteapp.data.local.NoteRepoLocalImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository = NoteRepoLocalImpl(noteDao)

    @Provides
    fun provideLocalDB(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "notes-db"
    ).build()

    @Provides
    fun provideNoteDao(appDatabase: AppDatabase): NoteDao = appDatabase.noteDao()
}