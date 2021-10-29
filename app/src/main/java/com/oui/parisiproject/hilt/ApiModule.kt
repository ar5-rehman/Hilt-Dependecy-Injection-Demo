package com.oui.parisiproject.hilt

import android.content.Context
import com.oui.parisiproject.common.Constants.BASE_URL
import com.oui.parisiproject.common.Constants.SIZE_OF_CACHE
import com.oui.parisiproject.common.Keys
import com.oui.parisiproject.data.remote.ApiService
import com.oui.parisiproject.data.repository.RepositoryImpl
import com.oui.parisiproject.domain.repository.RepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton
import com.oui.parisiproject.common.OfflineInterceptor
import okhttp3.*

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
            level = HttpLoggingInterceptor.Level.HEADERS
        }

    @Singleton
    @Provides
    fun provideKeys(): Keys{
        return Keys()
    }

    @Singleton
    @Provides
    fun providesNetworkInterceptor(keys: Keys): Interceptor {
        return Interceptor { chain ->
            val builder = chain.request().newBuilder()

            val request: Request = chain.request()
            val url: HttpUrl = request.url().newBuilder().addQueryParameter("api_key", keys.key).build()

            val defaultHeaders: MutableMap<String, String> = mutableMapOf(
                Pair("Accept-Language", "en"),
                Pair("Request-Time", keys.request_time),
                Pair("Content-Type", "application/json")
            )
            for (header in defaultHeaders) {
               builder.addHeader(header.key, header.value)
            }

            val maxAge = 60

            chain.proceed(builder.url(url).header("Cache-Control", "public, max-age=$maxAge")
                .removeHeader("Pragma").build())
        }
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        @ApplicationContext context: Context,
        networkInterceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {

        val cache: Cache = Cache(File(context.cacheDir, "https"), SIZE_OF_CACHE.toLong())

        return OkHttpClient
            .Builder()
            .cache(cache)
            .addNetworkInterceptor(networkInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(OfflineInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): ApiService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()
        .create(ApiService::class.java)

    @Provides
    fun provideRepository(apiService: ApiService): RepositoryInterface {
        return RepositoryImpl(apiService)
    }
}