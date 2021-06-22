package com.example.mymovies

import android.app.Application
import com.example.data.repository.MoviesRepository
import com.example.data.repository.PermissionChecker
import com.example.data.repository.RegionRepository
import com.example.data.source.LocalDataSource
import com.example.data.source.LocationDataSource
import com.example.data.source.RemoteDataSource
import com.example.mymovies.data.AndroidPermissionChecker
import com.example.mymovies.data.PlayServicesLocationDataSource
import com.example.mymovies.data.database.MovieDatabase
import com.example.mymovies.data.database.RoomDataSource
import com.example.mymovies.data.server.TheMovieDbDataSource
import com.example.mymovies.ui.detail.DetailFragment
import com.example.mymovies.ui.detail.DetailViewModel
import com.example.mymovies.ui.main.MainFragment
import com.example.mymovies.ui.main.MainViewModel
import com.example.usecases.FindMovieById
import com.example.usecases.GetPopularMovies
import com.example.usecases.ToggleMovieFavorite

import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}

private val appModule = module {
    single(named("apiKey")) { androidApplication().getString(R.string.api_key) }
    single { MovieDatabase.build(get()) }
    factory<LocalDataSource> { RoomDataSource(get()) }
    factory<RemoteDataSource> { TheMovieDbDataSource() }
    factory<LocationDataSource> { PlayServicesLocationDataSource(get()) }
    factory<PermissionChecker> { AndroidPermissionChecker(get()) }
}

private val dataModule = module {
    factory { RegionRepository(get(), get()) }
    factory { MoviesRepository(get(), get(), get(), get(named("apiKey"))) }
}

private val scopesModule = module {
    scope(named<MainFragment>()) {
        viewModel { MainViewModel(get()) }
        scoped { GetPopularMovies(get()) }
    }

    scope(named<DetailFragment>()) {
        viewModel { (id: Int) -> DetailViewModel(id, get(), get()) }
        scoped { FindMovieById(get()) }
        scoped { ToggleMovieFavorite(get()) }
    }
}