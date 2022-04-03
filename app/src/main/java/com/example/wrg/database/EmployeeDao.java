package com.example.wrg.database;


import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.wrg.model.Employee;

import java.util.List;

@androidx.room.Dao
public interface EmployeeDao {

    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAllEmployees(List<Employee> employeeModel);


    @Query("SELECT * FROM employee_detail")
    public List<Employee> getAllEmployees();

    //select workout by id
    @Query("SELECT * FROM employee_detail WHERE id=:id")
    public Employee getEmployeeByID(int id);

    //delete all employee
    @Query("DELETE FROM employee_detail")
    public void deleteAllEmployees();
}
