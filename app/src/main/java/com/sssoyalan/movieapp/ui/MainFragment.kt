package com.sssoyalan.movieapp.ui

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.sssoyalan.movieapp.R
import com.sssoyalan.movieapp.data.model.Movie
import com.sssoyalan.movieapp.ui.viewmodel.MainViewModel
import com.sssoyalan.movieapp.vo.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : Fragment(), MainAdapter.OnMovieClickListener, View.OnClickListener {

    private lateinit var adapter: MainAdapter

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearchView()
        setupObservers()
        img_close.setOnClickListener(this)
    }

    private fun setupObservers() {
        viewModel.fetchMoviesList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    rv_movies.adapter =
                        MainAdapter(requireContext(), result.data.toMutableList(), this,false)
                    adapter = rv_movies.adapter as MainAdapter
                    img_not_found.visibility=if (result.data.isEmpty()) View.VISIBLE else View.GONE
                }
                is Resource.Failure -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Error getting data ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        viewModel.fetchMoviesFilterList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    rv_movies.adapter =
                        MainAdapter(requireContext(), result.data.toMutableList(), this,false)
                    adapter = rv_movies.adapter as MainAdapter
                    img_not_found.visibility=if (result.data.isEmpty()) View.VISIBLE else View.GONE
                }
                is Resource.Failure -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Error getting data ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun setupSearchView() {
        searchView.onActionViewExpanded()
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setMovie(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.length!! >=3){
                    viewModel.setMovie(newText)
                }
                img_close.visibility=imgCloseVisibility(newText)
                return false
            }
        })
    }

    private fun setupRecyclerView() {
        rv_movies.layoutManager = GridLayoutManager(requireContext(),2)
    }

    private fun imgCloseVisibility(txt : String): Int {
        return if (txt.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun onMovieCLick(movie: Movie, position: Int, isDelete : Boolean) {
        if (!isDelete){
            val bundle = Bundle()
            bundle.putParcelable("movie", movie)
            findNavController().navigate(R.id.action_mainFragment_to_movieDetailFragment, bundle)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.settings_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_search -> showSearchView()
            R.id.menu_sort -> showSortDialog()
            R.id.menu_favorites ->
                findNavController().navigate(R.id.action_mainFragment_to_favoritesFragment)
            R.id.menu_settings ->
                findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
            R.id.menu_about ->
                findNavController().navigate(R.id.action_mainFragment_to_aboutFragment)
            else -> return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showSortDialog() {
        var mResult: String = ""
        val listItems = resources.getStringArray(R.array.sort_values_array)
        val listener = DialogInterface.OnClickListener { dialogInterface, i ->
            mResult = listItems.get(i)
        }
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(resources.getString(R.string.shortdialog_title))
            .setSingleChoiceItems(listItems, -1, listener)
            .setPositiveButton(getString(R.string.ok), DialogInterface.OnClickListener { dialog, which ->
                if(adapter.itemCount>0)
                adapter.sortMovies(mResult)
            })
            .setNegativeButton(getString(R.string.cancel), DialogInterface.OnClickListener { dialog, which ->
                builder.setCancelable(true)
            })

        builder.create()
        builder.show()
    }

    private fun hideSearchview(){
        cl_search_view.visibility = View.GONE
    }

    private fun showSearchView(){
        cl_search_view.visibility = View.VISIBLE
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.img_close-> hideSearchview()
            else -> Log.d("TAG", "wrong input")
        }
    }
}