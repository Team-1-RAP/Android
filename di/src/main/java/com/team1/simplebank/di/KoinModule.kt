package com.team1.simplebank.di

import com.synrgy.xdomain.repositoryInterface.AuthRepository
import com.synrgy.xdomain.useCase.auth.LoginUseCase
import com.synrgy.xdomain.useCase.auth.GetSessionUseCase
import com.synrgy.xdomain.useCase.auth.ClearSessionUseCase
import com.team1.simplebank.data.repositoryImpl.AuthRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val koinModule = module {

    //provide ctx to koin
    single { androidContext() }

    //repo
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }

    //useCase
    singleOf(::LoginUseCase)
    singleOf(::GetSessionUseCase)
    singleOf(::ClearSessionUseCase)


}