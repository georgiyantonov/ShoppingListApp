package com.example.shoppinglistapp.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.databinding.FragmentNewItemSheetBinding
import com.example.shoppinglistapp.entities.Item
import com.example.shoppinglistapp.ui.viewmodels.ItemViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NewItemSheet(var item: Item?) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewItemSheetBinding
    private lateinit var itemViewModel: ItemViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        if (item != null) {
            binding.tvItemTitle.text = resources.getString(R.string.bs_title_update)
            binding.btnSave.text = resources.getString(R.string.btn_update)
            val editable = Editable.Factory.getInstance()
            binding.tiItemName.text = editable.newEditable(item!!.name)
            binding.tiItemDescription.text = editable.newEditable(item!!.description)
            val amount = when(item?.amount){
                null -> ""
                else -> item!!.amount!!.toInt()
            }

            binding.tiItemAmount.text = editable.newEditable(amount.toString())
        } else {
            binding.tvItemTitle.text = resources.getString(R.string.bs_title_add)
        }
        itemViewModel = ViewModelProvider(activity)[ItemViewModel::class.java]
        binding.btnSave.setOnClickListener{
            saveItem()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewItemSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun saveItem() {
        val name = binding.tiItemName.text.toString().trim()
        val description = binding.tiItemDescription.text.toString().trim()
        val amount = binding.tiItemAmount.text.toString()
        if (name == ""){
            Toast.makeText(context, "Введите название", Toast.LENGTH_SHORT).show()
            return
        }
        if (item == null) {
            val newItem = Item(name, description, if(amount.isEmpty()) null else amount.toFloat())
            itemViewModel.addItem(newItem)
        } else {
            item!!.name = name
            item!!.description = description
            item!!.amount = amount.toFloat()

            itemViewModel.updateItem(item!!)
        }
        binding.tiItemName.setText("")
        binding.tiItemDescription.setText("")
        binding.tiItemAmount.setText("")
        dismiss()

    }

}