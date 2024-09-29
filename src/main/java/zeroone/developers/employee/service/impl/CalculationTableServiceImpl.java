package zeroone.developers.employee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zeroone.developers.employee.entity.CalculationTable;
import zeroone.developers.employee.exception.CalculationTableException;
import zeroone.developers.employee.exception.ResourceNotFoundException;
import zeroone.developers.employee.repository.CalculationTableRepository;
import zeroone.developers.employee.service.CalculationTableService;

import java.util.List;
import java.util.Optional;
/**
 * Implementation of the CalculationTableService interface.
 * Provides CRUD operations and custom queries for CalculationTable entities.
 */
@Service
public class CalculationTableServiceImpl implements CalculationTableService {

    private final CalculationTableRepository calculationTableRepository;

    /**
     * Constructor with dependency injection for CalculationTableRepository.
     *
     * @param calculationTableRepository the repository for CalculationTable operations
     */
    @Autowired
    public CalculationTableServiceImpl(CalculationTableRepository calculationTableRepository) {
        this.calculationTableRepository = calculationTableRepository;
    }


    //jpa native query methods

    /**
     * Retrieves a list of employees with salary higher than a given threshold for a specific month.
     *
     * @param month      the month for which the data is being retrieved
     * @param threshold  the salary threshold to filter employees
     * @return a list of employees with higher salary in the form of Object arrays
     */
    @Override
    public List<Object[]> getEmployeesWithHigherSalary(int month, double threshold) {
        return calculationTableRepository.findEmployeesWithHigherSalary(month, threshold);
    }


    /**
     * Retrieves the number of organizations and total salary for employees with the same PINFL
     * who worked in different regions for a specific month.
     *
     * @param month the month for which the data is being retrieved
     * @return a list of employees' data by region in the form of Object arrays
     */
    @Override
    public List<Object[]> getEmployeesByRegion(int month) {
        return calculationTableRepository.findEmployeesByRegion(month);
    }


    /**
     * Retrieves the average salary for all organizations related to a given organization in a specific month.
     *
     * @param month          the month for which the data is being retrieved
     * @param organizationId the ID of the organization for which the data is being retrieved
     * @return a list of average salaries by organization in the form of Object arrays
     */
    @Override
    public List<Object[]> getAverageSalaryByOrganization(int month, Long organizationId) {
        return calculationTableRepository.findAverageSalaryByOrganization(month, organizationId);
    }


    /**
     * Retrieves information about employees who received salaries and took vacations in a specific month.
     *
     * @param month the month for which the data is being retrieved
     * @return a list of employees' data with salaries and vacations in the form of Object arrays
     */
    @Override
    public List<Object[]> getEmployeesWithSalariesAndVacations(int month) {
        return calculationTableRepository.findEmployeesWithSalariesAndVacations(month);
    }


    //crud methods

    /**
     * Retrieve all calculation records.
     *
     * @return a list of all calculation records
     */
    @Override
    public List<CalculationTable> findAllCalculations() {
        return calculationTableRepository.findAll();
    }


    /**
     * Retrieves a calculation record by their ID.
     *
     * @param id the ID of the CalculationTable
     * @return an Optional containing the found CalculationTable, or throws ResourceNotFoundException if not found
     * @throws ResourceNotFoundException if the CalculationTable with the specified ID is not found
     */
    @Override
    public Optional<CalculationTable> findCalculationById(Long id) {
        return Optional.ofNullable(calculationTableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Calculation not found with id " + id)));
    }


    /**
     * Save a new calculation record.
     *
     * @param calculation the calculation record to save
     * @return the saved calculation record
     * @throws CalculationTableException if the calculation data is invalid
     */
    @Override
    public CalculationTable saveCalculation(CalculationTable calculation) throws CalculationTableException{
        if (calculation.getAmount() <= 0) {
            throw new CalculationTableException("Calculation field must be positive");
        }
        return calculationTableRepository.save(calculation);
    }


    /**
     * Updates an existing CalculationTable entity by their ID.
     *
     * @param id                    the ID of the CalculationTable to be updated
     * @param calculationTableDetails the details to update the CalculationTable entity
     * @return the updated CalculationTable entity
     * @throws ResourceNotFoundException if the CalculationTable with the specified ID is not found
     * @throws CalculationTableException if the calculation data is invalid
     */
    @Override
    public CalculationTable updateCalculationTable(Long id, CalculationTable calculationTableDetails) throws CalculationTableException{
        CalculationTable calculationTable = calculationTableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CalculationTable not found with id: " + id));

        // Updating the fields
        calculationTable.setAmount(calculationTableDetails.getAmount());
        calculationTable.setRate(calculationTableDetails.getRate());
        calculationTable.setDate(calculationTableDetails.getDate());
        calculationTable.setEmployee(calculationTableDetails.getEmployee());
        calculationTable.setOrganization(calculationTableDetails.getOrganization());
        calculationTable.setCalculationType(calculationTableDetails.getCalculationType());

        return calculationTableRepository.save(calculationTable);
    }


    /**
     * Deletes a CalculationTable entity by their ID.
     *
     * @param id the ID of the CalculationTable to be deleted
     * @throws ResourceNotFoundException if the CalculationTable with the specified ID is not found
     */
    @Override
    public void deleteCalculation(Long id) {
        CalculationTable calculation = calculationTableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Calculation not found with id " + id));
        calculationTableRepository.deleteById(id);
    }


}
