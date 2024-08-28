package com.team1.simplebank.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.synrgy.xdomain.repositoryInterface.IAuthRepository
import com.synrgy.xdomain.repositoryInterface.IQrisRepository
import com.synrgy.xdomain.repositoryInterface.IUserRepository
import com.synrgy.xdomain.repositoryInterface.MutationRepository
import com.synrgy.xdomain.repositoryInterface.TransferRepository
import com.team1.simplebank.data.BuildConfig
import com.team1.simplebank.data.dataStore.AuthDataStore
import com.team1.simplebank.data.local.SimpleBankDatabase
import com.team1.simplebank.data.local.TransferDao
import com.team1.simplebank.data.remote.api.ApiService
import com.team1.simplebank.data.remote.api.FSW.ApiServiceFromFSW
import com.team1.simplebank.data.repositoryImpl.AuthRepositoryImpl
import com.team1.simplebank.data.repositoryImpl.MutationRepositoryImpl
import com.team1.simplebank.data.repositoryImpl.TransferRepositoryImpl
import com.team1.simplebank.data.repositoryImpl.QrisRepositoryImpl
import com.team1.simplebank.data.repositoryImpl.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_session")

    @Singleton
    @Provides
    fun providesContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Singleton
    @Provides
    fun provideAuthDataStore(dataStore: DataStore<Preferences>): AuthDataStore {
        return AuthDataStore(dataStore)
    }

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): SimpleBankDatabase {
        return Room.databaseBuilder(
            context, SimpleBankDatabase::class.java, "transferDao.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideTransferDao(db:SimpleBankDatabase):TransferDao{
        return db.transferDao()
    }


    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }


    @Singleton
    @Provides
    fun provideAuthInterceptor(authDataStore: AuthDataStore) = Interceptor {
        val request = it.request()
        if (request.url.encodedPath.contains("v1/auth/login")) {
            return@Interceptor it.proceed(request)
        }
        val token = runBlocking {
            authDataStore.getUserSession().first().accessToken
        }
        val requestHeaders = request.newBuilder()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer $token")
            .build()
        it.proceed(requestHeaders)
    }

    @Singleton
    @Provides
    fun provideClientOkHttp(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(0, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.SECONDS)
            .writeTimeout(0, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiServiceFromBEJ(client: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideApiServiceFromFSW(client: OkHttpClient): ApiServiceFromFSW {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_FSW)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiServiceFromFSW::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        apiService: ApiService,
        authDataStore: AuthDataStore,
    ): IAuthRepository {
        return AuthRepositoryImpl(apiService, authDataStore)
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        apiService: ApiService,
        authDataStore: AuthDataStore
    ): IUserRepository {
        return UserRepositoryImpl(apiService, authDataStore)
    }

    @Singleton
    @Provides
    fun provideMutationRepository(
        apiService: ApiService, authDataStore: AuthDataStore
    ): MutationRepository {
        return MutationRepositoryImpl(apiService, authDataStore)
    }

    @Singleton
    @Provides
    fun provideNewAccountTransferRepository(
        apiService: ApiService,
        apiServiceFromFSW: ApiServiceFromFSW,
        transferDao: TransferDao
    ): TransferRepository {
        return TransferRepositoryImpl(apiService, apiServiceFromFSW,transferDao)
    }

    @Singleton
    @Provides
    fun provideQrisRepository(
        apiService: ApiService
    ): IQrisRepository {
        return QrisRepositoryImpl(apiService)
    }




}