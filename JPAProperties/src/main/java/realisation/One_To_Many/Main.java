package realisation.One_To_Many;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

//    Создать сущности с отношением один ко многим: Человек и Недвижимость (у человека может быть много недвижимости)

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

                Person person1 = new Person("Ivan", "Ivanov");
                Person person2 = new Person("Petr", "Petrov");

                List<Realty> listRealties1 = Arrays.asList(new Realty("Moscow", 100, person1), new Realty("Samara", 150, person2));
                List<Realty> listRealties2 = Arrays.asList(new Realty("Rostov", 200, person1), new Realty("Irkutsk", 250, person2));

                person1.setRealtyList(listRealties1);
                person2.setRealtyList(listRealties2);

                em.getTransaction().begin();
                em.persist(person1);
                em.persist(person2);

                for(Realty r : listRealties1){
                    em.persist(r);
                }

                for(Realty r : listRealties2){
                    em.persist(r);
                }

                em.getTransaction().commit();

            }
            else if(input.equals("2")){
                System.out.println("2. Read.");

                List<Person> personList = em.createQuery("from Person").getResultList();
                System.out.printf("%-20s %-20s %-20s %-20s %n", "Person_ID", "Name", "Surname", "Realties");
                personList.stream()
                        .forEach(x-> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getName(), x.getSurname(), x.getRealtyList()));

            }
            else if(input.equals("3")){
                System.out.println("3. Update.");

                System.out.println("Люди, имещиеся в таблице:");
                List<Person> personList = em.createQuery("from Person").getResultList();
                System.out.printf("%-20s %-20s %-20s %-20s %n", "Person_ID", "Name", "Surname", "Realties");
                personList.stream()
                        .forEach(x-> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getName(), x.getSurname(), x.getRealtyList()));

                System.out.println("Введите фамилию, которая будет изменена:");
                String oldSurname = scn.nextLine();

                System.out.println("Введите новую фамилию:");
                String newSurname = scn.nextLine();

                Person changedPerson = (Person) em.find(Person.class, oldSurname);
                changedPerson.setSurname(newSurname);

                em.getTransaction().begin();

                try{
                    em.merge(changedPerson);
                    em.getTransaction().commit();
                }catch(Exception ex){
                    System.out.println(ex);
                    em.getTransaction().rollback();
                }

                System.out.println("Список людей с изменной фамилией:");
                personList = em.createQuery("from Person").getResultList();
                System.out.printf("%-20s %-20s %-20s %-20s %n", "Person_ID", "Name", "Surname", "Realties");
                personList.stream()
                        .forEach(x-> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getName(), x.getSurname(), x.getRealtyList()));

            }
            else if(input.equals("4")){
                System.out.println("4. Delete.");

                System.out.println("Люди, имещиеся в таблице:");
                List<Person> personList = em.createQuery("from Person").getResultList();
                System.out.printf("%-20s %-20s %-20s %-20s %n", "Person_ID", "Name", "Surname", "Realties");
                personList.stream()
                        .forEach(x-> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getName(), x.getSurname(), x.getRealtyList()));

                System.out.println("Введите фамилию, которая будет удалена:");
                String deletedSurname = scn.nextLine();

                Person deletedPerson = (Person) em.find(Person.class, deletedSurname);

                em.getTransaction().begin();

                try{
                    em.remove(deletedPerson);
                    em.getTransaction().commit();
                }catch(Exception ex){
                    System.out.println(ex);
                    em.getTransaction().rollback();
                }

                System.out.println("Люди, в таблице после удаления:");
                personList = em.createQuery("from Person").getResultList();
                System.out.printf("%-20s %-20s %-20s %-20s %n", "Person_ID", "Name", "Surname", "Realties");
                personList.stream()
                        .forEach(x-> System.out.printf("%-20s %-20s %-20s %-20s %n", x.getId(), x.getName(), x.getSurname(), x.getRealtyList()));

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
