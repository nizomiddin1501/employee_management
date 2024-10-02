package zeroone.developers.employee.service;

import zeroone.developers.employee.entity.Employee;
import zeroone.developers.employee.exception.EmployeeException;
import zeroone.developers.employee.exception.ResourceNotFoundException;
import zeroone.developers.employee.payload.EmployeeDto;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing employees.
 * Provides methods for performing CRUD operations on employees.
 */
public interface EmployeeService {


    /**
     * Retrieve all employee records as DTOs.
     *
     * This method retrieves all employee entities from the database,
     * converts them to EmployeeDto objects, and returns the list of DTOs.
     *
     * @return a list of EmployeeDto representing all employee records
     */
    List<EmployeeDto> findAllEmployees();


    /**
     * Retrieve an employee by their ID.
     *
     * This method fetches the employee data by ID and returns it as a DTO.
     *
     * @param id the ID of the employee
     * @return an Optional containing the employee as a DTO if found, otherwise empty
     * @throws ResourceNotFoundException if the employee with the given ID does not exist
     */
    Optional<EmployeeDto> findEmployeeById(Long id) throws ResourceNotFoundException;


    /**
     * Save a new employee record.
     *
     * This method saves a new employee using the information provided in the EmployeeDto
     * and returns the saved employee as an EmployeeDto.
     *
     * @param employeeDto the DTO containing the employee information to be saved
     * @return the saved employee as a DTO
     * @throws EmployeeException if the employee data is invalid
     */
    EmployeeDto saveEmployee(EmployeeDto employeeDto) throws EmployeeException;


    /**
     * Update an existing employee record.
     *
     * This method updates the details of an employee based on the provided employee ID
     * and the new details contained in the EmployeeDto. It returns the updated employee
     * as an EmployeeDto.
     *
     * @param id the ID of the employee to be updated
     * @param employeeDto the new details for the employee
     * @return the updated employee as a DTO
     * @throws ResourceNotFoundException if the employee is not found with the given ID
     */
    EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) throws ResourceNotFoundException;


    /**
     * Delete an employee by their ID.
     *
     * This method finds the employee by their ID and removes the employee from the database.
     *
     * @param id the ID of the employee to delete
     * @throws ResourceNotFoundException if the employee is not found
     */
    void deleteEmployee(Long id) throws ResourceNotFoundException;





}
