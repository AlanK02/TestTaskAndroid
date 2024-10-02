package com.alan.feature_find_vacancy.presentation.screen

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alan.core.models.Vacancy
import com.alan.core.models.VacancyModel
import com.alan.core.utils.formatVacancyCount
import com.alan.core_ui.adapter.AdviceAdapter
import com.alan.core_ui.adapter.VacanciesAdapter
import com.alan.feature_find_vacancy.databinding.FragmentFindVacancyBinding
import com.alan.feature_find_vacancy.presentation.viewModel.FindVacancyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentFindVacancyBinding
    private val viewModel: FindVacancyViewModel by viewModels()
    private val offerAdapter = AdviceAdapter()

    private lateinit var vacanciesAdapter: VacanciesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindVacancyBinding.inflate(layoutInflater)
        vacanciesAdapter = VacanciesAdapter(clickedMoreVacancyButton = {
            viewModel.isExpanded.value = true
        }, clickedVacancy = { id ->
            val deepLink = Uri.parse("app://com.alan.find/details?id=$id")
            findNavController().navigate(deepLink)
        }, onLikedClicked = { prevStatus: Boolean, id: String ->
            viewModel.changeStatus(prevStatus, id)
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvOffers.adapter = offerAdapter
        binding.rvVacancies.adapter = vacanciesAdapter

        observeViewModel()
        onClickListener()

    }

    private fun onClickListener() {
        binding.apply {
            tabIcon.setOnClickListener {
                if (viewModel.isExpanded.value == true) {
                    viewModel.isExpanded.value = false
                }
            }
        }
    }

    private fun observeViewModel() {

        viewModel.apply {

            getVacancies().observe(viewLifecycleOwner) { res ->
                viewModel.saveVacanciesInViewModel(res)
                if (res.isEmpty()) viewModel.getDataRemotely()
                else if (viewModel.isExpanded.value == false || viewModel.isExpanded.value == null) vacanciesAdapter.submitList(
                    addButtonToList(viewModel.vacancies)
                ) else vacanciesAdapter.submitList(viewModel.vacancies.map { VacancyModel(it) })

                binding.vacancyCount.text = formatVacancyCount(requireContext(), res.count())
            }

            getAdvice().observe(viewLifecycleOwner) { res ->
                offerAdapter.submitList(res)
            }

            isLoading.observe(viewLifecycleOwner) { res ->
                binding.progressBar.isVisible = res
            }

            isExpanded.observe(viewLifecycleOwner) { res ->
                if (res == true) {
                    vacanciesAdapter.submitList(viewModel.vacancies.map {
                        VacancyModel(
                            it
                        )
                    })
                    binding.rvOffers.isVisible = false
                    binding.extraInfo.isVisible = true
                    binding.tabIcon.setImageResource(com.alan.core.R.drawable.ic_back)
                    binding.rvOffers.isVisible = false
                    binding.vacanciesTitle.isVisible = false

                } else if (res == false) {
                    binding.extraInfo.isVisible = false
                    binding.rvOffers.isVisible = true
                    binding.tabIcon.setImageResource(com.alan.core.R.drawable.ic_search)
                    binding.rvOffers.isVisible = true
                    binding.vacanciesTitle.isVisible = true
                    vacanciesAdapter.submitList(
                        addButtonToList(
                            viewModel.vacancies
                        )
                    )
                }
            }

        }

    }

}

fun addButtonToList(list: List<Vacancy>): List<VacancyModel> {
    val newList = mutableListOf<VacancyModel>()
    newList.addAll(list.take(3).map { VacancyModel(data = it) })
    newList.add(VacancyModel(data = null, button = true))
    return newList
}