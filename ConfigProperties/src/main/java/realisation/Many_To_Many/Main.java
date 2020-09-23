package realisation.Many_To_Many;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import realisation.HibernateUtil;
import realisation.One_To_One.Auto;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

//    Создать сущность с отношением многие ко многим: Язык программирования и Программист
//    (На языке Java может писать много программистов, каждый программист может писать на разных языках)

//    Для каждых сущностей создать объекты, связать их друг с другом. Выполнить действия: read, update, delete.
//    Не забудьте про:
//    - Eager или Lazy
//    - cascade (сделайте так, чтобы при удалении элемента из главной таблицы, удалялись его зависимые элементы.
//    При отношении Many-to-many такого быть не должно, должна удаляться только связь)
//    Также не забудьте указать mappedBy


        SessionFactory sessionFactory = HibernateUtil.createSessionFactory();
        Session session = sessionFactory.openSession();

        Scanner scn = new Scanner(System.in);

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

                Coder coder1 = new Coder("John");
                Coder coder2 = new Coder("Bill");

                List<Language> listLanguage1 = Arrays.asList(new Language("JAVA", coder1), new Language("Pascal", coder2));
                List<Language> listLanguage2 = Arrays.asList(new Language("C++", coder1), new Language("Fortran", coder2));

                coder1.getListLanguage().addAll(listLanguage1);
                coder2.getListLanguage().addAll(listLanguage2);

                session.beginTransaction();
                session.save(coder1);
                session.save(coder2);

                for(Language l : listLanguage1){
                    session.save(l);
                }

                for(Language l : listLanguage2){
                    session.save(l);
                }

                session.getTransaction().commit();

            }
            else if(input.equals("2")){
                System.out.println("2. Read.");

                List<Coder> listCoder = session.createQuery("from Coder").getResultList();
                System.out.printf("%-20s %-20s %-20s %n", "Coder_ID", "Name", "Languages");
                listCoder.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %n", x.getId(), x.getName(), x.getListLanguage()));

         }
            else if(input.equals("3")){
                System.out.println("3. Update.");

                System.out.println("Программисты, имещиеся в таблице:");
                List<Coder> listCoder = session.createQuery("from Coder").getResultList();
                System.out.printf("%-20s %-20s %-20s %n", "Coder_ID", "Name", "Languages");
                listCoder.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %n", x.getId(), x.getName(), x.getListLanguage()));

                System.out.println("Введите имя программиста, который будет изменен:");
                String oldName = scn.nextLine();

                System.out.println("Ведите новое имя программиста:");
                String newName = scn.nextLine();

                Query<Coder> query = session.createQuery("from Coder where name := coderParameter");
                query.setParameter("coderParametet", oldName);
                Coder coder = query.getSingleResult();
                coder.setName(newName);

                session.beginTransaction();

                try{
                    session.save(coder);
                    session.getTransaction().commit();
                }catch(Exception ex){
                    System.out.println(ex);
                    session.getTransaction().rollback();
                }

                System.out.println("Список программистов с изменениями:");
                listCoder = session.createQuery("from Coder").getResultList();
                System.out.printf("%-20s %-20s %-20s %n", "Coder_ID", "Name", "Languages");
                listCoder.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %n", x.getId(), x.getName(), x.getListLanguage()));

            }
            else if(input.equals("4")){
                System.out.println("4. Delete.");

                System.out.println("Программисты, имещиеся в таблице:");
                List<Coder> listCoder = session.createQuery("from Coder").getResultList();
                System.out.printf("%-20s %-20s %-20s %n", "Coder_ID", "Name", "Languages");
                listCoder.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %n", x.getId(), x.getName(), x.getListLanguage()));

                System.out.println("Введите имя программиста, который будет удален:");
                String deletedName = scn.nextLine();

                Query<Coder> query = session.createQuery("from Coder where name := coderParameter");
                query.setParameter("coderParameter", deletedName);
                Coder coder = query.getSingleResult();

                session.beginTransaction();

                try{
                    session.remove(coder);
                    session.getTransaction().commit();
                }catch(Exception ex){
                    System.out.println(ex);
                    session.getTransaction().rollback();
                }

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
