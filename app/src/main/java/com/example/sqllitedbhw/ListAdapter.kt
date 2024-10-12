package com.example.sqllitedbhw

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.sqllitedbhw.databinding.ItemListViewBinding

class ListAdapter(context: Context, products: MutableList<Product>) :
    ArrayAdapter<Product>(context, R.layout.item_list_view, products) {

        private lateinit var binding: ItemListViewBinding

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val person= getItem(position)

        binding = if (convertView == null) {
            ItemListViewBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            ItemListViewBinding.bind(convertView)
        }

        binding.itemListViewNameProductTV.text = person?.name
        binding.itemListViewWeightTV.text = person?.weight.toString()
        binding.itemListViewPriceTV.text = "${person?.price.toString()} руб."

        return binding.root
    }
}