package com.example.lbc_albums.di.modules

import com.example.lbc_albums.helpers.DevRule.API_BASE_URL
import com.example.lbc_albums.helpers.DevRule.CONNECTION_TIMEOUT_VALUE
import com.example.lbc_albums.helpers.DevRule.READ_TIMEOUT_VALUE
import com.example.lbc_albums.helpers.DevRule.WRITE_TIMEOUT_VALUE
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Koin DI module.
 * Set up the retrofit Config.
 */
val remoteModules = module {
    /**
     * OK HTTP configuration
     */
    single { createOkHttpClient() }

    /**
     * Retrofit creation.
     */
    single {
        createRetrofit(get(), API_BASE_URL)
    }
}

/**
 * OK HTTP configuration
 */
private fun createOkHttpClient(): OkHttpClient {

    return OkHttpClient.Builder()
        .connectTimeout(CONNECTION_TIMEOUT_VALUE, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT_VALUE, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT_VALUE, TimeUnit.SECONDS)
        .build()
}


/**
 * Retrofit configuration
 */
fun createRetrofit(okHttpClient: OkHttpClient, baseURL: String): Retrofit {

    return Retrofit.Builder()
        .baseUrl(baseURL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

/**
 * Create WS generically
 */
inline fun <reified T> createWebService(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}
