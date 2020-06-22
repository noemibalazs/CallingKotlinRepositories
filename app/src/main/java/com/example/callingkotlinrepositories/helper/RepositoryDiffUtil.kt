package com.example.callingkotlinrepositories.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.callingkotlinrepositories.data.Repository

class RepositoryDiffUtil : DiffUtil.ItemCallback<Repository>() {

    override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem.id == newItem.id && oldItem.name == newItem.name && oldItem.fullName == newItem.fullName
                && oldItem.description == newItem.description
    }
}