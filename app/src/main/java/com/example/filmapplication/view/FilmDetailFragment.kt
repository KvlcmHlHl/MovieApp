package com.example.filmapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.filmapplication.R
import com.example.filmapplication.adapter.RecyclerViewAdapter
import com.example.filmapplication.databinding.FragmentFilmDetailBinding
import com.example.filmapplication.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class FilmDetailFragment : Fragment() {

    private var _binding: FragmentFilmDetailBinding? = null
    private val binding get() = _binding!!
    private val movieModel by viewModel<MovieViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmDetailBinding.inflate(
            LayoutInflater.from(requireContext()),
            container,
            false
        )
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {

            movieModel.getMovieDetailFromAPI(FilmDetailFragmentArgs.fromBundle(it).movie.imdbID!!)
            Glide.with(this)
                .load(FilmDetailFragmentArgs.fromBundle(it).movie.Poster)
                .into(binding.imageView)

            //binding.moviePoster.setImageBitmap(image)
            binding.filmName.text = FilmDetailFragmentArgs.fromBundle(it).movie.Title
            binding.filmYear.text = FilmDetailFragmentArgs.fromBundle(it).movie.Year
            binding.actors.text = ""
            binding.imdbRating.text=""
        }
    }

    fun observeLiveData() {
        movieModel.movie.observe(viewLifecycleOwner, Observer {movies ->
            movies?.let {

                it.data?.let {
                    binding.actors.text =it.Actors
                    binding.imdbRating.text = it.imdbRating

                }
            }

        })

        movieModel.movieError.observe(viewLifecycleOwner, Observer { error->
            error?.let {
                if(error.data == true) {
                    binding.imdbRating.visibility=View.GONE
                } else {
                    binding.actors.visibility = View.GONE
                }
            }
        })


    }
}