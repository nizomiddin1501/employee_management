package zeroone.developers.employee.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class OrganizationDto {


    private Long id;

    @NotEmpty(message = "Name is required")
    @Size(min = 2, message = "Name must be at least 2 characters")
    private String name;
    private RegionDto region;
    private OrganizationDto parent;


    public OrganizationDto(Long id, String name, RegionDto region, OrganizationDto parent) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.parent = parent;
    }

    public OrganizationDto() {
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

    public RegionDto getRegion() {
        return region;
    }

    public void setRegion(RegionDto region) {
        this.region = region;
    }

    public OrganizationDto getParent() {
        return parent;
    }

    public void setParent(OrganizationDto parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "OrganizationDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", region=" + region +
                ", parent=" + parent +
                '}';
    }
}
