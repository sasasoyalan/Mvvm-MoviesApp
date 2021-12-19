package com.sssoyalan.movieapp.domain

import com.sssoyalan.movieapp.data.model.Movie
import com.sssoyalan.movieapp.data.model.MovieEntity
import com.sssoyalan.movieapp.vo.Resource

interface DataSource {
    suspend fun getPopularMovies(yearRelease: String, sortType: String): Resource<List<Movie>>
    suspend fun getMoviesBySearch(query: String): Resource<List<Movie>>
    suspend fun insertMovieIntoRoom(movie: MovieEntity)
    suspend fun getMoviesFavorites(): Resource<List<MovieEntity>>
    suspend fun deleteMovieFromRoom(movie: MovieEntity)
}