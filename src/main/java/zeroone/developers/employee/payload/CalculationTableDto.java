package zeroone.developers.employee.payload;

import java.sql.Date;

public class CalculationTableDto {


    private Long id;
    private EmployeeDto employee;
    private Double amount;
    private Double rate;
    private Date date;
    private OrganizationDto organization;
    private String calculationType;


    public CalculationTableDto(Long id, EmployeeDto employee, Double amount, Double rate, Date date, OrganizationDto organization, String calculationType) {
        this.id = id;
        this.employee = employee;
        this.amount = amount;
        this.rate = rate;
        this.date = date;
        this.organization = organization;
        this.calculationType = calculationType;
    }


    public CalculationTableDto() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeDto getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDto employee) {
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

    public OrganizationDto getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDto organization) {
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
        return "CalculationTableDto{" +
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
