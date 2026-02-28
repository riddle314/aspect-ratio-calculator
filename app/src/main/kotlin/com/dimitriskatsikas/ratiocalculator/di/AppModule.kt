package com.dimitriskatsikas.ratiocalculator.di

import com.dimitriskatsikas.ratiocalculator.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.dimitriskatsikas.common.VersionName

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @VersionName
    fun provideVersionName(): String = BuildConfig.VERSION_NAME
}
