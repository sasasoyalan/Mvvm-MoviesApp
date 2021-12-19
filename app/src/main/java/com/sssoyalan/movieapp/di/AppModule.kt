package com.sssoyalan.movieapp.di

import android.content.Context
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.sssoyalan.movieapp.AppDatabase
import com.sssoyalan.movieapp.domain.WebService
import com.sssoyalan.movieapp.utils.AppConstants.BASE_URL
import com.sssoyalan.movieapp.utils.AppConstants.TABLE_NAME
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.sssoyalan.movieapp.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRoomInstance(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            TABLE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideTragosDao(db: AppDatabase) = db.movieDao()

    @Singleton
    @Provides
    fun provideInterceptor(): Interceptor = Interceptor { chain ->
        val url =
            chain.request().url.newBuilder().addQueryParameter("api_key", AppConstants.API_KEY)
                .build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        chain.proceed(request)
    }

    @Singleton
    @Provides
    fun provideApiClient(interceptor: Interceptor): OkHttpClient =
        OkHttpClient().newBuilder().addInterceptor(interceptor).build()

    @Singleton
    @Provides
    fun provideMoshi() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideRetrofitInstance(apiClient: OkHttpClient, moshi: Moshi) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(apiClient)
        .build()

    @Singleton
    @Provides
    fun provideWebService(retrofit: Retrofit) = retrofit.create(WebService::class.java)

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context) =  PreferenceManager.getDefaultSharedPreferences(context)
}