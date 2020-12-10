package com.mat.shoppinglist

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class ProductsAdapter(
        private val handler: ProductHandler
) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private var data: List<Product> = listOf()
    private lateinit var isBoughtList: List<Boolean>

    fun updateData(list: List<Product>) {
        data = list
        isBoughtList = data.map {
            it.is_bought
        }
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
        holder.productNameTextView.setOnClickListener {
            handler.updateProduct(position, !isBoughtList[position])
        }
        holder.removeButton.setOnClickListener {
            handler.removeProduct(position)
        }
        holder.productNameTextView.paintFlags =
                if(isBoughtList[position])
                        (holder.productNameTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                else
                        (holder.productNameTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv())
    }

    override fun getItemCount(): Int = data.size
}