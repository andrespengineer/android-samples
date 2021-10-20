package com.social.hilt

import android.content.Context
import com.social.data.local.PreferencesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun providePreferencesDataSource(@ApplicationContext context: Context) : PreferencesDataSource {
        return PreferencesDataSource(context)
    }
}