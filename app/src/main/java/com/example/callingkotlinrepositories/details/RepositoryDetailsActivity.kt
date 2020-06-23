package com.example.callingkotlinrepositories.details

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.callingkotlinrepositories.R
import com.example.callingkotlinrepositories.base.BaseActivity
import com.example.callingkotlinrepositories.data.RepositoryDetails
import com.example.callingkotlinrepositories.databinding.ActivityRepositoryDetailsBinding
import com.example.callingkotlinrepositories.helper.DataManager
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

        setUpCustomActionBar()
        initBinding()
        setObservers()
    }

    private fun setUpCustomActionBar() {
        val url = dataManager.getUserAvatarUrl()
        Log.d(KOIN_TAG, "see user url: $url")
        supportActionBar?.setDisplayShowCustomEnabled(true);
        supportActionBar?.setCustomView(R.layout.custom_action_bar)
        supportActionBar?.setTitle(getString(R.string.txt_repository_details))

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

}