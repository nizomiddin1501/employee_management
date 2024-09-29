package zeroone.developers.employee.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "region")
@Schema(description = "Region entity")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique Identifier of the Region", example = "1")
    private Long id;

    @Column(nullable = false, length = 20)
    @Schema(description = "Name of the region", example = "Tashkent")
    private String name;



    public Region(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Region() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
