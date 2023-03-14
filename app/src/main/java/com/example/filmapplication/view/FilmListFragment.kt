package com.example.filmapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapplication.R
import com.example.filmapplication.adapter.RecyclerViewAdapter
import com.example.filmapplication.databinding.FragmentFilmListBinding
import com.example.filmapplication.model.MovieModel
import com.example.filmapplication.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmListFragment : Fragment() {

    private var _binding: FragmentFilmListBinding? = null
    private val binding get() = _binding!!

    private val movieModel by viewModel<MovieViewModel>()
    private var adapter = RecyclerViewAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmListBinding.inflate(LayoutInflater.from(requireContext()),container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.errorText.visibility = View.GONE

        binding.search.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let {
                    val layoutManager = LinearLayoutManager(requireContext())
                    binding.recyclerView.layoutManager=layoutManager
                    binding.recyclerView.addItemDecoration(RecyclerViewItemDecoration(requireContext(),
                        R.drawable.divider))
                    movieModel.getDataFromAPI(it)
                    observeLiveData()
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })
    }

    fun observeLiveData() {
        movieModel.movieList.observe(viewLifecycleOwner, Observer {movies ->
            movies?.let {
                adapter = RecyclerViewAdapter(ArrayList(movies.data?.Search ?: arrayListOf()))
                binding.recyclerView.adapter = adapter
            }

        })

        movieModel.movieError.observe(viewLifecycleOwner, Observer { error->
            error?.let {
                if(error.data == true) {
                    binding.errorText .visibility = View.VISIBLE
                } else {
                    binding.errorText.visibility = View.GONE
                }
            }
        })

        movieModel.movieLoading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if (loading.data == true) {
                    binding.errorText.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}