package com.serial.decoder.core.di

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.serial.decoder.core.util.dataStore
import com.serial.decoder.feature_decoding.data.local.source.DataStoreManagement
import com.serial.decoder.feature_decoding.data.local.source.SerialDecoderDataStore
import com.serial.decoder.feature_onboarding.data.local.SharedPreferenceManagement
import com.serial.decoder.feature_onboarding.data.local.SharedPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPrefModule {

    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            SharedPreferenceManager.PREF,
            Context.MODE_PRIVATE
        )
    }


    @Provides
    @Singleton
    fun provideSharedPrefManager(sharedPreferences: SharedPreferences): SharedPreferenceManagement {
        return SharedPreferenceManager(sharedPreferences)
    }

    @Provides
    @Singleton
    fun providesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideSharedPrefDataStoreManager(dataStore: DataStore<Preferences>): DataStoreManagement {
        return SerialDecoderDataStore(dataStore)
    }
}