package com.movies.app.di

import android.content.Context
import androidx.room.Room
import com.movies.core.data.source.local.room.MovieDao
import com.movies.core.data.source.local.room.MovieDatabase
import com.movies.core.data.source.local.room.ReviewMovieDao
import com.movies.core.data.source.local.room.VideoMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context : Context): MovieDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("movie".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java, "MovieApp.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao = database.movieDao()

    @Provides
    fun provideReviewMovieDao(database: MovieDatabase): ReviewMovieDao = database.reviewMovieDao()

    @Provides
    fun provideVideoMovieDao(database: MovieDatabase): VideoMovieDao = database.videoMovieDao()
}