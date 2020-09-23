package realisation.Many_To_Many;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Language {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private String languageName;

    @ManyToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Coder> listCoder = new ArrayList<>();

    public Language() {
    }

    public Language(String languageName, Coder...coders) {
        this.languageName = languageName;
        for (int i = 0; i < coders.length; i++) {
            listCoder.add(coders[i]);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public List<Coder> getListCoder() {
        return listCoder;
    }

    public void setListCoder(List<Coder> listCoder) {
        this.listCoder = listCoder;
    }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", languageName='" + languageName + '\'' +
                ", listCoder=" + listCoder +
                '}';
    }
}
