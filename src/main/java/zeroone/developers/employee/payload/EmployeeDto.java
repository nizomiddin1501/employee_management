package zeroone.developers.employee.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.sql.Date;

public class EmployeeDto {

    private Long id;

    @NotEmpty(message = "First name is required")
    @Size(min = 2, message = "First name must be at least 2 characters")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    @Size(min = 2, message = "Last name must be at least 2 characters")
    private String lastName;

    @NotEmpty(message = "Pinfl is required")
    @Size(min = 2, message = "Employee's pinfl must be at least 2 characters")
    private String pinfl;
    private Date hireDate;
    private OrganizationDto organization;


    public EmployeeDto(Long id, String firstName, String lastName, String pinfl, Date hireDate, OrganizationDto organization) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pinfl = pinfl;
        this.hireDate = hireDate;
        this.organization = organization;
    }

    public EmployeeDto() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPinfl() {
        return pinfl;
    }

    public void setPinfl(String pinfl) {
        this.pinfl = pinfl;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public OrganizationDto getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDto organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pinfl='" + pinfl + '\'' +
                ", hireDate=" + hireDate +
                ", organization=" + organization +
                '}';
    }
}
