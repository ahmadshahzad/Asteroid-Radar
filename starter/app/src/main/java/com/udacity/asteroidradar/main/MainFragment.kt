package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorDestinationBuilder
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.detail.DetailFragmentArgs
import com.udacity.asteroidradar.main.reposiory.AsteroidRepository
import com.udacity.asteroidradar.network.retrofit.AsteroidService
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding

    private val viewModel by viewModels<MainViewModel> {
        val dataSource = AsteroidDatabase.getInstance(requireContext()).asteroidDatabaseDao
        MainViewModelFactory(AsteroidRepository(AsteroidService.api, dataSource))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.imageOfTheDay.observe(viewLifecycleOwner, {
            it?.let {
                binding.imageOfTheDay = it
            }
        })

        val asteroidAdapter = AsteroidsAdapter(AsteroidClickListener {
            findNavController().navigate(MainFragmentDirections.actionShowDetail(it.toAsteroidDataModel()))
        })
        with(binding.asteroidRecycler) {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = asteroidAdapter
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(DividerItemDecoration(requireActivity(), DividerItemDecoration.HORIZONTAL))
        }
        viewModel.asteroids.observe(viewLifecycleOwner, {
            it?.let {
                lifecycleScope.launch {
                    asteroidAdapter.submitList(it)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_today_menu -> viewModel.updateAsteroidsFilter(AsteroidService.GetAsteroidsFilter.TODAY)
            R.id.show_week_menu -> viewModel.updateAsteroidsFilter(AsteroidService.GetAsteroidsFilter.WEEK)
            R.id.show_saved_menu -> viewModel.updateAsteroidsFilter(AsteroidService.GetAsteroidsFilter.SAVED)
        }
        return true
    }
}
