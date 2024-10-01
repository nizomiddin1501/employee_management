package zeroone.developers.employee.payload;

import java.sql.Date;

public class EmployeeDto {

    private Long id;
    private String firstName;
    private String lastName;
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
