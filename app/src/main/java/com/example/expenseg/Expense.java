package com.example.expenseg;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense")
public class Expense {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name ="tittle")
    private String tittle;

    @ColumnInfo(name="amount")
    private int amount;

    @ColumnInfo(name="exptype")
    private String expType;

    public Expense(int id,String tittle,int amount,String expType)
    {
        this.id=id;
        this.amount=amount;
        this.tittle=tittle;
        this.expType=expType;
    }
    @Ignore
    public Expense(String tittle,int amount,String expType)
    {
        this.amount=amount;
        this.tittle=tittle;
        this.expType=expType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getExpType() {
        return expType;
    }

    public void setExpType(String expType) {
        this.expType = expType;
    }
}
