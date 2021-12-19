package com.sssoyalan.movieapp.domain

import androidx.room.*
import com.sssoyalan.movieapp.data.model.MovieEntity

@Dao
interface MoviesDao {

    @Query("SELECT * FROM table_movies")
    suspend fun getAllFavoriteMovies():List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(movie:MovieEntity)

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)
}