package com.example.callingkotlinrepositories.loginuser

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.example.callingkotlinrepositories.R
import com.example.callingkotlinrepositories.base.BaseActivity
import com.example.callingkotlinrepositories.data.User
import com.example.callingkotlinrepositories.databinding.ActivityLoginUserBinding
import com.example.callingkotlinrepositories.helper.DataManager
import com.example.callingkotlinrepositories.helper.openActivity
import com.example.callingkotlinrepositories.repository.RepositoryActivity
import org.koin.android.ext.android.inject

class LoginUserActivity : BaseActivity<LoginUserViewModel>() {

    private lateinit var binding: ActivityLoginUserBinding
    private val viewModel: LoginUserViewModel by inject()
    private val dataManager: DataManager by inject()

    override fun initViewModel(): LoginUserViewModel = viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_user)

        initBinding()
        setObservers()
    }

    private fun initBinding() {
        binding.viewModel = viewModel
    }

    private fun setObservers() {

        viewModel.mutableUserName.observe(this, Observer {
            dataManager.saveUserNameOrEmail(it)
        })

        viewModel.mutableUserPassword.observe(this, Observer {
            dataManager.saveUserPassword(it)
        })

        viewModel.mutableErrorField.observe(this, Observer {
            userCredentialsShouldNotBeEmptyErrorDialog()
        })

        viewModel.mutableUserDetails.observe(this, Observer {
            saveUserAvatarAndLogin(it)
            it?.let {
               this.openActivity(RepositoryActivity::class.java)
            }
        })

        viewModel.mutableCredentialError.observe(this, Observer {
            userCredentialsErrorDialog()
        })
    }

    private fun userCredentialsShouldNotBeEmptyErrorDialog() {
        MaterialDialog(this).show {
            title(R.string.tv_credentials_not_empty_title)
            positiveButton(R.string.ok)
            positiveButton {
                dismiss()
            }
        }
    }

    private fun saveUserAvatarAndLogin(user: User?) {
        user?.let {
            dataManager.saveUserAvatarUrl(it.avatar_url ?: "")
            dataManager.saveUserLoginAs(it.login ?: "")
        }
    }

    private fun userCredentialsErrorDialog(){
        MaterialDialog(this).show {
            title(R.string.tv_credentials_error_title)
            positiveButton(R.string.ok)
            positiveButton {
                dismiss()
            }
        }
    }
}