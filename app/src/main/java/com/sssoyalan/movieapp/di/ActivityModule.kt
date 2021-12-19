package com.sssoyalan.movieapp.di

import com.sssoyalan.movieapp.data.DataSourceImpl
import com.sssoyalan.movieapp.domain.DataSource
import com.sssoyalan.movieapp.domain.Repo
import com.sssoyalan.movieapp.domain.RepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityModule {

    @Binds
    abstract  fun bindRepoImpl(repoImpl: RepoImpl) : Repo

    @Binds
    abstract  fun bindDataSourceImpl(dataSourceImpl: DataSourceImpl) : DataSource
}