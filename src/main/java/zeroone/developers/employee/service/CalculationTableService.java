package zeroone.developers.employee.service;
import zeroone.developers.employee.entity.CalculationTable;
import zeroone.developers.employee.exception.CalculationTableException;
import zeroone.developers.employee.exception.ResourceNotFoundException;


import java.util.List;
import java.util.Optional;
/**
 * Service interface for managing calculation records.
 * Provides methods for performing CRUD operations on calculations and reports methods.
 */
public interface CalculationTableService {


    /**
     * Get all calculation records.
     *
     * @return a list of all calculation records
     */
    List<CalculationTable> findAllCalculations();


    /**
     * Find a calculation by its ID.
     *
     * @param id the ID of the calculation
     * @return an Optional containing the found calculation or empty if not found
     * @throws ResourceNotFoundException if no calculation is found with the provided ID
     */
    Optional<CalculationTable> findCalculationById(Long id) throws ResourceNotFoundException;


    /**
     * Save a new calculation record.
     *
     * @param calculationDetails the calculation record to be saved
     * @return the saved calculation record
     * @throws CalculationTableException if the calculation data is invalid
     */
    CalculationTable saveCalculation(CalculationTable calculationDetails) throws CalculationTableException;


    /**
     * Update an existing calculation record.
     *
     * @param id the ID of the calculation to be updated
     * @param calculationTableDetails the new details for the calculation
     * @return the updated calculation record
     * @throws ResourceNotFoundException if no calculation is found with the provided ID
     */
    CalculationTable updateCalculationTable(Long id, CalculationTable calculationTableDetails) throws ResourceNotFoundException;




    /**
     * Delete a calculation record by its ID.
     *
     * @param id the ID of the calculation to be deleted
     * @throws ResourceNotFoundException if the calculation record is not found
     */
    void deleteCalculation(Long id) throws ResourceNotFoundException;


    //jpa native query methods


    /**
     * Get a list of employees with a salary higher than the given threshold for the specified month.
     *
     * @param month the month for which to get the employees
     * @param threshold the salary threshold
     * @return a list of employees with a salary higher than the threshold
     */
    List<Object[]> getEmployeesWithHigherSalary(int month, double threshold);


    /**
     * Get a list of employees working in different regions for a specific month.
     *
     * @param month the month for which to get the employees
     * @return a list of employees working in various regions
     */
    List<Object[]> getEmployeesByRegion(int month);


    /**
     * Get the average salary of all employees in a specific organization for the given month.
     *
     * @param month the month for which to get the average salary
     * @param organizationId the ID of the organization
     * @return a list containing the average salary information
     */
    List<Object[]> getAverageSalaryByOrganization(int month, Long organizationId);


    /**
     * Get the details of employees who received salaries and took vacations during the specified month.
     *
     * @param month the month for which to get the data
     * @return a list containing the details of employees with salaries and vacations
     */
    List<Object[]> getEmployeesWithSalariesAndVacations(int month);









}
