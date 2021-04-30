package com.yuaihen.wcdxg.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.leancloud.AVObject
import com.yuaihen.wcdxg.databinding.ItemWordBookBinding

/**
 * Created by Yuaihen.
 * on 2021/2/26
 */
class WordBookAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var _dataList = listOf<AVObject>()
    private lateinit var binding: ItemWordBookBinding

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
    }

    fun updateDataList(dataList: List<AVObject>) {
        _dataList = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemWordBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return object : RecyclerView.ViewHolder(
            binding.root
        ) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val ob = _dataList[position]
        binding.contentText.text = ob.get("word")?.toString()
        binding.timeText.text = ob.get("createdAt")?.toString()

    }

    override fun getItemCount() = _dataList.size
}