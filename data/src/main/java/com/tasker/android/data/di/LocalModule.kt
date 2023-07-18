package com.tasker.android.data.di

import android.content.Context
import androidx.room.Room
import com.tasker.android.data.room.LocalDatabase
import com.tasker.android.data.room.LocalTaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideLocalDataBase(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(context, LocalDatabase::class.java, "local_db").build()
    }

    @Provides
    @Singleton
    fun provideLocalTaskDao(localDatabase: LocalDatabase): LocalTaskDao {
        return localDatabase.localTaskDao()
    }
}