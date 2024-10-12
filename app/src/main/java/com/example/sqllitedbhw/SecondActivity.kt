package com.example.sqllitedbhw

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sqllitedbhw.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    private var products: MutableList<Product> = mutableListOf()
    private var listViewAdapter: ListAdapter? = null
    private val db = DBHelper(this, null)

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)


        listViewAdapter = ListAdapter(this, products)
        binding.secondActivityMainListViewLV.adapter = listViewAdapter

        binding.secondActivitySaveButtonBTN.setOnClickListener{

            products.clear()
            if (binding.secondActivityProductEditTextET.text.isNotEmpty()
                && binding.secondActivityWeightEditTextET.text.isNotEmpty()
                && binding.secondActivityPriceEditTextET.text.isNotEmpty()) {

                val name = binding.secondActivityProductEditTextET.text.toString()
                val weight = binding.secondActivityWeightEditTextET.text.toString().toDouble()
                val price = binding.secondActivityPriceEditTextET.text.toString().toInt()

                db.addPerson(name,weight,price)

                val cursor = db.getInfo()
                if (cursor != null && cursor.moveToFirst()) {
                    cursor.moveToFirst()
                    val nameFromDB = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME))
                    val weightFromDB = cursor.getDouble(cursor.getColumnIndex(DBHelper.KEY_WEIGHT))
                    val priceFromDB = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_PRICE))
                    val product = Product(nameFromDB,weightFromDB,priceFromDB)
                    products.add(product)
                }
                while (cursor!!.moveToNext()){
                    val nameFromDB = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME))
                    val weightFromDB = cursor.getDouble(cursor.getColumnIndex(DBHelper.KEY_WEIGHT))
                    val priceFromDB = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_PRICE))
                    val product = Product(nameFromDB,weightFromDB,priceFromDB)
                    products.add(product)
                }
                cursor.close()

                listViewAdapter = ListAdapter(this, products)
                binding.secondActivityMainListViewLV.adapter = listViewAdapter
                listViewAdapter!!.notifyDataSetChanged()

                clearFields()

            } else {
                Toast.makeText(this, "Заполните необходимые поля",
                    Toast.LENGTH_SHORT).show()
            }

        }


        binding.secondActivityRemoveButtonBTN.setOnClickListener{
            db.removeAll()
            clearFields()
            products.clear()
            listViewAdapter = ListAdapter(this, products)
            binding.secondActivityMainListViewLV.adapter = listViewAdapter
            listViewAdapter!!.notifyDataSetChanged()
        }

        binding.secondActivityExitButtonBTN.setOnClickListener {
            this.finishAffinity()
        }

    }

    private fun clearFields() {
        binding.secondActivityProductEditTextET.text.clear()
        binding.secondActivityWeightEditTextET.text.clear()
        binding.secondActivityPriceEditTextET.text.clear()
    }


}