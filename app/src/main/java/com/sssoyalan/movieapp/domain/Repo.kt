package com.sssoyalan.movieapp.domain

import com.sssoyalan.movieapp.data.model.Movie
import com.sssoyalan.movieapp.data.model.MovieEntity
import com.sssoyalan.movieapp.vo.Resource

interface Repo {
    suspend fun getMoviesList(yearRelease:String, sortType:String) : Resource<List<Movie>>
    suspend fun getMoviesListSearch(query:String) : Resource<List<Movie>>
    suspend fun getFavoriteMovies() : Resource<List<MovieEntity>>
    suspend fun insertMovie(movie:MovieEntity)
    suspend fun deleteMovie(movie:MovieEntity)
}