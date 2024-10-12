package com.example.sqllitedbhw

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sqllitedbhw.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private var role = mutableListOf(
        "Не выбрано",
        "Крановщик",
        "Строитель",
        "Бригадир",
        "Грузчик",
        "Разнорабочий"
    )
    private var persons: MutableList<Person> = mutableListOf()
    private var listViewAdapter: ListAdapter? = null
    private val db = DBHelper(this, null)

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            role
        )

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.secondActivityRoleSpinner.adapter = spinnerAdapter

        listViewAdapter = ListAdapter(this, persons)
        binding.secondActivityMainListViewLV.adapter = listViewAdapter

        binding.secondActivitySaveButtonBTN.setOnClickListener{

            if (binding.secondActivityNameEditTextET.text.isNotEmpty()
                && binding.secondActivityPhoneEditTextET.text.isNotEmpty()) {
                val name = binding.secondActivityNameEditTextET.text.toString()
                val phone = binding.secondActivityPhoneEditTextET.text.toString()
                val role = binding.secondActivityRoleSpinner.selectedItem.toString()

                db.addPerson(name,role,phone)

                binding.secondActivityNameEditTextET.text.clear()
                binding.secondActivityPhoneEditTextET.text.clear()
            } else {
                Toast.makeText(this, "Заполните необходимые поля",
                    Toast.LENGTH_SHORT).show()
            }

        }

        binding.secondActivityGetButtonBTN.setOnClickListener{

            clearListView()


            val cursor = db.getInfo()
            if (cursor != null && cursor.moveToFirst()) {
                cursor.moveToFirst()
                val name = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME))
                val phone = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PHONE))
                val role = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_ROLE))
                val person = Person(name,phone,role)
                persons.add(person)
            }
            while (cursor!!.moveToNext()){
                val name = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME))
                val phone = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PHONE))
                val role = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_ROLE))
                val person = Person(name,phone,role)
                persons.add(person)
            }
            cursor.close()

            listViewAdapter = ListAdapter(this, persons)
            binding.secondActivityMainListViewLV.adapter = listViewAdapter
            listViewAdapter!!.notifyDataSetChanged()

        }

        binding.secondActivityRemoveButtonBTN.setOnClickListener{
            db.removeAll()
            clearListView()
        }

        binding.secondActivityExitButtonBTN.setOnClickListener {
            this.finishAffinity()
        }

    }

    private fun clearListView() {
        persons.clear()
        binding.secondActivityNameEditTextET.text.clear()
        binding.secondActivityPhoneEditTextET.text.clear()
        listViewAdapter = ListAdapter(this, persons)
        binding.secondActivityMainListViewLV.adapter = listViewAdapter
        listViewAdapter!!.notifyDataSetChanged()
    }
}