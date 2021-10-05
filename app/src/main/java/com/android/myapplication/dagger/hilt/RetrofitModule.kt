package com.android.myapplication.dagger.hilt

import com.android.myapplication.data.network.Api
import com.android.myapplication.data.network.RetrofitApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(FragmentComponent::class)
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
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor?): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor!!)
                .connectTimeout(Api.Base.TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Api.Base.TIMEOUT, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient?, factory: GsonConverterFactory?): Retrofit {
        return Retrofit.Builder()
                .baseUrl(Api.Base.BASE_URL)
                .addConverterFactory(factory!!)
                .client(okHttpClient!!)
                .build()
    }

    @Provides
    fun providesRetrofitClient(retrofit: Retrofit): RetrofitApiClient {
        return retrofit.create(RetrofitApiClient::class.java)
    }
}