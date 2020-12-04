package com.mat.shoppinglist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
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
        viewModel.list.observe(viewLifecycleOwner) {
            TODO()
        }
    }

}