package com.social.hilt

import com.social.data.clients.adapters.RetrofitCallAdapterFactory
import com.social.data.clients.api.ApiParameters
import com.social.data.clients.api.RetrofitApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RetrofitModule {

    @Provides
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(ApiParameters.Base.BASE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(ApiParameters.Base.BASE_TIMEOUT, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    fun provideCallAdapterFactory() : RetrofitCallAdapterFactory {
        return RetrofitCallAdapterFactory.create()
    }

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient, factory: GsonConverterFactory, retrofitCallAdapterFactory: RetrofitCallAdapterFactory): Retrofit {
        return Retrofit.Builder()
                .baseUrl(ApiParameters.Base.BASE_URL)
                .addConverterFactory(factory)
                .addCallAdapterFactory(retrofitCallAdapterFactory)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun providesRetrofitClient(retrofit: Retrofit): RetrofitApiClient {
        return retrofit.create(RetrofitApiClient::class.java)
    }
}