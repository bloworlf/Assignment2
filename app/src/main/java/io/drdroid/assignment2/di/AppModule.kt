package io.drdroid.assignment2.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.drdroid.assignment2.data.repo.Repository
import io.drdroid.assignment2.data.repo.RepositoryImpl
import io.drdroid.assignment2.network.MovieCall
import io.drdroid.assignment2.network.TvShowCall
import io.drdroid.assignment2.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module //states that current class is a module
@InstallIn(SingletonComponent::class) // informs the scope of class or items inside
class AppModule {

    @Provides
    fun providesOkHttpInstance(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    fun providesRetrofitInstance(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun providesTvShowApi(retrofit: Retrofit): TvShowCall {
        return retrofit.create(TvShowCall::class.java)
    }
    @Provides
    fun providesMovieApi(retrofit: Retrofit): MovieCall {
        return retrofit.create(MovieCall::class.java)
    }

    @Provides
    fun provideRepo(tvShowCall: TvShowCall, movieCall: MovieCall):Repository{
        return RepositoryImpl(tvShowCall, movieCall)
    }
}