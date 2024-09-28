package zeroone.developers.employee.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "employee")
@Schema(description = "Employee entity")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique Identifier of the Employee", example = "1")
    private Long id;

    @Schema(description = "Employee's first name", example = "Nizomiddin")
    private String firstName;

    @Schema(description = "Employee's last name", example = "Mirzanazarov")
    private String lastName;

    @Schema(description = "Employee's personal identification number (PINFL)", example = "12345678901234")
    private String pinfl;

    @Schema(description = "Date when the employee was hired", example = "2024-05-10")
    @Temporal(TemporalType.DATE)
    private Date hireDate;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    @Schema(description = "The organization where the employee works", example = "Organization(id=1, name=Zero:One Group)")
    private Organization organization;

    public Employee() {
    }

    public Employee(Long id, String firstName, String lastName, String pinfl, Date hireDate, Organization organization) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pinfl = pinfl;
        this.hireDate = hireDate;
        this.organization = organization;
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

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pinfl='" + pinfl + '\'' +
                ", hireDate=" + hireDate +
                ", organization=" + organization +
                '}';
    }
}
