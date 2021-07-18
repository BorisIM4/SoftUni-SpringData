import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {

        //Тук създаваме ентити мениджър който води към persistence.xml файла.
        //С този ентити мениджър създаваме DDL
        EntityManagerFactory emf = Persistence
                //тук името (softuni) трябва да е същото като persistence unit name в persistence.xml файла.
                .createEntityManagerFactory("softuni");

        EntityManager entityManager = emf.createEntityManager();

        Engine engine = new Engine(entityManager);
        engine.run();
    }
}
