package com.inspirecoding.roombasics.di

import android.app.Application
import com.inspirecoding.roombasics.repository.ToDoRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RoomDatabaseModule
{
    @Singleton
    @Provides
    fun providesDatabase (application: Application) = ToDoRoomDatabase.getDatabase(application)

    @Singleton
    @Provides
    fun providesCurrentWeatherDao (database: ToDoRoomDatabase) = database.toDoDao()
}