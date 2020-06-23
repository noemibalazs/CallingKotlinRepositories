package com.example.callingkotlinrepositories.details

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.callingkotlinrepositories.R
import com.example.callingkotlinrepositories.base.BaseActivity
import com.example.callingkotlinrepositories.data.RepositoryDetails
import com.example.callingkotlinrepositories.databinding.ActivityRepositoryDetailsBinding
import com.example.callingkotlinrepositories.helper.DataManager
import com.example.callingkotlinrepositories.loginuser.LoginUserActivity
import com.example.callingkotlinrepositories.utils.loadPicture
import org.koin.android.ext.android.inject
import org.koin.core.logger.KOIN_TAG

class RepositoryDetailsActivity : BaseActivity<RepositoryDetailsViewModel>() {

    private val repositoryDetailsViewModel: RepositoryDetailsViewModel by inject()
    private val dataManager: DataManager by inject()

    private lateinit var binding: ActivityRepositoryDetailsBinding

    override fun initViewModel(): RepositoryDetailsViewModel {
        return repositoryDetailsViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repository_details)

        supportActionBar?.title = getString(R.string.txt_repository_details)

        setUpCustomActionBar()
        initBinding()
        setObservers()
    }

    private fun setUpCustomActionBar() {
        val url = dataManager.getUserAvatarUrl()
        Log.d(KOIN_TAG, "see user url: $url")
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(R.layout.custom_action_bar)

        val view = supportActionBar?.customView
        view?.findViewById<ImageView>(R.id.iv_user_picture)?.loadPicture(url)
    }

    private fun initBinding() {
        binding.viewModel = repositoryDetailsViewModel
    }

    private fun setObservers() {
        repositoryDetailsViewModel.mutableRepositoryDetails.observe(this, Observer {
            populateUI(it)
        })

        repositoryDetailsViewModel.mutableFailureError.observe(this, Observer {
            showErrorToastToUser()
        })

        repositoryDetailsViewModel.mutableRepositoryIssues.observe(this, Observer {
            binding.tvRepoIssuesPastYear.text = getString(R.string.txt_repo_issues_count, it.total_count)
        })
    }

    private fun populateUI(repositoryDetails: RepositoryDetails) {
        binding.tvRepoName.text = getString(R.string.txt_repo_name, repositoryDetails.name)
        binding.tvRepoDescription.text =
            getString(R.string.txt_repo_description, repositoryDetails.description)
        binding.tvRepoSize.text = getString(R.string.txt_repo_size, repositoryDetails.size)
        binding.tvRepoWatchers.text =
            getString(R.string.txt_repo_watchers, repositoryDetails.watchers_count)
        binding.tvRepoStargazers.text =
            getString(R.string.txt_repo_stargazers, repositoryDetails.stargazers_count)
        binding.tvRepoForks.text = getString(R.string.txt_repo_forks, repositoryDetails.forks_count)
        binding.tvRepoOpenIssuesCount.text =
            getString(R.string.txt_repo_open_issues_count, repositoryDetails.open_issues_count)
    }

    private fun showErrorToastToUser() {
        Toast.makeText(this, getString(R.string.txt_error), Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        menu.findItem(R.id.menu_sign_in_as).title =
            getString(R.string.txt_user_sign_is_as_title, dataManager.getUserLoginAs())
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_sign_out) {
            userClickedSignedOut()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun userClickedSignedOut() {
        val intent = Intent(this, LoginUserActivity::class.java).apply {
            flags =
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
        finish()
    }
}