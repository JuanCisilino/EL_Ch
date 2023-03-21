package com.frost.el_ch.network

import android.content.Context
import androidx.room.Room
import com.frost.el_ch.database.Database
import com.frost.el_ch.repositories.QRRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val EL_CH_DATABASE = "eldar_challenge_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, Database::class.java, EL_CH_DATABASE).build()

    @Singleton
    @Provides
    fun provideDao(db: Database) = db.getUserDao()

    @Singleton
    @Provides
    fun provideRetrofit() = Retrofit.Builder()
        .baseUrl("https://neutrinoapi-qr-code.p.rapidapi.com/")
        .client(
            OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .addHeader("x-rapidapi-key", "b8c90dfa99msh3a2fa3c9df39bc1p1a3da7jsn5970183f34b3")
                    .addHeader("x-rapidapi-host", "neutrinoapi-qr-code.p.rapidapi.com")
                    .build()
                chain.proceed(request)
            }
            .build())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build()
        .create(QRService::class.java)

    @Singleton
    @Provides
    fun provideQR(qrApi: QRService) = QRRepository(qrApi)
}