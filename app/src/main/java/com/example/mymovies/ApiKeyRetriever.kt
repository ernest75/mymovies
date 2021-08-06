package com.example.mymovies

object ApiKeyRetriever {
    init {
        System.loadLibrary("api-keys")
    }

    external fun getMoviesApiKey(): String
}