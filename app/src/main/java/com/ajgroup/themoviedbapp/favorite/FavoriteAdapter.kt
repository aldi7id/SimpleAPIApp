package com.ajgroup.themoviedbapp.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ajgroup.themoviedbapp.database.Favorite
import com.ajgroup.themoviedbapp.databinding.ListFavoriteBinding
import com.bumptech.glide.Glide

class FavoriteAdapter(private val onClick:(Favorite)->Unit)
    : ListAdapter<Favorite, FavoriteAdapter.ViewHolder>(FavoriteComparator()) {


    class ViewHolder(private val binding: ListFavoriteBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(currentFavorite: Favorite,
                 onClick: (Favorite) -> Unit){

            binding.apply {
                Glide.with(binding.ivPoster)
                    .load("https://image.tmdb.org/t/p/w500"+currentFavorite.posterPath)
                    .into(ivPoster)
                root.setOnClickListener {
                    onClick(currentFavorite)
                }
                tvTitle.text = currentFavorite.title
                tvRating.text = currentFavorite.voteAverage.toString()
                tvOverview.text = currentFavorite.overview
            }

        }

    }

    class FavoriteComparator : DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListFavoriteBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

}