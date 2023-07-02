package io.drdroid.assignment2.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module //states that current class is a module
@InstallIn(SingletonComponent::class) // informs the scope of class or items inside
class AppModule {
}