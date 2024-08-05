package com.team1.simplebank.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.synrgy.xdomain.repositoryInterface.IAuthRepository
import com.synrgy.xdomain.repositoryInterface.IUserRepository
import com.synrgy.xdomain.repositoryInterface.MutationRepository
import com.team1.simplebank.data.BuildConfig
import com.team1.simplebank.data.dataStore.AuthDataStore
import com.team1.simplebank.data.remote.api.ApiService
import com.team1.simplebank.data.repositoryImpl.AuthRepositoryImpl
import com.team1.simplebank.data.repositoryImpl.MutationRepositoryImpl
import com.team1.simplebank.data.repositoryImpl.UserRepositoryImpl
import com.team1.simplebank.data.repositoryImpl.pagingsource.MutationPagingSource
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
    fun provideApiService(authDataStore: AuthDataStore): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val authInterceptor = Interceptor { chain ->
            val request = chain.request()
            // Skip authInterceptor for login request
            if (request.url.encodedPath.contains("v1/auth/login")) {
                return@Interceptor chain.proceed(request)
            }

            val token = runBlocking {
                authDataStore.getUserSession().first().accessToken
            }
            val requestHeaders = request.newBuilder()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(requestHeaders)
        }

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(0, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.SECONDS)
            .writeTimeout(0, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
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
    ) : IUserRepository {
        return UserRepositoryImpl(apiService,authDataStore)
    }

    @Singleton
    @Provides
    fun provideMutationRepository(
        apiService: ApiService, authDataStore: AuthDataStore
    ):MutationRepository{
        return MutationRepositoryImpl(apiService,authDataStore)
    }



}