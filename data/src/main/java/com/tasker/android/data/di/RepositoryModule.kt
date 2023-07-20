package com.tasker.android.data.di

import com.tasker.android.data.repository.LocalTaskRepositoryImpl
import com.tasker.android.domain.repository.SmsRepository
import com.tasker.android.data.repository.SmsRepositoryImpl
import com.tasker.android.data.repository.TaskRepositoryImpl
import com.tasker.android.data.repository.UserRepositoryImpl
import com.tasker.android.domain.repository.LocalTaskRepository
import com.tasker.android.domain.repository.TaskRepository
import com.tasker.android.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindsSmsRepository(
        smsRepositoryImpl: SmsRepositoryImpl,
    ): SmsRepository

    @Singleton
    @Binds
    abstract fun bindsLocalTaskRepository(
        localTaskRepositoryImpl: LocalTaskRepositoryImpl,
    ): LocalTaskRepository

    @Singleton
    @Binds
    abstract fun bindsUserRepository(
        userRepositoryImpl: UserRepositoryImpl,
    ): UserRepository

    @Singleton
    @Binds
    abstract fun bindsTaskRepository(
        taskRepositoryImpl: TaskRepositoryImpl,
    ): TaskRepository
}