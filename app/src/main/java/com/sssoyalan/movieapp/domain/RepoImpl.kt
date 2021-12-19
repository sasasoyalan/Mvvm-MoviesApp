package com.sssoyalan.movieapp.domain

import com.sssoyalan.movieapp.data.model.Movie
import com.sssoyalan.movieapp.data.model.MovieEntity
import com.sssoyalan.movieapp.vo.Resource
import javax.inject.Inject

class RepoImpl @Inject constructor(private val dataSource: DataSource) : Repo {

    override suspend fun getMoviesList(yearRelease:String, sortType:String): Resource<List<Movie>> {
        return dataSource.getPopularMovies(yearRelease, sortType)
    }

    override suspend fun getMoviesListSearch(query:String): Resource<List<Movie>> {
        return dataSource.getMoviesBySearch(query)
    }

    override suspend fun getFavoriteMovies(): Resource<List<MovieEntity>> {
        return dataSource.getMoviesFavorites()
    }

    override suspend fun insertMovie(movie: MovieEntity) {
        dataSource.insertMovieIntoRoom(movie)
    }

    override suspend fun deleteMovie(movie: MovieEntity) {
        dataSource.deleteMovieFromRoom(movie)
    }
}