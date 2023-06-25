package com.prity.githubmobile.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.prity.githubmobile.R
import com.prity.githubmobile.adapter.MainAdapter
import com.prity.githubmobile.databinding.ActivityMainBinding
import com.prity.githubmobile.listenerShare
import com.prity.githubmobile.model.GitDataModel
import com.prity.githubmobile.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainAdapter by lazy {
        MainAdapter()
    }

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        listenerShare = { data ->
            shareRepo(data)
        }

        binding.recGitList.adapter = mainAdapter

        mainViewModel.allRepos.observe(this) { repos ->
            if (repos.isNotEmpty()) {
                mainAdapter.setData(repos)
            }
        }
    }

    private fun shareRepo(repo: GitDataModel) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, repo.name)
        if (!repo.url.isNullOrEmpty()) {
            shareIntent.putExtra(Intent.EXTRA_TEXT, repo.url)
        } else {
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.no_url_available))
        }
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_repository)))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.drawer_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Add -> {
                val intent = Intent(this@MainActivity, AddRepoScreen::class.java)
                startActivity(intent)

            }
        }
        return true
    }
}