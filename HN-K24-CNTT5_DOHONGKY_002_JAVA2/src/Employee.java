import java.util.Scanner;
public class Employee {
    private String empId;
    private String empName;
    private int age;
    private double salary;

    public Employee() {

    }

    public String getEmpId() {

        return empId;
    }
    public void setEmpId(String empId) {

        this.empId = empId;
    }

    public String getEmpName() {

        return empName;
    }
    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public int getAge() {

        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void inputData(Scanner sc) {
        System.out.print("Nhập mã NV: ");
        empId = sc.nextLine();

        System.out.print("Nhập tên: ");
        empName = sc.nextLine();

        System.out.print("Nhập tuổi: ");
        age = Integer.parseInt(sc.nextLine());

        System.out.print("Nhập lương: ");
        salary = Double.parseDouble(sc.nextLine());
    }

    public void displayData() {
        System.out.println("Mã: " + empId +
                " | Tên: " + empName +
                " | Tuổi: " + age +
                " | Lương: " + salary);
    }
}