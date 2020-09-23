package realisation.One_To_Many;

import javax.persistence.*;

@Entity
public class Realty {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int realty_id;

    private String address;
    private int square;

    @ManyToOne
    @JoinColumn (name = "person_id")
    private Person person;

    public Realty() {
    }

    public Realty(String address, int square, Person person) {
        this.address = address;
        this.square = square;
        this.person = person;
    }

    public int getRealty_id() {
        return realty_id;
    }

    public void setRealty_id(int realty_id) {
        this.realty_id = realty_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSquare() {
        return square;
    }

    public void setSquare(int square) {
        this.square = square;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Realty{" +
                "realty_id=" + realty_id +
                ", address='" + address + '\'' +
                ", square=" + square +
                ", person=" + person +
                '}';
    }
}
