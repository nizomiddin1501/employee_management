package zeroone.developers.employee.service;
import zeroone.developers.employee.entity.CalculationTable;


import java.util.List;
import java.util.Optional;

public interface CalculationTableService {

    List<CalculationTable> findAllCalculations();
    Optional<CalculationTable> findCalculationById(Long id);
    CalculationTable saveCalculation(CalculationTable calculationTable);
    CalculationTable updateCalculationTable(Long id, CalculationTable calculationTableDetails);
    void deleteCalculation(Long id);


    //jpa native query methods
    List<Object[]> getEmployeesWithHigherSalary(int month, double threshold);
    List<Object[]> getEmployeesByRegion(int month);
    List<Object[]> getAverageSalaryByOrganization(int month, Long organizationId);
    List<Object[]> getEmployeesWithSalariesAndVacations(int month);









}
