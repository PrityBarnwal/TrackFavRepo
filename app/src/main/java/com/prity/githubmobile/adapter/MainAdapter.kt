package com.prity.githubmobile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prity.githubmobile.databinding.ItemListGitDataBinding
import com.prity.githubmobile.listenerShare
import com.prity.githubmobile.model.GitDataModel

class MainAdapter :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private val listData: MutableList<GitDataModel> = mutableListOf()

    inner class MainViewHolder(var binding: ItemListGitDataBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemListGitDataBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.binding.apply {
            data = listData[position]
            ivShare.setOnClickListener {
                listenerShare?.invoke(listData[position])
//                listener.onShareItemClick(listData[position])
            }
        }
    }

    override fun getItemCount() = listData.size

    fun setData(repos: List<GitDataModel>) {
        listData.clear()
        listData.addAll(repos)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onShareItemClick(repo: GitDataModel)
    }
}

