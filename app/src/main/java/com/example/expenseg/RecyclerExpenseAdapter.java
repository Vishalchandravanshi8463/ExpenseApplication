package com.example.expenseg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerExpenseAdapter extends RecyclerView.Adapter<RecyclerExpenseAdapter.ViewHolder> {

    Context context;
    ArrayList<Expense>arrExpense=new ArrayList<>();
    DatabaseHelper databaseHelper;

    public RecyclerExpenseAdapter(Context context,ArrayList<Expense>arrExpense,DatabaseHelper databaseHelper)
    {
       this.context=context;
       this.arrExpense=arrExpense;
       this.databaseHelper=databaseHelper;
    }
    @NonNull
    @Override
    public RecyclerExpenseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.expense_row,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerExpenseAdapter.ViewHolder holder, int position) {

        holder.txtTitle.setText(arrExpense.get(position).getTittle());
        int amountValue=arrExpense.get(position).getAmount();
        String amount=String.valueOf(amountValue);
        holder.txtAmount.setText(amount);

        String expType=arrExpense.get(position).getExpType();

        if(expType.equals("Expense"))
        {
            holder.txtAmount.setTextColor(ContextCompat.getColor(context,R.color.red));
        }
        else
        {
            holder.txtAmount.setTextColor(ContextCompat.getColor(context,R.color.green));
        }

        holder.llRow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteItems(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrExpense.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView txtTitle,txtAmount;
        LinearLayout llRow;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            txtTitle=itemView.findViewById(R.id.txtTitle);
            txtAmount=itemView.findViewById(R.id.txtAmount);
            llRow=itemView.findViewById(R.id.llRow);


        }
    }

    public void deleteItems(int pos)
    {
        AlertDialog dialog=new AlertDialog.Builder(context)
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete this")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        Expense expenseToDelte=arrExpense.get(pos);
                        databaseHelper.expenseDao().deleteNote(expenseToDelte);

                        ((MainActivity)context).showExpenses();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }
}
