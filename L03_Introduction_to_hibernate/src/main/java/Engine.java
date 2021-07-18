import entities.Address;
import entities.Employee;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class Engine implements Runnable{

    private final EntityManager entityManager;
    private BufferedReader bufferedReader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        System.out.println("Select ex number:");
        try {
            int exNumber = Integer.parseInt(bufferedReader.readLine());

            switch (exNumber) {
                case 2:
                    exTwoChangeCasing();
                case 3:
                    exTreeContainsEmployee();
                case 4:
                    exFourEmployeesWithSalaryOver50000();
                case 5:
                    exFiveEmployeesFromDepartment();
                case 6:
                    exSixAddingANewAddressAndUpdatingEmployee();
                case 7:
                    exSevenAddressesWithEmployeeCount();
                case 8:
                    exEightGetEmployeeWithProject();
                case 9:
                    exNineFindLatest10Projects();
                case 10:
                    exTenIncreaseSalaries();
                case 11:
                    exElevenFindEmployeesByFirstName();
                case 12:
                    exTwelveEmployeesMaximumSalaries();
                case 13:
                    exThirteenRemoveTowns();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    private void exThirteenRemoveTowns() throws IOException {
        System.out.println("Enter town name:");
        String townName = bufferedReader.readLine();

        Town town = entityManager
                .createQuery("SELECT t FROM Town t " +
                        "WHERE t.name = :t_name", Town.class)
                .setParameter("t_name", townName)
                .getSingleResult();

        int affectedRows =  removeAddressesByTownId(town.getId());

        entityManager.getTransaction().begin();
        entityManager.remove(town);
        entityManager.getTransaction().commit();

        System.out.printf("%d address in %s is deleted",
                affectedRows,
                townName);
    }

    private int removeAddressesByTownId(Integer id) {

        List<Address> addresses = entityManager.createQuery("SELECT a FROM Address a " +
                "WHERE a.town.id = :p_id", Address.class)
                .setParameter("p_id", id)
                .getResultList();

        entityManager.getTransaction().begin();
        addresses.forEach(entityManager::remove);
        entityManager.getTransaction().commit();

        return addresses.size();
    }

    @SuppressWarnings("unchecked")
    private void exTwelveEmployeesMaximumSalaries() {
        //Не е добра практика да се използва NativeQuery
        List<Object[]> rows = entityManager.createNativeQuery("SELECT d.name, MAX(e.salary) AS m_salary FROM departments d\n" +
                "join employees e on d.department_id = e.department_id\n" +
                "GROUP BY d.name\n" +
                "HAVING m_salary NOT BETWEEN 30000 AND 70000")
                .getResultList();

        //TODO да напрвя принтирането
    }

    private void exElevenFindEmployeesByFirstName() {
        //TODO да нарпавя 11 задача
    }

    private void exTenIncreaseSalaries() {
        entityManager.getTransaction().begin();
        int affectedRows = entityManager
                .createQuery("UPDATE Employee e " +
                        "SET e.salary = e.salary * 1.2 " +
                        "WHERE e.department.id IN :ids")
                .setParameter("ids", Set.of(1, 2, 4, 11))
                .executeUpdate();
        entityManager.getTransaction().commit();
        System.out.println(affectedRows);
    }

    private void exNineFindLatest10Projects() {
        //TODO да нарпавя 09 задача
    }

    private void exEightGetEmployeeWithProject() {
        Employee employee = entityManager.find(Employee.class, 147);
        //TODO да нарпавя 08 задача
    }

    private void exSevenAddressesWithEmployeeCount() {
        List<Address> addresses = entityManager
                .createQuery("SELECT a FROM Address a " +
                        "ORDER BY a.employees.size DESC", Address.class)
                .setMaxResults(10)
                .getResultList();

        addresses.forEach(address -> {
            System.out.printf("%s , %s - %d employees%n",
                    address.getText(),
                    address.getTown() == null ? "unknow" : address.getTown().getName(),
                    address.getEmployees().size());
        });
    }

    private void exSixAddingANewAddressAndUpdatingEmployee() throws IOException {
        System.out.println("Enter employee last name:");
        String lastName = bufferedReader.readLine();

        Employee employee = entityManager
                .createQuery("SELECT e FROM Employee e " +
                        "WHERE e.lastName = :l_name", Employee.class)
                .setParameter("l_name", lastName)
                .getSingleResult();

        Address address = createAddress("Vitoshka 15");

        entityManager.getTransaction().begin();
        employee.setAddress(address);
        entityManager.getTransaction().commit();
    }

    private Address createAddress(String addressText) {
        Address address = new Address();
        address.setText(addressText);

        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();

        return address;
    }

    private void exFiveEmployeesFromDepartment() {
        //Diane Margheim from Research and Development - $40900.00

        entityManager.createQuery("SELECT e FROM Employee e " +
                "WHERE e.department.name = :d_name " +
                "ORDER BY e.salary, e.id", Employee.class)
                .setParameter("d_name", "Research and Development")
                .getResultStream()
                .forEach(employee -> {
                    System.out.printf("%s %s from %s - $%.2f%n",
                            employee.getFirstName(),
                            employee.getLastName(),
                            employee.getDepartment().getName(),
                            employee.getSalary());
                });
    }

    private void exFourEmployeesWithSalaryOver50000() {
        entityManager
                .createQuery("SELECT e FROM Employee e " +
                "WHERE e.salary > :min_salary", Employee.class)
                .setParameter("min_salary", BigDecimal.valueOf(50000))
                .getResultStream()
                .map(Employee::getFirstName)
                .forEach(System.out::println);

    }

    private void exTreeContainsEmployee() throws IOException {
        System.out.println("Enter employee full name:");
        String[] fullName = bufferedReader.readLine().split("\\s+");

        String firstName = fullName[0];
        String lastName = fullName[1];

        Long singleResult = entityManager.createQuery("SELECT count(e) FROM Employee e " +
                " WHERE e.firstName = :f_name AND e.lastName = :l_name", Long.class)
                .setParameter("f_name", firstName)
                .setParameter("l_name", lastName)
                .getSingleResult();

        System.out.println(singleResult == 0
        ? "No" : "Yes");

    }

    private void exTwoChangeCasing() {
        // При update и delete винаги трявба да има транзакция, която дасе отваря и след това да се изпълнява.
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("UPDATE Town t " +
                "SET t.name = upper(t.name) " +
                "WHERE length(t.name) < 5 ");

        System.out.println(query.executeUpdate());

        entityManager.getTransaction().commit();
    }
}
