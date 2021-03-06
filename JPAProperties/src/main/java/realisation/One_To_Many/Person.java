package realisation.One_To_Many;

import javax.persistence.*;
import java.util.List;

@Entity
public class Person {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String surname;

    @OneToMany (mappedBy = "person", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Realty> realtyList;

    public Person() {
    }

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Realty> getRealtyList() {
        return realtyList;
    }

    public void setRealtyList(List<Realty> realtyList) {
        this.realtyList = realtyList;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", realtyList=" + realtyList +
                '}';
    }
}
