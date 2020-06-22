package com.example.callingkotlinrepositories.repository

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.callingkotlinrepositories.R
import com.example.callingkotlinrepositories.data.Repository
import com.example.callingkotlinrepositories.databinding.ItemRepositoryBinding
import com.example.callingkotlinrepositories.helper.DebounceClickListener
import com.example.callingkotlinrepositories.helper.RepositoryClickListener

class RepositoryVH(
    private val binding: ItemRepositoryBinding,
    private val repositoryViewModel: RepositoryViewModel,
    private val repositoryClickListener: RepositoryClickListener?
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(repository: Repository) {
        binding.viewModel = repositoryViewModel
        binding.apply {
            val context = binding.root.context
            tvRepoName.text =
                context.getString(R.string.tv_repo_name, repository.name)
            tvRepoDescription.text =
                context.getString(R.string.tv_repo_description, repository.description)

            cvRepoContainer.setOnClickListener(object : DebounceClickListener() {
                override fun onDebounce(view: View) {
                    repositoryClickListener?.onRepositoryClicked(
                        repository.id,
                        repository.fullName ?: ""
                    )
                }
            })
        }
    }
}