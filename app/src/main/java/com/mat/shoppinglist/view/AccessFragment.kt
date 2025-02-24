package com.mat.shoppinglist.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mat.shoppinglist.*
import com.mat.shoppinglist.databinding.FragmentAccessBinding
import com.mat.shoppinglist.data.ProductList
import com.mat.shoppinglist.model.Result
import com.mat.shoppinglist.viewmodel.AccessViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.sample
import org.koin.android.viewmodel.ext.android.viewModel

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
        observeList(viewModel.list)
        observeList(viewModel.newList)
        binding.btOpenList.clicks().sample(1000)
            .onEach {
                triggerListGathering()
            }
            .launchIn(lifecycleScope)
        binding.mbNewList.setOnClickListener {
            if(!MainActivity.networkAvailable) {
                shortToast("Network not available!")
                return@setOnClickListener
            }
            popListNameDialog()
        }
    }

    private fun observeList(list: LiveData<Result<ProductList>>) {
        list.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Success -> {
                    val action =
                        AccessFragmentDirections.actionLoadList("${it.data.access_code}${it.data.name}")
                    findNavController().navigate(action)
                }
                is Result.Error -> {
                    shortToast(it.exception.localizedMessage ?: "undefined error")
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

    private fun popListNameDialog() {
        val input = EditText(requireActivity())
        val layout = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        input.layoutParams = layout
        val dialog = AlertDialog.Builder(requireActivity())
            .setTitle("New list")
            .setMessage("Provide list name")
            .setView(input)
            .setPositiveButton("Create") {
                dialogInterface: DialogInterface, _ ->
                    if(input.text.isNotEmpty()) {
                        viewModel.addNewList(input.text.toString())
                    } else {
                        shortToast("list name can't be empty")
                    }
                    dialogInterface.dismiss()
            }
            .setNegativeButton("Cancel") {
                dialogInterface: DialogInterface, _ ->
                    dialogInterface.dismiss()
            }
            .create()
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
    }

}