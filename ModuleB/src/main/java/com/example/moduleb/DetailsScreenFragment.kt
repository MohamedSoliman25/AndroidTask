package com.example.moduleb

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moduleb.databinding.FragmentDetailsScreenBinding


class DetailsScreenFragment : Fragment() {


//    val args:DetailsScreenFragmentArgs by navArgs()
    private val TAG = "DetailsScreenFragment"

    private var _binding: FragmentDetailsScreenBinding? = null
    private val binding get() = _binding!!

    private var name:String = ""
    private var state:String? = null
    private var country:String = ""
    private var countryCode:String = ""
    private var webPage:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        receiveUniversityDate()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsScreenBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchDataUI()
        handleRefreshButton()
    }

    private fun receiveUniversityDate(){
        //val user = arguments?.getSerializable("universityItem") as? UniversityItem
        name = arguments?.getString("universityName").toString()
        state = arguments?.getString("universityState")
        country = arguments?.getString("universityCountry").toString()
        countryCode = arguments?.getString("universityCountryCode").toString()
        webPage = arguments?.getString("universityWeb").toString()
    }

    fun fetchDataUI(){
        binding.apply {
            universityName.text = name
            Log.d(TAG, "fetchDataUIbjbj: "+state)
            state?.let {
                universityState.text = state
            }?:"".apply {
                if (this.isEmpty()){
                    universityState.visibility = View.GONE
                }
            }
            universityCountry.text = country
            universityCountryCode.text = countryCode
            universityWebPage.text = webPage
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleRefreshButton(){
        binding.apply {
            refresh.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
}