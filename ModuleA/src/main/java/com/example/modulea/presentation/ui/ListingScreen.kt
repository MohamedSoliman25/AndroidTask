package com.example.modulea.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modulea.R
import com.example.modulea.databinding.FragmentListingScreenBinding
import com.example.modulea.domain.model.UniversityItem
import com.example.modulea.presentation.adapters.UniversityListAdapter
import com.example.modulea.presentation.viewmodel.UniversityViewModel
import com.example.modulea.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ListingScreen : Fragment() {


    private var _binding: FragmentListingScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<UniversityViewModel>()

    private  val TAG = "ListingScreen"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListingScreenBinding.inflate(inflater, container, false)
        initRecyclerView()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadUniversities("United Arab Emirates")
        observeUniversities()

    }
    private fun observeUniversities(){
        lifecycleScope.launchWhenStarted {
            viewModel.universities.collectLatest { result ->
                result.onSuccess { universities ->
                    Log.d(TAG, "observeUniversities: "+universities.size)
                    if (universities.isNotEmpty()){
                        getUniversityAdapter()?.updateData(universities)
                    }
                }.onFailure {
                    Toast.makeText(requireContext(), "Failed to load data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        binding.rvUniversity.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = UniversityListAdapter(ArrayList(), ::onItemClick)
        }
    }
    private fun onItemClick(universityItem: UniversityItem) {

//        val bundle  = Bundle().apply {
//            putSerializable(Utils.UNIVERSITY_ITEM,universityItem)
//        }
        val bundle  = Bundle().apply {
            putString(Utils.UNIVERSITY_NAME,universityItem.name)
            universityItem.state_province?.let {
                putString(Utils.UNIVERSITY_STATE,universityItem.state_province)
            }
            putString(Utils.UNIVERSITY_COUNTRY,universityItem.country)
            putString(Utils.UNIVERSITY_COUNTRY_CODE,universityItem.alpha_two_code)
            if (universityItem.web_pages.isNotEmpty()){
                putString(Utils.UNIVERSITY_WEB,universityItem.web_pages[0])
            }
        }
        findNavController().navigate(R.id.action_listingScreen_to_detailsScreenFragment,bundle)
    }
    private fun getUniversityAdapter() =
        binding.rvUniversity.adapter as? UniversityListAdapter

    }
