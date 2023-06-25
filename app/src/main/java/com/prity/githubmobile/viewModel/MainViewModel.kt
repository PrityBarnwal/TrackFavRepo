package com.prity.githubmobile.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.prity.githubmobile.db.RepoDatabase
import com.prity.githubmobile.model.GitDataModel
import com.prity.githubmobile.repository.GitRepoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: GitRepoRepository
    val allRepos: LiveData<List<GitDataModel>>

    init {
        val repoDao = RepoDatabase.getInstance(application).repoDao()
        repository = GitRepoRepository(repoDao)
        allRepos = repository.allRepos
    }

    fun addRepo(repo: GitDataModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRepo(repo)
        }
    }
}

