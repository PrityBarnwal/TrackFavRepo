package com.prity.githubmobile.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.prity.githubmobile.model.GitDataModel


@Dao
interface GitRepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRepo(repo: GitDataModel)

    @Query("SELECT * FROM repoTable")
    fun getAllRepos(): LiveData<List<GitDataModel>>
}


