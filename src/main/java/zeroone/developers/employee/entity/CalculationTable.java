package zeroone.developers.employee.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "calculation_table")
@Schema(description = "CalculationTable entity")
public class CalculationTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @Schema(description = "The employee related to this calculation",
            example = "Employee(id=1, firstName=Nizomiddin, lastName=Mirzanazarov)")
    private Employee employee;

    @Column(nullable = false)
    @Schema(description = "The amount of calculation",
            example = "5000.0")
    private Double amount;

    @Column(nullable = false)
    @Schema(description = "The rate used for the calculation",
            example = "10.5")
    private Double rate;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    @Schema(description = "The date of the calculation",
            example = "2023-09-28")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    @Schema(description = "The organization for which the calculation is being made",
            example = "Organization(id=1, name=Zero:One Group)")
    private Organization organization;

    @Column(length = 20, nullable = false)
    @Schema(description = "The type of the calculation",
            example = "SALARY")
    private String calculationType;

    public CalculationTable() {
    }

    public CalculationTable(Long id, Employee employee, Double amount, Double rate, Date date, Organization organization, String calculationType) {
        this.id = id;
        this.employee = employee;
        this.amount = amount;
        this.rate = rate;
        this.date = date;
        this.organization = organization;
        this.calculationType = calculationType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getCalculationType() {
        return calculationType;
    }

    public void setCalculationType(String calculationType) {
        this.calculationType = calculationType;
    }

    @Override
    public String toString() {
        return "CalculationTable{" +
                "id=" + id +
                ", employee=" + employee +
                ", amount=" + amount +
                ", rate=" + rate +
                ", date=" + date +
                ", organization=" + organization +
                ", calculationType='" + calculationType + '\'' +
                '}';
    }
}
