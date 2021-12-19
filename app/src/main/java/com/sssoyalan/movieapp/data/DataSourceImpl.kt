package com.sssoyalan.movieapp.data

import android.content.SharedPreferences
import com.sssoyalan.movieapp.data.model.Movie
import com.sssoyalan.movieapp.data.model.MovieEntity
import com.sssoyalan.movieapp.domain.DataSource
import com.sssoyalan.movieapp.domain.MoviesDao
import com.sssoyalan.movieapp.domain.WebService
import com.sssoyalan.movieapp.vo.Resource
import javax.inject.Inject

class DataSourceImpl @Inject constructor(
    private val moviesDao: MoviesDao,
    private val webService: WebService,
    private val sharedPreferences: SharedPreferences
) : DataSource {

    override suspend fun getPopularMovies(
        yearRelease: String,
        sortType: String
    ): Resource<List<Movie>> {
        return Resource.Success(webService.getPopularMovies(yearRelease, sortType, sharedPreferences.getString("language", "en")!!).results)
    }

    override suspend fun getMoviesBySearch(query: String): Resource<List<Movie>> {
        return Resource.Success(webService.getMoviesSearch(query, sharedPreferences.getString("language", "en")!!, true).results)
    }

    override suspend fun insertMovieIntoRoom(movie: MovieEntity) {
        moviesDao.insertFavorite(movie)
    }

    override suspend fun getMoviesFavorites(): Resource<List<MovieEntity>> {
        return Resource.Success(moviesDao.getAllFavoriteMovies())
    }

    override suspend fun deleteMovieFromRoom(movie: MovieEntity) {
        moviesDao.deleteMovie(movie)
    }

}