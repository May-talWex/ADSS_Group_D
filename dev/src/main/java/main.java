import HR.Data.BranchDAO;
import HR.Data.EmployeesDAO;
import HR.Domain.BankAccount;
import HR.Domain.Employee;
import HR.Domain.Salary;
import HR.Presentation.HRMainMenu;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class main {
    public static void main(String[] args) throws Exception {
//        EmployeesDAO e = new EmployeesDAO();
//        BranchDAO b = new BranchDAO();
//        e.removeEmployeeFromDatabase(1009);
//        e.addEmployeeToDatabase(new Employee(1009, "Ido", "test", new BankAccount(1, 0, 0), b.getBranchFromDatabase(9999), new Salary(1)));
//        Employee emp = EmployeesDAO.getEmployeeById(1009);
//        System.out.println(emp);
//        String input = "9999\n1009\n1\n1\n\n3";//\nMilk\n1001\nyes\nyes\n2025-01-01\nDP\nMLK1\n5\n7\n";
////        System.in.reset();
//        InputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
        HRMainMenu.DisplayHRMainMenu();

//        e.removeEmployeeFromDatabase(1009);

    }
}
