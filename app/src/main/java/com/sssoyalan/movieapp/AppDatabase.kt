package com.sssoyalan.movieapp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sssoyalan.movieapp.data.model.MovieEntity
import com.sssoyalan.movieapp.domain.MoviesDao

@Database(entities = [MovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MoviesDao
}