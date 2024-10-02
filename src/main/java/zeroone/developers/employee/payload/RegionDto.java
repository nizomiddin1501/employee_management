package zeroone.developers.employee.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class RegionDto {


    private Long id;

    @NotEmpty(message = "Name is required")
    @Size(min = 2, message = "Name must be at least 2 characters")
    private String name;


    public RegionDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public RegionDto() {
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
        return "RegionDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
