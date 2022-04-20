//package com.ajgroup.themoviedbapp.home
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import androidx.recyclerview.widget.AsyncListDiffer
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.ajgroup.themoviedbapp.R
//import com.ajgroup.themoviedbapp.database.RegisterEntity
//import com.ajgroup.themoviedbapp.databinding.ItemContentBinding
//import com.ajgroup.themoviedbapp.databinding.ListItemBinding
//import com.bumptech.glide.Glide
//
//class MyRecycleViewAdapter (private val onItemClick: OnClickListener)
//: RecyclerView.Adapter<MyRecycleViewAdapter.ViewHolder>() {
//
//    private val diffCallback = object : DiffUtil.ItemCallback<Result>(){
//        override fun areItemsTheSame(
//            oldItem: Result,
//            newItem: Result): Boolean  = oldItem.id == newItem.id
//
//        override fun areContentsTheSame(
//            oldItem: Result,
//            newItem: Result): Boolean = oldItem.hashCode() == newItem.hashCode()
//    }
//    private val IMAGE_BASE ="https://image.tmdb.org/t/p/w500/"
//
//    private val differ = AsyncListDiffer(this, diffCallback)
//
//    fun submitData(value: List<Result>) = differ.submitList(value)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecycleViewAdapter.ViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        return ViewHolder(ItemContentBinding.inflate(inflater, parent, false))
//    }
//
//    override fun onBindViewHolder(holder: MyRecycleViewAdapter.ViewHolder, position: Int) {
//        val data = differ.currentList[position]
//        data.let { holder.bind(data) }
//    }
//
//    override fun getItemCount(): Int = differ.currentList.size
//
//    inner class ViewHolder(private val binding: ItemContentBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(data: Result){
//            binding.apply {
//                tvJudul.text = data.originalTitle
//                tvPrice.text = data.releaseDate
//
//                if (data.posterPath == null) {
//                    Glide.with(binding.root).load(R.drawable.ic_launcher_background).into(ivHeader)
//                }else {
//                    Glide.with(binding.root).load(IMAGE_BASE+data.posterPath).into(ivHeader)
//                }
//                root.setOnClickListener {
//                    onItemClick.onClickItem(data)
//                }
//            }
//        }
//    }
//    interface OnClickListener {
//        fun onClickItem(data: Result)
//    }
//
//}