package com.yuaihen.wcdxg.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.databinding.LoadStateItemBinding

/**
 * Created by Yuaihen.
 * on 2021/5/28
 * Paging LoadState Adapter
 */
class ArticleLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<ArticleLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        return holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(parent, retry)
    }

    inner class LoadStateViewHolder(
        parent: ViewGroup,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.load_state_item, parent, false)
    ) {
        private val binding = LoadStateItemBinding.bind(itemView)
        private val progressBar = binding.progressbar
        private val errorMsg = binding.tvErrorMsg.also {
            it.setOnClickListener { retry() }
        }

        @SuppressLint("SetTextI18n")
        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                errorMsg.text = "${loadState.error.localizedMessage}，点击重试"
            }

            progressBar.isVisible = loadState is LoadState.Loading
            errorMsg.isVisible = loadState is LoadState.Error
        }
    }


}