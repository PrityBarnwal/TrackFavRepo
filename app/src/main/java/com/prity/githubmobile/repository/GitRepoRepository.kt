package com.prity.githubmobile.repository

import androidx.lifecycle.LiveData
import com.prity.githubmobile.db.GitRepoDao
import com.prity.githubmobile.model.GitDataModel


class GitRepoRepository(private val repoDao: GitRepoDao) {
    val allRepos: LiveData<List<GitDataModel>> = repoDao.getAllRepos()

    suspend fun addRepo(repo: GitDataModel) {
        repoDao.addRepo(repo)
    }
}