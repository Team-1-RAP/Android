package com.team1.simplebank.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.team1.simplebank.data.dataStore.Auth
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataStoreModule = module {

    singleOf(::provideDatastore)
    singleOf(::Auth)
}


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_session")
fun provideDatastore(context: Context): DataStore<Preferences> {
    return context.dataStore
}