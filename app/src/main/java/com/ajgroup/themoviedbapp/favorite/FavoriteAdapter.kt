package com.ajgroup.themoviedbapp.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ajgroup.themoviedbapp.database.entity.FavoriteEntity
import com.ajgroup.themoviedbapp.databinding.ListFavoriteBinding
import com.ajgroup.themoviedbapp.home.HomeAdapter
import com.ajgroup.themoviedbapp.model.Result
import com.bumptech.glide.Glide

class FavoriteAdapter(private val onItemClick: FavoriteAdapter.OnClickListener) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<FavoriteEntity>(){
        override fun areItemsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val IMAGE_BASE ="https://image.tmdb.org/t/p/w500/"

    private val differ = AsyncListDiffer(this, diffCallback)
    fun submitData(value: List<FavoriteEntity?>) = differ.submitList(value)
    inner class ViewHolder(private val binding: ListFavoriteBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: FavoriteEntity){
            binding.apply {
                if (data.title != "") {
                    Glide.with(binding.ivPoster)
                        .load("https://image.tmdb.org/t/p/w500" + data.posterPath)
                        .into(ivPoster)
                    root.setOnClickListener {
                        onItemClick.onClickItem(data)
                    }
                    tvTitle.text = data.title
                    tvRating.text = data.voteAverage.toString()
                    tvOverview.text = data.overview
                } else {
                    tvTitle.setText("OKE").toString()
                }
            }

        }

    }

    interface OnClickListener {
        fun onClickItem(data: FavoriteEntity)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListFavoriteBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let { holder.bind(data) }
    }

    override fun getItemCount(): Int = differ.currentList.size


}