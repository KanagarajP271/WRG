package com.example.wrg.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.wrg.model.Employee;

// adding annotation for our database entities and db version.
@Database(entities = {Employee.class}, version = 2, exportSchema = false)
public abstract class EmployeeDatabase extends RoomDatabase {

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile EmployeeDatabase INSTANCE;
    // below line is to create abstract variable for dao.
    public abstract EmployeeDao Dao();

    // on below line we are getting instance for our database.
   public static EmployeeDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EmployeeDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EmployeeDatabase.class, "employee_db")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
