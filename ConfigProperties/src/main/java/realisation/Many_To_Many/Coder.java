package realisation.Many_To_Many;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Coder {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = @JoinColumn (name = "coder_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    List<Language> listLanguage = new ArrayList<>();

    public Coder() {
    }

    public Coder(String name) {
        this.name = name;
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

    public List<Language> getListLanguage() {
        return listLanguage;
    }

    public void setListLanguage(List<Language> listLanguage) {
        this.listLanguage = listLanguage;
    }

    @Override
    public String toString() {
        return "Coder{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", listLanguage=" + listLanguage +
                '}';
    }
}
