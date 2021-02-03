/*
 * Copyright 2015 Department of Computer Science and Engineering, University of Moratuwa.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *                  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.FeedReaderDbHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * This is an In-Memory implementation of TransactionDAO interface. This is not a persistent storage. All the
 * transaction logs are stored in a LinkedList in memory.
 */
public class PersistentTransactionDAO implements TransactionDAO {
    private final List<Transaction> transactions;
    FeedReaderDbHelper dbHelper;
    public PersistentTransactionDAO(FeedReaderDbHelper dbHelper) {
        this.dbHelper=dbHelper;
        transactions = new LinkedList<>();
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        SQLiteDatabase db =this.dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("accountNo",accountNo );
        values.put("date", date.toString());
        values.put("expenseType",expenseType.toString());
        values.put("amount", amount);

        long newRowId = db.insert("transactions", null, values);
        System.out.println("New Transaction row added to the database");
        System.out.println(accountNo);
        System.out.println(date.toString());
        System.out.println(expenseType.toString());
        System.out.println(amount);

        Transaction transaction = new Transaction(date, accountNo, expenseType, amount);
        transactions.add(transaction);

    }

    @Override
    public List<Transaction> getAllTransactionLogs(){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Transaction> array_list = new ArrayList<Transaction>();
        Cursor res =  db.rawQuery( "select * from transactions", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            String date=res.getString(res.getColumnIndex("date"));
            String accountNo=res.getString(res.getColumnIndex("accountNo"));
            String expenseType=res.getString(res.getColumnIndex("expenseType"));
            String amount=res.getString(res.getColumnIndex("amount"));

            try {
                Date date1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(date);
                Transaction t= new Transaction(date1,accountNo,ExpenseType.valueOf(expenseType),Double.valueOf(amount));
                array_list.add(t);
            }
            catch(Exception e) {
                System.out.println("Error in Date");
                System.out.println(e);
            }
            res.moveToNext();
        }
        System.out.println(array_list);
        return array_list;

        //return transactions;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Transaction> array_list = new ArrayList<Transaction>();
        Cursor res =  db.rawQuery( "select * from transactions limit ?", new String[] {Integer.toString(limit)}  );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            String date=res.getString(res.getColumnIndex("date"));
            String accountNo=res.getString(res.getColumnIndex("accountNo"));
            String expenseType=res.getString(res.getColumnIndex("expenseType"));
            String amount=res.getString(res.getColumnIndex("amount"));

            try {
                Date date1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(date);
                Transaction t= new Transaction(date1,accountNo,ExpenseType.valueOf(expenseType),Double.valueOf(amount));
                array_list.add(t);
            }
            catch(Exception e) {
                System.out.println("Error in Date");
                System.out.println(e);
            }
            res.moveToNext();
        }

        return array_list;
    }

}
