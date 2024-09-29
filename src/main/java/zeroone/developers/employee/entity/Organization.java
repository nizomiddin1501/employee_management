package zeroone.developers.employee.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "organization")
@Schema(description = "Organization entity")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    @Schema(description = "Name of the organization", example = "Zero:One Group")
    private String name;

    @ManyToOne
    @JoinColumn(name = "region_id")
    @Schema(description = "Region where the organization is located", example = "Region(id=1, name='Tashkent')")
    private Region region;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @Schema(description = "Parent organization if any, otherwise null", example = "null or Organization(id=2, name='Zero:One Education')")
    private Organization parent;

    public Organization() {
    }

    public Organization(Long id, String name, Region region, Organization parent) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.parent = parent;
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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Organization getParent() {
        return parent;
    }

    public void setParent(Organization parent) {
        this.parent = parent;
    }


    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", region=" + region +
                ", parent=" + parent +
                '}';
    }
}
