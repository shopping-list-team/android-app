package com.mat.shoppinglist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mat.shoppinglist.databinding.FragmentListBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ListFragment : Fragment(), ProductHandler {

    private val viewModel: ListViewModel by viewModel()
    private lateinit var _binding: FragmentListBinding
    private val binding get() = _binding
    private val args: ListFragmentArgs by navArgs()
    private val accessCode: String by lazy {
        args.accessCode.take(8)
    }
    private lateinit var listName: String
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
        listName = args.accessCode.takeLast(args.accessCode.length - 8)
        registerBackCallback()
        binding.rvProductList.layoutManager = LinearLayoutManager(requireActivity())
        adapter = ProductsAdapter(this)
        binding.rvProductList.adapter = adapter
        binding.tietListName.setText(listName)
        binding.tvAccessCode.text = accessCode
        binding.btAddNewProduct.setOnClickListener {
            sendNewProduct()
        }
        binding.tietListName.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus && binding.tietListName.text.toString() != listName) {
                val updatedList = ProductList(binding.tietListName.text.toString(), accessCode)
                viewModel.updateList(updatedList)
            }
        }
        binding.btRemoveList.setOnClickListener {
            val listToDelete = ProductList(listName, accessCode)
            viewModel.deleteList(listToDelete)
        }
        observeList()
        viewModel.loadProducts(accessCode)
        observeNewProduct()
        observeUpdatedList()
        observeDeletedList()
        observeDeletedProduct()
        observeUpdatedProduct()
    }

    private fun observeList() {
        viewModel.productList.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Success -> {
                    adapter.updateData(it.data)
                }
                is Result.Error -> {
                    shortToast(it.exception.localizedMessage ?: "undefined error")
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

    private fun sendNewProduct() {
        val text = binding.tietProductName.text.toString()
        if(text.isEmpty()) {
            shortToast("Product name can't be empty!")
            return
        }
        viewModel.sendNewProduct(text, 1, accessCode, false)
    }

    private fun observeNewProduct() {
        viewModel.newProduct.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Success -> {
                    viewModel.loadProducts(accessCode)
                    binding.tietProductName.setText("")
                    binding.tietProductName.clearFocus()
                }
                is Result.Error -> {
                    shortToast(it.exception.localizedMessage ?: "undefined error")
                }
            }
        }
    }

    private fun observeUpdatedList() {
        viewModel.updatedList.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Success -> {
                    shortToast("list name changed successfully")
                    listName = it.data.name
                }
                is Result.Error -> {
                    binding.tietListName.setText(listName)
                    shortToast(it.exception.localizedMessage ?: "undefined error")
                }
            }
        }
    }

    private fun observeDeletedList() {
        viewModel.deletedList.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Success -> {
                    val action = ListFragmentDirections.actionListFragmentToAccessFragment()
                    findNavController().navigate(action)
                    shortToast("$listName deleted")
                }
                is Result.Error -> {
                    shortToast(it.exception.localizedMessage ?: "undefined error")
                }
            }
        }
    }

    private fun observeDeletedProduct() {
        viewModel.deletedProduct.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Success -> {
                    viewModel.loadProducts(accessCode)
                }
                is Result.Error -> {
                    shortToast(it.exception.localizedMessage ?: "undefined error")
                }
            }
        }
    }

    private fun observeUpdatedProduct() {
        viewModel.updatedProduct.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Success -> {
                    viewModel.loadProducts(accessCode)
                }
                is Result.Error -> {
                    shortToast(it.exception.localizedMessage ?: "undefined error")
                }
            }
        }
    }

    override fun removeProduct(index: Int) {
        viewModel.deleteProduct((viewModel.productList.value as Result.Success).data[index])
    }

    override fun updateProduct(index: Int, bought: Boolean) {
        val product = (viewModel.productList.value as Result.Success).data[index].copy(is_bought = bought)
        viewModel.updateProduct(product)
    }

}