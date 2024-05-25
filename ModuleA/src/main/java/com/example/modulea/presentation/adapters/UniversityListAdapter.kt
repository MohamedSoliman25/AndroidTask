package com.example.modulea.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.modulea.databinding.UniversityItemBinding
import com.example.modulea.domain.model.UniversityItem
import kotlin.properties.Delegates

class UniversityListAdapter(
    list: List<UniversityItem> = emptyList(),
    private val onItemClick: (UniversityItem) -> Unit

) : RecyclerView.Adapter<UniversityListAdapter.ViewHolder>() {

    private var list: List<UniversityItem> by Delegates.observable(list) { _, old, new ->
        DiffUtil.calculateDiff(
            object : DiffUtil.Callback() {
                override fun getOldListSize() = old.size

                override fun getNewListSize() = new.size

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    old[oldItemPosition].name == new[newItemPosition].name

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    old[oldItemPosition] == new[newItemPosition]

            }
        ).also { result ->
            result.dispatchUpdatesTo(this@UniversityListAdapter)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(
            UniversityItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], onItemClick)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateData(newList: List<UniversityItem>) {
        Log.d("TAG", "updateData: "+newList)
        list = newList
    }

    class ViewHolder(
        private val binding: UniversityItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(university: UniversityItem, onItemClick: (UniversityItem) -> Unit
        ) {
            binding.apply {
                universityName.text = university.name
                universityState.text = university.state_province
                root.setOnClickListener {
                    onItemClick(university)
                }
            }

        }
    }
}
