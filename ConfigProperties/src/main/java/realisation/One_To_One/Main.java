package realisation.One_To_One;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import realisation.HibernateUtil;

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

        SessionFactory sessionFactory = HibernateUtil.createSessionFactory();
        Session session = sessionFactory.openSession();

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

                auto1.setSteeringWheel(steeringWheel1);
                auto2.setSteeringWheel(steeringWheel2);

                steeringWheel1.setAuto(auto1);
                steeringWheel2.setAuto(auto2);

                session.beginTransaction();
                session.save(auto1);
                session.save(auto2);
                session.save(steeringWheel1);
                session.save(steeringWheel2);
                session.getTransaction().commit();

            }
            else if(input.equals("2")){
                System.out.println("2. Read.");

                List<Auto> listAuto = session.createQuery("from Auto").getResultList();
                listAuto.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %-20s %n", x.getId(), x.getAutomaker(), x.getModel(), x.getSteeringWheel()));

            }
            else if(input.equals("3")){
                System.out.println("3. Update.");

                System.out.println("Автомобили, имеющиеся в таблице:");
                List<Auto> listAuto = session.createQuery("from Auto").getResultList();
                listAuto.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %-20s %n", x.getId(), x.getAutomaker(), x.getModel(), x.getSteeringWheel()));

                System.out.println("Введите название модели, которая будет изменена:");
                String oldModel = scn.nextLine();

                System.out.println("Введите название новой модели:");
                String newModel = scn.nextLine();

                Query<Auto> query = session.createQuery("from Auto where model :=autoParameter");
                query.setParameter("autoParameter", oldModel);

                Auto auto = query.getSingleResult();
                auto.setModel(newModel);

                session.beginTransaction();

                try{
                    session.save(auto);
                    session.getTransaction().commit();
                }
                catch(Exception ex){
                    System.out.println(ex);
                    session.getTransaction().rollback();
                }

                System.out.println("Список с измененной моделью автомобиля:");
                listAuto = session.createQuery("from Auto").getResultList();
                listAuto.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %-20s %n", x.getId(), x.getAutomaker(), x.getModel(), x.getSteeringWheel()));

            }
            else if(input.equals("4")){
                System.out.println("4. Delete.");

                System.out.println("Автомобили, имещиеся в базе:");
                List<Auto> listAuto = session.createQuery("from Auto").getResultList();
                listAuto.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %-20s %-20s %n", x.getId(), x.getAutomaker(), x.getModel(), x.getSteeringWheel()));

                System.out.println("Введите название модели, которая будет удалена:");
                String deletedModel = scn.nextLine();

                Query<Auto> query = session.createQuery("from Auto where model :=autoParameter");
                query.setParameter("autoParameter", deletedModel);

                Auto auto = query.getSingleResult();
                auto.setModel(deletedModel);

                session.beginTransaction();

                try{
                    session.remove(auto);
                    session.getTransaction().commit();
                }
                catch(Exception ex){
                    System.out.println(ex);
                    session.getTransaction().rollback();
                }

                System.out.println("Список после удаления автомобиля:");
                listAuto = session.createQuery("from Auto").getResultList();
                listAuto.stream()
                        .forEach(x-> System.out.printf("%-20s %-20s %-20s %-20s %-20s %n", x.getId(), x.getAutomaker(), x.getModel(), x.getSteeringWheel()));

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
