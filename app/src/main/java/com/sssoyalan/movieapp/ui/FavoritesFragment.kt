package com.sssoyalan.movieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.sssoyalan.movieapp.R
import com.sssoyalan.movieapp.data.model.Movie
import com.sssoyalan.movieapp.data.model.MovieEntity
import com.sssoyalan.movieapp.ui.viewmodel.MainViewModel
import com.sssoyalan.movieapp.vo.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.fragment_favorites.img_not_found
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class FavoritesFragment : Fragment(), MainAdapter.OnMovieClickListener {

    private lateinit var adapter: MainAdapter
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getFavoriteMovies().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    val list = result.data.map {
                        Movie(
                            it.movieId,
                            it.releaseDate,
                            it.movieImg,
                            it.name,
                            it.description,
                            it.voteAverage,
                            it.adult
                        )
                    }.toMutableList()

                    adapter = MainAdapter(
                        requireContext(), list, this,true
                    )

                    rv_movies_favorites.adapter = adapter
                    img_not_found.visibility=if (result.data.isEmpty()) View.VISIBLE else View.GONE
                    (activity as AppCompatActivity).supportActionBar?.title = String.format(getString(
                                            R.string.favorite_movies)+adapter.itemCount.toString()+")")
                }
                is Resource.Failure -> {
                }
            }
        })
    }

    private fun setupRecyclerView() {
        rv_movies_favorites.layoutManager = GridLayoutManager(requireContext(),2)
    }

    override fun onMovieCLick(movie: Movie, position: Int, isDelete : Boolean) {
        if (isDelete){
            viewModel.deleteMovie(
                MovieEntity(
                    movie.movieId,
                    movie.releaseDate,
                    movie.movieImg,
                    movie.name,
                    movie.description,
                    movie.voteAverage,
                    movie.adult
                )
            )
            val count = adapter.deleteMovie(position)
            img_not_found.visibility=if (count>0) View.GONE else View.VISIBLE
            (activity as AppCompatActivity).supportActionBar?.title = String.format(getString(
                R.string.favorite_movies)+adapter.itemCount.toString()+")")
            Toast.makeText(requireContext(), getString(R.string.movie_deleted), Toast.LENGTH_SHORT).show()
        }else{
            val bundle = Bundle()
            bundle.putParcelable("movie", movie)
            findNavController().navigate(R.id.action_favoritesFragment_to_movieDetailFragment, bundle)
        }

    }
}