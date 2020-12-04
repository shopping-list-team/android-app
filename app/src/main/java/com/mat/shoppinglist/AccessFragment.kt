package com.mat.shoppinglist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mat.shoppinglist.databinding.FragmentAccessBinding
import org.koin.android.ext.android.bind
import org.koin.android.viewmodel.ext.android.viewModel
import retrofit2.HttpException
import java.lang.Exception

class AccessFragment : Fragment() {

    private lateinit var _binding: FragmentAccessBinding
    private val binding get() = _binding
    private val viewModel: AccessViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        switchLayouts(true)
        observeList()
        binding.btOpenList.setOnClickListener {
            triggerListGathering()
        }
    }

    private fun observeList() {
        viewModel.list.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Success -> {
                    val action = AccessFragmentDirections.actionLoadList(it.data.access_code)
                    findNavController().navigate(action)
                }
                is Result.Error -> {
                    longToast(it.exception.localizedMessage ?: "undefined error")
                    switchLayouts(true)
                }
            }

        }
    }

    private fun triggerListGathering() {
        if(!MainActivity.networkAvailable) {
            shortToast("Network not available!")
            return
        }

        val accessCode = binding.tietAccessCode.text ?: return
        if(accessCode.length < 8) {
            shortToast("Access code is too short!")
            return
        }
        switchLayouts(false)
        viewModel.loadList(accessCode.toString())
    }

    private fun switchLayouts(contentLayout: Boolean) {
        if(contentLayout) {
            binding.contentLayout.visibility = View.VISIBLE
            binding.noContentLayout.visibility = View.INVISIBLE
        } else {
            binding.contentLayout.visibility = View.INVISIBLE
            binding.noContentLayout.visibility = View.VISIBLE
        }
    }

}