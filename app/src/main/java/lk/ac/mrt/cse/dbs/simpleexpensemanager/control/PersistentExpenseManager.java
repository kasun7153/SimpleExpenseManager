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

package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

/**
 *
 */
public class PersistentExpenseManager extends ExpenseManager {
    FeedReaderDbHelper dbHelper;
    public PersistentExpenseManager(Context context) {

        setup(context);
    }

    @Override
    public void setup(Context context) {
        /*** Begin generating dummy data for In-Memory implementation ***/
        this.dbHelper = new FeedReaderDbHelper(context);

        TransactionDAO inMemoryTransactionDAO = new PersistentTransactionDAO(this.dbHelper);
        setTransactionsDAO(inMemoryTransactionDAO);

        AccountDAO inMemoryAccountDAO = new PersistentAccountDAO(this.dbHelper);
        setAccountsDAO(inMemoryAccountDAO);

        // dummy data
        Account dummyAcct1 = new Account("180535T", "BOC", "Kasun Chandika", 10000.0);
        getAccountsDAO().addAccount(dummyAcct1);


        /*** End ***/
    }
}
