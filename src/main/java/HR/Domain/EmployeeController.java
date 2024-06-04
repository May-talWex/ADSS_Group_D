package HR.Domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

import java.util.ArrayList;
import java.util.List;

public class EmployeeController {
    @JsonCreator
    public Employee JSONtoClass() {
        return null;
    }

    public EmployeeController() {

    }

    public Employee createEmployee(Branch branch) {
        System.out.println("Creating Employee");
        System.out.println("Enter workerId: ");
        int workerId = Integer.parseInt(System.console().readLine());
        System.out.println("Enter workerName: ");
        String workerName = System.console().readLine();
        System.out.println("Enter workerEmail: ");
        String workerEmail = System.console().readLine();
        System.out.println("Enter bankNumber: ");
        int bankNumber = Integer.parseInt(System.console().readLine());
        System.out.println("Enter branchNumber: ");
        int branchNumber = Integer.parseInt(System.console().readLine());
        System.out.println("Enter accountNumber: ");
        int accountNumber = Integer.parseInt(System.console().readLine());
        BankAccount bankAccount = new BankAccount(bankNumber, accountNumber, branchNumber);
        System.out.println("Enter workerSalary: ");
        float workerSalary = Float.parseFloat(System.console().readLine());
        Salary salary = new Salary(workerSalary, LocalDate.now());
        Employee employee = new Employee(workerId, workerName, workerEmail, bankAccount, branch, salary);
        System.out.println("Employee " + workerName + " created successfully");
        return employee;
    }


}
