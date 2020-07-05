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
import com.example.callingkotlinrepositories.data.LastYearStats
import com.example.callingkotlinrepositories.data.RepositoryDetails
import com.example.callingkotlinrepositories.databinding.ActivityRepositoryDetailsBinding
import com.example.callingkotlinrepositories.helper.DataManager
import com.example.callingkotlinrepositories.loginuser.LoginUserActivity
import com.example.callingkotlinrepositories.utils.*
import kotlinx.android.synthetic.main.repository_week_commit.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.core.logger.KOIN_TAG
import java.text.SimpleDateFormat
import java.util.*

class RepositoryDetailsActivity : BaseActivity<RepositoryDetailsViewModel>() {

    private val repositoryDetailsViewModel: RepositoryDetailsViewModel by stateViewModel()
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
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(R.layout.custom_action_bar)

        val view = supportActionBar?.customView
        view?.findViewById<ImageView>(R.id.iv_user_picture)?.loadPicture(url)
    }

    private fun initBinding() {
        binding.viewModel = repositoryDetailsViewModel
    }

    private fun setObservers() {

        val repositoryDetailsState = repositoryDetailsViewModel.handle.contains(
            REPOSITORY_DETAILS_SAVED_STATE
        )

        if (!repositoryDetailsState) {
            repositoryDetailsViewModel.getRepositoryDetails().observe(this, Observer {
                populateUI(it)
                repositoryDetailsViewModel.saveRepositoryDetailsDueConfChanges(it)
            })
        } else {
            repositoryDetailsViewModel.getRepositoryDetailsAfterConfChanges()
                .observe(this, Observer {
                    populateUI(it)
                })
        }

        val repositoryIssuesState = repositoryDetailsViewModel.handle.contains(
            REPOSITORY_ISSUES_COUNTER_SAVED_STATE
        )

        if (!repositoryIssuesState) {
            repositoryDetailsViewModel.getRepositoryIssuesCount().observe(this, Observer {
                binding.tvRepoIssuesPastYear.setSpannableText(
                    getString(R.string.txt_repo_issues_count, it.total_count)
                )
                repositoryDetailsViewModel.saveRepositoryIssuesDueConfChanges(it)
            })
        } else {
            repositoryDetailsViewModel.getRepositoryIssuesAfterConfChanges()
                .observe(this, Observer {
                    binding.tvRepoIssuesPastYear.setSpannableText(
                        getString(R.string.txt_repo_issues_count, it.total_count)
                    )
                })
        }

        val repositoryStatsState = repositoryDetailsViewModel.handle.contains(
            REPOSITORY_LAST_YEAR_STATS_SAVED_STATE
        )

        if (!repositoryStatsState) {
            repositoryDetailsViewModel.getRepositoryStats().observe(this, Observer {
                populateFieldsWithStats(it)
                repositoryDetailsViewModel.saveRepositoryLastYearStatsDueConfChanges(it)
            })
        } else {
            repositoryDetailsViewModel.getRepositoryStatsAfterConfChanges().observe(this, Observer {
                populateFieldsWithStats(it)
            })
        }

        repositoryDetailsViewModel.mutableFailureError.observe(this, Observer {
            showErrorToastToUser()
        })
    }

    private fun populateUI(repositoryDetails: RepositoryDetails) {
        binding.tvRepoName.setSpannableText(
            getString(
                R.string.txt_repo_name,
                repositoryDetails.name
            )
        )
        binding.tvRepoDescription.setSpannableText(
            getString(
                R.string.txt_repo_description,
                repositoryDetails.description
            )
        )
        binding.tvRepoSize.setSpannableText(
            getString(
                R.string.txt_repo_size,
                repositoryDetails.size
            )
        )
        binding.tvRepoWatchers.setSpannableText(
            getString(
                R.string.txt_repo_watchers,
                repositoryDetails.watchers_count
            )
        )
        binding.tvRepoStargazers.setSpannableText(
            getString(
                R.string.txt_repo_stargazers,
                repositoryDetails.stargazers_count
            )
        )
        binding.tvRepoForks.setSpannableText(
            getString(
                R.string.txt_repo_forks,
                repositoryDetails.forks_count
            )
        )
        binding.tvRepoOpenIssuesCount.setSpannableText(
            getString(
                R.string.txt_repo_open_issues_count,
                repositoryDetails.open_issues_count
            )
        )
    }

    private fun showErrorToastToUser() {
        Toast.makeText(this, getString(R.string.txt_error), Toast.LENGTH_LONG).show()
    }

    private fun populateFieldsWithStats(lastYearStats: MutableList<LastYearStats>) {
        val firstWeekStats = lastYearStats[0]
        binding.clFirstWeek.tv_week.text =
            getString(R.string.txt_first_week, convertLongToDate(firstWeekStats.week))
        binding.clFirstWeek.tv_sunday.text = getString(R.string.txt_sunday, firstWeekStats.days[0])
        binding.clFirstWeek.tv_monday.text = getString(R.string.txt_monday, firstWeekStats.days[1])
        binding.clFirstWeek.tv_tuesday.text =
            getString(R.string.txt_tuesday, firstWeekStats.days[2])
        binding.clFirstWeek.tv_wednesday.text =
            getString(R.string.txt_wednesday, firstWeekStats.days[3])
        binding.clFirstWeek.tv_thursday.text =
            getString(R.string.txt_thursday, firstWeekStats.days[4])
        binding.clFirstWeek.tv_friday.text = getString(R.string.txt_friday, firstWeekStats.days[5])
        binding.clFirstWeek.tv_saturday.text =
            getString(R.string.txt_saturday, firstWeekStats.days[6])

        val lastWeekStats = lastYearStats[51]
        binding.clLastWeek.tv_week.text =
            getString(R.string.txt_last_week, convertLongToDate(lastWeekStats.week))
        binding.clLastWeek.tv_sunday.text = getString(R.string.txt_sunday, lastWeekStats.days[0])
        binding.clLastWeek.tv_monday.text = getString(R.string.txt_monday, lastWeekStats.days[1])
        binding.clLastWeek.tv_tuesday.text = getString(R.string.txt_tuesday, lastWeekStats.days[2])
        binding.clLastWeek.tv_wednesday.text =
            getString(R.string.txt_wednesday, lastWeekStats.days[3])
        binding.clLastWeek.tv_thursday.text =
            getString(R.string.txt_thursday, lastWeekStats.days[4])
        binding.clLastWeek.tv_friday.text = getString(R.string.txt_friday, lastWeekStats.days[5])
        binding.clLastWeek.tv_saturday.text =
            getString(R.string.txt_saturday, lastWeekStats.days[6])
    }

    private fun convertLongToDate(timeStamp: Long): String {
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        val date = Date(timeStamp * 1000)
        return dateFormat.format(date)
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