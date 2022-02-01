package com.example.moviedb.di

import com.example.moviedb.BuildConfig
import com.example.moviedb.framework.network.MovieServiceApi
import com.example.moviedb.utils.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { HttpClientFactory() }
    single { provideOkHttpBuilder(httpClientFactory = get()) }
    single { provideGson() }
    single { provideConverterFactory(get())}
    single { provideOkHttpClient(clientBuilder = get(), apiKey = provideApiKey(get())) }
    single { provideRetrofit(get(), get()) }
    single { provideServiceApi(get()) }
}

internal fun provideOkHttpBuilder(
    httpClientFactory: HttpClientFactory
): OkHttpClient.Builder {
    return httpClientFactory.create()
}

fun provideOkHttpClient(
    clientBuilder: OkHttpClient.Builder,
    apiKey: String
): OkHttpClient {
    clientBuilder
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
            val originalHttpUrl = chain.request().url
            val url = originalHttpUrl.newBuilder().addQueryParameter("api_key", apiKey).build()
            request.url(url)
            return@addInterceptor chain.proceed(request.build())
        }
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
    if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(loggingInterceptor)
    }
    return clientBuilder.build()
}

fun provideRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit {
    return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
        .addConverterFactory(gsonConverterFactory).build()
}

fun provideGson(): Gson {
    return GsonBuilder().create()
}

fun provideConverterFactory(gson: Gson): GsonConverterFactory {
    return GsonConverterFactory.create(gson)
}

fun provideServiceApi(retrofit: Retrofit): MovieServiceApi = retrofit.create(
    MovieServiceApi::class.java)

internal class HttpClientFactory {
    private val httpClient by lazy {
        OkHttpClient()
    }

    fun create(): OkHttpClient.Builder {
        return httpClient.newBuilder()
    }
}
