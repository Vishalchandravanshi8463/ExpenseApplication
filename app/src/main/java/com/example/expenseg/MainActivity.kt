package com.example.expenseg

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expenseg.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var fabAdd:FloatingActionButton
    lateinit var binding:ActivityMainBinding
    lateinit var databaseHelper: DatabaseHelper
    lateinit var recyclerExpenses:RecyclerView
    lateinit var txtTotalExpense:TextView
    lateinit var txtTotalIncome:TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        databaseHelper= DatabaseHelper.getInstance(this)
        recyclerExpenses=binding.recyclerExpenses
        recyclerExpenses.layoutManager=GridLayoutManager(this,1)

        fabAdd=binding.fabtn
        txtTotalExpense=binding.txtTotalExpense
        txtTotalIncome=binding.txtTotalIncome

        showExpenses()
        fabAdd.setOnClickListener {

            val dialog=Dialog(this@MainActivity)
            dialog.setContentView(R.layout.add_expense)

            val edtTitle:EditText
            val edtAmount:EditText
            val radioGroup:RadioGroup
            val radioIncome:RadioButton
            val radioExpenses:RadioButton
            val btnAdd:Button

            edtAmount=dialog.findViewById(R.id.edtAmount)
            edtTitle=dialog.findViewById(R.id.edtTittle)
            radioGroup=dialog.findViewById(R.id.radioGroup)
            radioIncome=dialog.findViewById(R.id.radioIncome)
            radioExpenses=dialog.findViewById(R.id.radioExpense)
            btnAdd=dialog.findViewById(R.id.btnAdd)

            btnAdd.setOnClickListener {

                val radioButtonValue=if(radioExpenses.isChecked)"Expense" else "Income"

                val title=edtTitle.text.toString()
                val amounts=edtAmount.text.toString()

                if(title.isNotBlank() && amounts.isNotBlank())
                {
                    try {
                        val amount=amounts.toInt()
                        val newExpense=Expense(title,amount,radioButtonValue)
                        databaseHelper.expenseDao().addExpense(newExpense)

                        Toast.makeText(this@MainActivity, "Saved", Toast.LENGTH_SHORT).show()

                        showExpenses()
                        dialog.dismiss()
                    }
                    catch (e:java.lang.NumberFormatException)
                    {
                        Toast.makeText(this@MainActivity, "Enter Amount only", Toast.LENGTH_SHORT).show()
                    }
                }
                else
                {
                    Toast.makeText(this@MainActivity, "Field can not be empty", Toast.LENGTH_SHORT).show()
                }
            }

            dialog.show()
        }

    }

    fun showExpenses()
    {
        val arrExpense=databaseHelper.expenseDao().getExpense()

        if(arrExpense.isNotEmpty())
        {




            recyclerExpenses.visibility=View.VISIBLE
            recyclerExpenses.adapter=RecyclerExpenseAdapter(this,arrExpense as ArrayList<Expense>,databaseHelper)

            var totalIncomeN=0;
            var totalExpenseN=0;

            for(expense in arrExpense)
            {
                if(expense.expType=="Expense")
                {
                    totalExpenseN+=expense.amount
                }
                else
                {
                    totalIncomeN+=expense.amount
                }
            }
            txtTotalExpense.text="$totalExpenseN"
            txtTotalIncome.text="$totalIncomeN"

        }
        else
        {
            recyclerExpenses.visibility=View.GONE
            Toast.makeText(this@MainActivity, "Database is empty", Toast.LENGTH_SHORT).show()
        }
        for(expense in arrExpense)
        {

        }
    }
}