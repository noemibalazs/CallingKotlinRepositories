package com.example.callingkotlinrepositories.repository

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.callingkotlinrepositories.R
import com.example.callingkotlinrepositories.data.Repository
import com.example.callingkotlinrepositories.databinding.ItemRepositoryBinding
import com.example.callingkotlinrepositories.helper.RepositoryClickListener
import com.example.callingkotlinrepositories.helper.RepositoryDiffUtil

class RepositoryAdapter(
    private val repositoryViewModel: RepositoryViewModel
) : ListAdapter<Repository, RepositoryVH>(RepositoryDiffUtil()) {

    var repositoryClickListener: RepositoryClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryVH {
        val binding: ItemRepositoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_repository,
            parent,
            false
        )
        return RepositoryVH(binding, repositoryViewModel, repositoryClickListener)
    }

    override fun onBindViewHolder(holder: RepositoryVH, position: Int) {
        holder.onBind(getItem(position))
    }

    fun removeListener(){
       repositoryClickListener = null
    }
}