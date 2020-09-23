package realisation.One_To_One;

import javax.persistence.*;

@Entity
public class Auto {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private String automaker;
    private String model;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "steeringWheel_id")
    SteeringWheel steeringWheel;

    public Auto() {
    }

    public Auto(String automaker, String model) {
        this.automaker = automaker;
        this.model = model;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAutomaker() {
        return automaker;
    }

    public void setAutomaker(String automaker) {
        this.automaker = automaker;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public SteeringWheel getSteeringWheel() {
        return steeringWheel;
    }

    public void setSteeringWheel(SteeringWheel steeringWheel) {
        this.steeringWheel = steeringWheel;
    }

    @Override
    public String toString() {
        return "Auto{" +
                "id=" + id +
                ", automaker='" + automaker + '\'' +
                ", model='" + model + '\'' +
                ", steeringWheel=" + steeringWheel +
                '}';
    }
}
