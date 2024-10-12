package com.example.sqllitedbhw

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.sqllitedbhw.databinding.ItemListViewBinding

class ListAdapter(context: Context, persons: MutableList<Person>) :
    ArrayAdapter<Person>(context, R.layout.item_list_view, persons) {

        private lateinit var binding: ItemListViewBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val person= getItem(position)

        binding = if (convertView == null) {
            ItemListViewBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            ItemListViewBinding.bind(convertView)
        }

        binding.itemListViewNameTV.text = person?.name
        binding.itemListViewPhoneTV.text = person?.phone
        binding.itemListViewRoleTV.text = person?.role

        return binding.root
    }
}