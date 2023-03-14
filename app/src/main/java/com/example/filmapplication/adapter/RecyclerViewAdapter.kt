package com.example.filmapplication.adapter

import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.filmapplication.databinding.RowItemBinding
import com.example.filmapplication.model.MovieModel
import com.example.filmapplication.view.FilmListFragmentDirections

class RecyclerViewAdapter(val movieList: ArrayList<MovieModel>) : RecyclerView.Adapter<RecyclerViewAdapter.RowItemHolder>() {
    class RowItemHolder(val binding : RowItemBinding) : ViewHolder(binding.root)
    {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowItemHolder {
        val binding = RowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowItemHolder(binding)    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: RowItemHolder, position: Int) {

        holder.itemView.setOnClickListener {
            val action = FilmListFragmentDirections.actionFilmListFragmentToFilmDetailFragment(movieList.get(position))
            Navigation.findNavController(it).navigate(action)
        }

        holder.binding.filmName.text = movieList[position].Title
        holder.binding.filmYear.text = movieList[position].Year
        Glide.with(holder.binding.root)
            .load(movieList[position].Poster)
            .into(holder.binding.filmImage)

    }
}