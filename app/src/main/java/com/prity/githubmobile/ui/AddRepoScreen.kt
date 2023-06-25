package com.prity.githubmobile.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.prity.githubmobile.R
import com.prity.githubmobile.api.RetrofitInstance
import com.prity.githubmobile.databinding.ActivityAddRepoScreenBinding
import com.prity.githubmobile.model.GitDataModel
import com.prity.githubmobile.model.GitRepoResponse
import com.prity.githubmobile.viewModel.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddRepoScreen : AppCompatActivity() {
    private lateinit var binding: ActivityAddRepoScreenBinding

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRepoScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.apply {
            ivBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            btnAdd.setOnClickListener {
                val owner = etOwner.text.toString().trim()
                val repo = etRepository.text.toString().trim()

                if (owner.isNotEmpty() && repo.isNotEmpty()) {
                    fetchRepositoryDetails(owner, repo)
                } else {
                    Toast.makeText(
                        this@AddRepoScreen,
                        getString(R.string.please_enter_owner_repo_name),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun fetchRepositoryDetails(owner: String, repo: String) {
        val call = RetrofitInstance.api.getRepository(owner, repo)
        call.enqueue(object : Callback<GitRepoResponse> {
            override fun onResponse(call: Call<GitRepoResponse>, response: Response<GitRepoResponse>) {
                if (response.isSuccessful) {
                    val repoResponse = response.body()
                    if (repoResponse != null) {
                        val repo = GitDataModel(
                            name = repoResponse.name,
                            description = repoResponse.description ?: "",
                            url = repoResponse.html_url
                        )
                        mainViewModel.addRepo(repo)
                    } else {
                        Toast.makeText(this@AddRepoScreen, "Invalid repository", Toast.LENGTH_SHORT)
                            .show()
                    }
                    finish()
                } else {
                    Toast.makeText(
                        this@AddRepoScreen,
                        "Failed to fetch repository details",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<GitRepoResponse>, t: Throwable) {
                Toast.makeText(
                    this@AddRepoScreen,
                    "Failed to fetch repository details",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
