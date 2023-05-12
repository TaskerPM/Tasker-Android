package com.tasker.android.data.di

import com.tasker.android.data.repository.SmsRepository
import com.tasker.android.data.repository.SmsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun bindsSmsRepository(
        smsRepository: SmsRepositoryImpl,
    ): SmsRepository
}