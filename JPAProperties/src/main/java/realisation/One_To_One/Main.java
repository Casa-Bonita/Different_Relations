package realisation.One_To_One;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

//    Создать сущности с отношением один к одному: Автомобиль и Руль

//    Для каждых сущностей создать объекты, связать их друг с другом. Выполнить действия: read, update, delete.
//    Не забудьте про:
//    - Eager или Lazy
//    - cascade (сделайте так, чтобы при удалении элемента из главной таблицы, удалялись его зависимые элементы.
//    При отношении Many-to-many такого быть не должно, должна удаляться только связь)
//    Также не забудьте указать mappedBy

        Scanner scn = new Scanner(System.in);

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_Properties_Unit");
        EntityManager em = entityManagerFactory.createEntityManager();

        String input = "";
        while(!input.equals("5")){
            System.out.println("1. Create.");
            System.out.println("2. Read.");
            System.out.println("3. Update.");
            System.out.println("4. Delete.");
            System.out.println("5. Exit.");

            input = scn.nextLine();
            if(input.equals("1")){
                System.out.println("1. Create.");

                Auto auto1 = new Auto("Toyota", "Camry");
                Auto auto2 = new Auto("Nissan", "X-Trail");

                SteeringWheel steeringWheel1 = new SteeringWheel("X123", "leather", "black");
                SteeringWheel steeringWheel2 = new SteeringWheel("Y456", "plastic", "green");

                em.getTransaction().begin();
                em.persist(auto1);
                em.persist(auto2);
                em.persist(steeringWheel1);
                em.persist(steeringWheel2);
                em.getTransaction().commit();

            }
            else if(input.equals("2")){
                System.out.println("2. Read.");

                List<Auto> autoList = em.createQuery("from Auto").getResultList();
                autoList.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getModel(), x.getAutomaker(), x.getSteeringWheel()));

            }
            else if(input.equals("3")){
                System.out.println("3. Update.");

                System.out.println("Автомобили, имещиеся в таблице:");
                List<Auto> autoList = em.createQuery("from Auto").getResultList();
                autoList.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getModel(), x.getAutomaker(), x.getSteeringWheel()));

                System.out.println("Введите название модели, которая будет изменена:");
                String oldModel = scn.nextLine();

                System.out.println("Введите название новой модели:");
                String newModel = scn.nextLine();

                Auto changedAuto = (Auto) em.find(Auto.class, oldModel);
                changedAuto.setModel(newModel);

                em.getTransaction().begin();

                try{
                    em.merge(changedAuto);
                    em.getTransaction().commit();
                }catch(Exception ex){
                    System.out.println(ex);
                    em.getTransaction().rollback();
                }

                System.out.println("Автомобили после изменений:");
                autoList = em.createQuery("from Auto").getResultList();
                autoList.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getModel(), x.getAutomaker(), x.getSteeringWheel()));

            }
            else if(input.equals("4")){
                System.out.println("4. Delete.");

                System.out.println("Автомобили, имещиеся в таблице:");
                List<Auto> autoList = em.createQuery("from Auto").getResultList();
                autoList.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getModel(), x.getAutomaker(), x.getSteeringWheel()));

                System.out.println("Введите название модели, которая будет удалена:");
                String deletedModel = scn.nextLine();

                Auto deletedAuto = (Auto) em.find(Auto.class, deletedModel);

                em.getTransaction().begin();

                try{
                    em.remove(deletedAuto);
                    em.getTransaction().commit();
                }catch(Exception ex){
                    System.out.println(ex);
                    em.getTransaction().rollback();
                }

                System.out.println("Автомобили после удаления:");
                autoList = em.createQuery("from Auto").getResultList();
                autoList.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getModel(), x.getAutomaker(), x.getSteeringWheel()));

            }
            else if(input.equals("5")){
                System.out.println("5. Exit.");

            }
            else{
                System.out.println("Invalid input.");

            }
        }
        

    }
}
