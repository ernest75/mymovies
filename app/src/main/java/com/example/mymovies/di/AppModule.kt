package com.example.mymovies.di

import android.app.Application
import androidx.room.Room
import com.example.data.repository.PermissionChecker
import com.example.data.source.LocalDataSource
import com.example.data.source.LocationDataSource
import com.example.data.source.RemoteDataSource
import com.example.mymovies.R
import com.example.mymovies.data.AndroidPermissionChecker
import com.example.mymovies.data.PlayServicesLocationDataSource
import com.example.mymovies.data.database.MovieDatabase
import com.example.mymovies.data.database.RoomDataSource
import com.example.mymovies.data.server.TheMovieDbDataSource
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    @Named("apiKey")
    fun apiKeyProvider(app: Application): String = app.getString(R.string.api_key)

    @Provides
    @Singleton
    fun databaseProvider(app: Application) = Room.databaseBuilder(
        app,
        MovieDatabase::class.java,
        "movie-db"
    ).build()

    @Provides
    fun localDataSourceProvider(db: MovieDatabase): LocalDataSource = RoomDataSource(db)

    @Provides
    fun remoteDataSourceProvider(): RemoteDataSource = TheMovieDbDataSource()

    @Provides
    fun locationDataSourceProvider(app: Application): LocationDataSource =
        PlayServicesLocationDataSource(app)

    @Provides
    fun permissionCheckerProvider(app: Application): PermissionChecker =
        AndroidPermissionChecker(app)

    @Provides
    @Singleton
    fun coroutineDispatcher():CoroutineDispatcher = Dispatchers.Main

}