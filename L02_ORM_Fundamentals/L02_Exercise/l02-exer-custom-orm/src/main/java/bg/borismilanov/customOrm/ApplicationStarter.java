package bg.borismilanov.customOrm;

import bg.borismilanov.customOrm.entity.Address;
import bg.borismilanov.customOrm.entity.Employee;
import bg.borismilanov.customOrm.entity.User;
import ormFramework.core.EntityManager;
import ormFramework.core.EntityManagerFactory;

import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class ApplicationStarter {
    public static void main(String[] args) throws SQLException, URISyntaxException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {

        EntityManager entityManager = EntityManagerFactory.create(
                "mysql",
                "localhost",
                3306,
                "root",
                "",
                "test_orm",
                ApplicationStarter.class
        );

//        User user = new User("Pesho", 17);
//        User maria = new User("maria", 25);
//
//        entityManager.persist(maria);

//        User pesho = entityManager.findById(1, User.class);
//        User maria = entityManager.findById(6, User.class);

//        pesho.setAge(30);

//        entityManager.delete(maria);
//        entityManager.persist(pesho);
//
//        Address softUniAddress = entityManager.findById(1, Address.class);
//        Address codexioAddress = entityManager.findById(2, Address.class);
//        Department byId1 = entityManager.findById(30, Department.class);


        entityManager.alterTable(User.class);

    }
}
