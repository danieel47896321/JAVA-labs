package lab3;

public class Salary {
	public static void main(String[] args) {
		Employee employee1,employee2,employee3,employee4;
		employee1 = new Employee(6000);
		employee2 = new SalesEmployee(6000,100);
		employee3 = new Manager(6000,5);
		employee4 = new SalesManager(6000, 2, 100);
		System.out.println("The Salary for "+employee1.getName()+": "+employee1.ComputeSalary());
		System.out.println("The Salary for "+employee2.getName()+": "+employee2.ComputeSalary());
		System.out.println("The Salary for "+employee3.getName()+": "+employee3.ComputeSalary());
		System.out.println("The Salary for "+employee4.getName()+": "+employee4.ComputeSalary());
	}
}
