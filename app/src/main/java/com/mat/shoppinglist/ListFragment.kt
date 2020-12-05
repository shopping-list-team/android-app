package com.mat.shoppinglist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mat.shoppinglist.databinding.FragmentAccessBinding
import com.mat.shoppinglist.databinding.FragmentListBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ListFragment : Fragment() {

    private val viewModel: ListViewModel by viewModel()
    private lateinit var _binding: FragmentListBinding
    private val binding get() = _binding
    private val args: ListFragmentArgs by navArgs()
    private val accessCode: String by lazy {
        args.accessCode.take(8)
    }
    private val listName: String by lazy {
        args.accessCode.takeLast(args.accessCode.length - 8)
    }
    private lateinit var adapter: ProductsAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        registerBackCallback()
        binding.rvProductList.layoutManager = LinearLayoutManager(requireActivity())
        adapter = ProductsAdapter()
        binding.rvProductList.adapter = adapter
        binding.tietListName.setText(listName)
        binding.tvAccessCode.text = accessCode
        observeList()
        viewModel.loadProducts(accessCode)
    }

    private fun observeList() {
        viewModel.productList.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Success -> {
                    adapter.updateData(it.data)
                    val dt = it.data
                    Log.i("daata", "pobrana")
                    println()
                }
                is Result.Error -> {
                    longToast(it.exception.localizedMessage ?: "undefined error")
                }
            }
        }
    }

    private fun registerBackCallback() {
        val callback = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = ListFragmentDirections.actionListFragmentToAccessFragment()
                findNavController().navigate(action)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

}