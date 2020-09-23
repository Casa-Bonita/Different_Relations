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

    @ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Coder> coderList = new ArrayList<>();

    public Language() {
    }

    public Language (String languageName, Coder...coders){
        this.languageName = languageName;
        for (int i = 0; i < coders.length; i++) {
            coderList.add(coders[i]);
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

    public List<Coder> getCoderList() {
        return coderList;
    }

    public void setCoderList(List<Coder> coderList) {
        this.coderList = coderList;
    }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", languageName='" + languageName + '\'' +
                ", coderList=" + coderList +
                '}';
    }
}
