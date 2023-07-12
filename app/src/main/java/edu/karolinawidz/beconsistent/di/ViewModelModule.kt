package edu.karolinawidz.beconsistent.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.karolinawidz.beconsistent.BeConsistentApplication
import edu.karolinawidz.beconsistent.database.HabitDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Provides
    @Singleton
    fun provideDao(application: Application): HabitDao {
        return (application as BeConsistentApplication).database.dao()
    }
}