package com.example.callingkotlinrepositories.repository

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.callingkotlinrepositories.R
import com.example.callingkotlinrepositories.base.BaseActivity
import com.example.callingkotlinrepositories.databinding.ActivityRepositoryBinding
import com.example.callingkotlinrepositories.details.RepositoryDetailsActivity
import com.example.callingkotlinrepositories.helper.DataManager
import com.example.callingkotlinrepositories.helper.RepositoryClickListener
import com.example.callingkotlinrepositories.loginuser.LoginUserActivity
import com.example.callingkotlinrepositories.utils.REPOSITORY_LIST_SAVED_STATE
import com.example.callingkotlinrepositories.utils.loadPicture
import com.example.callingkotlinrepositories.utils.openActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.core.logger.KOIN_TAG

class RepositoryActivity : BaseActivity<RepositoryViewModel>() {

    private val dataManager: DataManager by inject()
    private lateinit var binding: ActivityRepositoryBinding
    private lateinit var repositoryAdapter: RepositoryAdapter
    private val repositoryViewModel: RepositoryViewModel by stateViewModel()

    private val repositoryListener = object : RepositoryClickListener {
        override fun onRepositoryClicked(id: Int, fullName: String) {
            dataManager.saveRepositoryId(id)
            dataManager.saveRepositoryFullName(fullName)
            openActivity(RepositoryDetailsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repository)

        setUpCustomActionBar()
        initBinding()
        setObserver()
    }

    override fun initViewModel(): RepositoryViewModel = repositoryViewModel

    private fun setUpCustomActionBar() {
        val url = dataManager.getUserAvatarUrl()
        Log.d(KOIN_TAG, "see user url: $url")
        supportActionBar?.setDisplayShowCustomEnabled(true);
        supportActionBar?.setCustomView(R.layout.custom_action_bar)

        val view = supportActionBar?.customView
        view?.findViewById<ImageView>(R.id.iv_user_picture)?.loadPicture(url)
    }

    private fun initBinding() {
        binding.viewModel = repositoryViewModel
        repositoryAdapter = RepositoryAdapter(repositoryViewModel).apply {
            this.repositoryClickListener = repositoryListener
        }
        binding.rvRepository.adapter = repositoryAdapter
    }

    private fun setObserver() {

        val savedState = repositoryViewModel.handle.contains(REPOSITORY_LIST_SAVED_STATE)

        if (!savedState) {
            repositoryViewModel.getRepositoryList().observe(this, Observer {
                repositoryAdapter.submitList(it)
                repositoryViewModel.saveRepositoryListDueConfChanges(it)
            })
        } else {
            repositoryViewModel.getReposListAfterConfChanges().observe(this, Observer {
                repositoryAdapter.submitList(null)
                repositoryAdapter.submitList(it)
            })
        }

        repositoryViewModel.mutableFailureError.observe(this, Observer {
            showErrorToastToUser()
        })

        repositoryViewModel.showLoading.observe(this, Observer {
            binding.pbRepository.visibility = if (it) View.VISIBLE else View.INVISIBLE
        })
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

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, LoginUserActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        repositoryAdapter.removeListener()
    }
}