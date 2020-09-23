package realisation.One_To_One;

import javax.persistence.*;

@Entity
public class SteeringWheel {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private String partNumber;
    private String material;
    private String color;

    @OneToOne(mappedBy = "steeringWheel")
    private Auto auto;

    public SteeringWheel() {
    }

    public SteeringWheel(String partNumber, String material, String color) {
        this.partNumber = partNumber;
        this.material = material;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    @Override
    public String toString() {
        return "SteeringWheel{" +
                "id=" + id +
                ", partNumber='" + partNumber + '\'' +
                ", material='" + material + '\'' +
                ", color='" + color + '\'' +
                ", auto=" + auto +
                '}';
    }
}
