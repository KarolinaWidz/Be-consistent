package edu.karolinawidz.beconsistent.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.karolinawidz.beconsistent.data.repository.HabitRepository
import edu.karolinawidz.beconsistent.data.repository.HabitRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindHabitRepository(repositoryImpl: HabitRepositoryImpl): HabitRepository
}