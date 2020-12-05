package com.mat.shoppinglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.mat.shoppinglist.databinding.RecyclerViewItemBinding

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private var data: List<Product> = listOf()

    fun updateData(list: List<Product>) {
        data = list
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val removeButton: MaterialButton = view.findViewById(R.id.bt_remove_product)
        val productNameTextView: MaterialTextView = view.findViewById(R.id.tv_product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.productNameTextView.text = data[position].name
    }

    override fun getItemCount(): Int = data.size
}