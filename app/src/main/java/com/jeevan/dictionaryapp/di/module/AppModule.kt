package com.jeevan.dictionaryapp.di.module

import android.content.Context
import androidx.room.Room
import com.jeevan.dictionaryapp.core.Constants.BASE_URL
import com.jeevan.dictionaryapp.data.local.DictionaryDatabase
import com.jeevan.dictionaryapp.data.remote.DictionaryAPI
import com.jeevan.dictionaryapp.data.repository.DictionaryRepositoryImpl
import com.jeevan.dictionaryapp.di.DictionaryApp
import com.jeevan.dictionaryapp.domain.repository.DictionaryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideDictionaryApi(): DictionaryAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .build()
            .create(DictionaryAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideDictionaryRepository(api: DictionaryAPI): DictionaryRepository =
        DictionaryRepositoryImpl(api = api)

    @Singleton
    @Provides
    fun provideDictionaryAppObject() = DictionaryApp()

    @Singleton
    @Provides
    fun provideDictionaryDatabase(@ApplicationContext app: Context) = Room
        .databaseBuilder(app, DictionaryDatabase::class.java, "dictionary_db")
        .build()

    @Singleton
    @Provides
    fun provideDictionaryDao(db: DictionaryDatabase) = db.dao
}