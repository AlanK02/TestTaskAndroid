package com.alan.feature_favorite.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alan.core.models.VacancyModel
import com.alan.core.utils.formatVacancyCount
import com.alan.core_ui.adapter.VacanciesAdapter
import com.alan.feature_favorite.databinding.FragmentFavouriteBinding
import com.alan.feature_favorite.presentation.viewModel.FavouriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var adapter: VacanciesAdapter
    private val viewModel by viewModels<FavouriteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteBinding.inflate(layoutInflater)
        adapter = VacanciesAdapter(clickedVacancy = {},
            clickedMoreVacancyButton = {},
            onLikedClicked = { status, id ->
                viewModel.updateFavourite(status, id)
            })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvVacancies.adapter = adapter

        viewModel.getFavourites().observe(viewLifecycleOwner) { res ->
            binding.vacancyCount.text =
                formatVacancyCount(requireContext(), res.count { it.isFavorite })
            binding.vacancyCount.isVisible = true
            adapter.submitList(res.filter { it.isFavorite }.map { VacancyModel(data = it) })
        }

    }
}