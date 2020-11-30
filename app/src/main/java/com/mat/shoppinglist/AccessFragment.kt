package com.mat.shoppinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mat.shoppinglist.databinding.FragmentAccessBinding

class AccessFragment : Fragment() {

    private lateinit var _binding: FragmentAccessBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccessBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

}