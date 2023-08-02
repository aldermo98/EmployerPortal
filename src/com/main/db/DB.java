package com.main.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.model.Employee;

public class DB {
    
    private Connection con;
    private final static String usr = "root";
    private final static String pw = "admin";
    
    public void dbConnect(){
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.out.println("failed");
            e.printStackTrace();
        }

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fullstackjava", usr, pw);
        } catch (SQLException e) {
            System.out.println("failed");
            e.printStackTrace();
        }
    }

    public void dbClose(){
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addEmployee(Employee e) {
        dbConnect();
        String sql = "insert into employees(name, city, salary, department) VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, e.getName());
            pstmt.setString(2, e.getCity());
            pstmt.setDouble(3, e.getSalary());
            pstmt.setString(4, e.getDepartment());

            pstmt.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        dbClose();
    }

    public List<Employee> fetchEmployees() {
        dbConnect();
        List<Employee> employees = new LinkedList<>();
        String sql = "select * from employees";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                employees.add(new Employee(
                    rs.getInt("id"),
                    rs.getString("name"), 
                    rs.getString("city"), 
                    rs.getDouble("salary"), 
                    rs.getString("department")
                ));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        dbClose();
        return employees;
    }

    public void updateEmployee(Employee e) {
        dbConnect();
        String sql = "update employees set name=?, city=?, salary=?, department=? where id=?";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, e.getName());
            pstmt.setString(2, e.getCity());
            pstmt.setDouble(3, e.getSalary());
            pstmt.setString(4, e.getDepartment());
            pstmt.setInt(5, e.getId());

            pstmt.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        dbClose();
    }

    public void removeEmployee(Employee e) {
        dbConnect();
        String sql = "delete from employees where id=?";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, e.getId());

            pstmt.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        dbClose();
    }
}
