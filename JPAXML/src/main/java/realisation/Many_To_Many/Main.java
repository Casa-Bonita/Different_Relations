package realisation.Many_To_Many;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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

        Scanner scn = new Scanner(System.in);

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_XML_Unit");
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

                Coder coder1 = new Coder("Ivan");
                Coder coder2 = new Coder("Petr");

                List<Language> languages1 = Arrays.asList(new Language("JAVA", coder1), new Language("Pascal", coder1));
                List<Language> languages2 = Arrays.asList(new Language("C++", coder2), new Language("Phyton", coder2));

                coder1.getListLanguage().addAll(languages1);
                coder2.getListLanguage().addAll(languages2);

                em.getTransaction().begin();

                em.persist(coder1);
                em.persist(coder2);

                for(Language l : languages1){
                    em.persist(l);
                }

                for(Language l : languages2){
                    em.persist(l);
                }

                em.getTransaction().commit();

            }
            else if(input.equals("2")){
                System.out.println("2. Read.");

                List<Coder> listCoder = em.createQuery("from Coder").getResultList();
                System.out.printf("%-20s %-20s %-20s %n", "Coder_ID", "Name", "Languages");
                listCoder.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %n", x.getId(), x.getName(), x.getListLanguage()));

            }
            else if(input.equals("3")){
                System.out.println("3. Update.");

                System.out.println("Программисты, имещиеся в таблице:");
                List<Coder> listCoder = em.createQuery("from Coder").getResultList();
                System.out.printf("%-20s %-20s %-20s %n", "Coder_ID", "Name", "Languages");
                listCoder.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %n", x.getId(), x.getName(), x.getListLanguage()));

                System.out.println("Введите имя программиста, который будет изменен:");
                String oldName = scn.nextLine();

                System.out.println("Ведите новое имя программиста:");
                String newName = scn.nextLine();

                Coder changedCoder = (Coder) em.find(Coder.class, oldName);
                changedCoder.setName(newName);

                em.getTransaction().begin();

                try{
                    em.merge(changedCoder);
                    em.getTransaction().commit();
                }catch(Exception ex){
                    System.out.println(ex);
                    em.getTransaction().rollback();
                }

                System.out.println("Список программистов с изменениями:");
                listCoder = em.createQuery("from Coder").getResultList();
                System.out.printf("%-20s %-20s %-20s %n", "Coder_ID", "Name", "Languages");
                listCoder.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %n", x.getId(), x.getName(), x.getListLanguage()));

            }
            else if(input.equals("4")){
                System.out.println("4. Delete.");

                System.out.println("Программисты, имещиеся в таблице:");
                List<Coder> listCoder = em.createQuery("from Coder").getResultList();
                System.out.printf("%-20s %-20s %-20s %n", "Coder_ID", "Name", "Languages");
                listCoder.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %n", x.getId(), x.getName(), x.getListLanguage()));

                System.out.println("Введите имя программиста, который будет удален:");
                String deletedName = scn.nextLine();

                Coder coder = (Coder) em.find(Coder.class, deletedName);

                em.getTransaction().begin();

                try{
                    em.remove(coder);
                    em.getTransaction().commit();
                }catch(Exception ex){
                    System.out.println(ex);
                    em.getTransaction().rollback();
                }


                System.out.println("Список после удаления программиста:");
                listCoder = em.createQuery("from Coder").getResultList();
                System.out.printf("%-20s %-20s %-20s %n", "Coder_ID", "Name", "Languages");
                listCoder.stream()
                        .forEach(x -> System.out.printf("%-20s %-20s %-20s %n", x.getId(), x.getName(), x.getListLanguage()));

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
