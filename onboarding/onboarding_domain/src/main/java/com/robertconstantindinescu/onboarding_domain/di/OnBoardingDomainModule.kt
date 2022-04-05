package com.robertconstantindinescu.onboarding_domain.di

import com.robertconstantindinescu.onboarding_domain.use_case.ValidateNutrients
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(/*SingletonComponent::class*/ViewModelComponent::class) //this dependency will survive ass long as the viewmodel does
object OnBoardingDomainModule {

    @Provides
    @ViewModelScoped
    //@Singleton
    fun provideValidateNutrientsUseCase(): ValidateNutrients{
        return ValidateNutrients()
    }
}