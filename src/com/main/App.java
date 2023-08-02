package com.main;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.main.db.DB;
import com.model.Employee;

public class App {
    public static void main(String[] args) {
        DB db = new DB();

        while(true){
            Scanner sc = new Scanner(System.in);
            showMenu();

            int input = sc.nextInt();
            if(input==0){
                System.out.println("Exiting...");
                break;
            }
            switch(input){
                case 1:
                    insertEmployee(db);
                break;
                case 2:
                    listEmployees(db);
                break;
                case 3:
                    updateEmployee(db);
                break;
                case 4:
                    removeEmployee(db);
                break;
                default: System.out.println("Not a valid entry.");
            }
        }
        
    }

    private static void showMenu(){
        System.out.println("******Employer Portal**********");
        System.out.println("1. Insert employee");
        System.out.println("2. Show all employees");
        System.out.println("3. Update employee");
        System.out.println("4. Remove employee");
        System.out.println("0. Exit");
    }

    private static void insertEmployee(DB db){
        Scanner sc = new Scanner(System.in);
        Employee e = new Employee();

        System.out.println("Enter name: ");
        e.setName(sc.nextLine());

        System.out.println("Enter city: ");
        e.setCity(sc.next());

        System.out.println("Enter salary: ");
        e.setSalary(sc.nextDouble());

        System.out.println("Enter department: ");
        e.setDepartment(sc.next());

        db.addEmployee(e);
        System.out.println("Employee inserted...");
    }

    private static void listEmployees(DB db) {
        List<Employee> employees = db.fetchEmployees();
        if(employees.size()==0)
            System.out.println("No Employees...");
        for(Employee e : employees)
            System.out.println(e.toString());
    }

    private static void updateEmployee(DB db) {
        while(true){
            Scanner sc = new Scanner(System.in);

            System.out.println("Search for employee: ");
            String name = sc.nextLine();

            List<Employee> emp = db.fetchEmployees().stream()
                .filter(e->e.getName().equals(name))
                .collect(Collectors.toList());

            if(emp.isEmpty()){
                System.out.println("Employee not found...");
            }else{
                System.out.println("Enter name: ");
                emp.get(0).setName(sc.next());

                System.out.println("Enter city: ");
                emp.get(0).setCity(sc.next());

                System.out.println("Enter salary: ");
                emp.get(0).setSalary(sc.nextDouble());

                System.out.println("Enter department: ");
                emp.get(0).setDepartment(sc.next());

                db.updateEmployee(emp.get(0));

                System.out.println("Employee updated...");

                break;
            }
        }
    }

    private static void removeEmployee(DB db) {
         while(true){
            Scanner sc = new Scanner(System.in);

            System.out.println("Search for employee: ");
            String name = sc.nextLine();

            List<Employee> emp = db.fetchEmployees().stream()
                .filter(e->e.getName().equals(name))
                .collect(Collectors.toList());

            if(emp.isEmpty()){
                System.out.println("Employee not found...");
            }else{
                db.removeEmployee(emp.get(0));
                System.out.println("Employee removed...");

                break;
            }
        }
    }
}
