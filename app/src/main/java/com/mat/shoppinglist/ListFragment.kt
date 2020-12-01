package com.mat.shoppinglist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mat.shoppinglist.databinding.FragmentAccessBinding
import com.mat.shoppinglist.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private lateinit var _binding: FragmentListBinding
    private val binding get() = _binding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

}