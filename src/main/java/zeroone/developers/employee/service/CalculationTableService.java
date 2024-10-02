package zeroone.developers.employee.service;
import zeroone.developers.employee.entity.CalculationTable;
import zeroone.developers.employee.exception.CalculationTableException;
import zeroone.developers.employee.exception.EmployeeException;
import zeroone.developers.employee.exception.ResourceNotFoundException;
import zeroone.developers.employee.payload.CalculationTableDto;


import java.util.List;
import java.util.Optional;
/**
 * Service interface for managing calculation records.
 * Provides methods for performing CRUD operations on calculations and reports methods.
 */
public interface CalculationTableService {


    /**
     * Retrieve all calculationTable records as DTOs.
     *
     * This method retrieves all calculationTable entities from the database,
     * converts them to CalculationTableDto objects, and returns the list of DTOs.
     *
     * @return a list of CalculationTableDto representing all calculationTable records
     */
    List<CalculationTableDto> findAllCalculations();


    /**
     * Retrieve an calculationTable by their ID.
     *
     * This method fetches the calculationTable data by ID and returns it as a DTO.
     *
     * @param id the ID of the calculationTable
     * @return an Optional containing the calculationTable as a DTO if found, otherwise empty
     * @throws ResourceNotFoundException if the calculationTable with the given ID does not exist
     */
    Optional<CalculationTableDto> findCalculationById(Long id) throws ResourceNotFoundException;


    /**
     * Save a new calculationTable record.
     *
     * This method saves a new calculationTable using the information provided in the CalculationTableDto
     * and returns the saved calculationTable as an CalculationTableDto.
     *
     * @param calculationTableDto the DTO containing the calculationTable information to be saved
     * @return the saved calculationTable as a DTO
     * @throws EmployeeException if the calculationTable data is invalid
     */
    CalculationTableDto saveCalculation(CalculationTableDto calculationTableDto) throws CalculationTableException;


    /**
     * Update an existing calculationTable record.
     *
     * This method updates the details of an calculationTable based on the provided calculationTable ID
     * and the new details contained in the CalculationTableDto. It returns the updated calculationTable
     * as an CalculationTableDto.
     *
     * @param id the ID of the calculationTable to be updated
     * @param calculationTableDto the new details for the employee
     * @return the updated calculationTable as a DTO
     * @throws ResourceNotFoundException if the calculationTable is not found with the given ID
     */
    CalculationTableDto updateCalculationTable(Long id, CalculationTableDto calculationTableDto) throws ResourceNotFoundException;




    /**
     * Delete an calculationTable by their ID.
     *
     * This method finds the calculationTable by their ID and removes the calculationTable from the database.
     *
     * @param id the ID of the calculationTable to delete
     * @throws ResourceNotFoundException if the calculationTable is not found
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
