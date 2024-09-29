package zeroone.developers.employee.service;

import zeroone.developers.employee.entity.Employee;
import zeroone.developers.employee.exception.EmployeeException;
import zeroone.developers.employee.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing employees.
 * Provides methods for performing CRUD operations on employees.
 */
public interface EmployeeService {


    /**
     * Retrieve employee records.
     *
     * @return a list of all employee records
     */
    List<Employee> findAllEmployees();


    /**
     * Retrieve an employee by their ID.
     *
     * @param id the ID of the employee
     * @return an Optional containing the employee if found, otherwise empty
     * @throws ResourceNotFoundException if the employee with the given ID does not exist
     */
    Optional<Employee> findEmployeeById(Long id) throws ResourceNotFoundException;


    /**
     * Save a new employee record.
     *
     * @param employee the employee object to be saved
     * @return the saved employee object
     * @throws EmployeeException if the employee is invalid
     */
    Employee saveEmployee(Employee employee) throws EmployeeException;


    /**
     * Update an existing employee record.
     *
     * @param id the ID of the employee to be updated
     * @param employeeDetails the new details for the employee
     * @return the updated employee object
     * @throws ResourceNotFoundException if the employee is not found
     */
    Employee updateEmployee(Long id, Employee employeeDetails) throws ResourceNotFoundException;


    /**
     * Delete an employee by their ID.
     *
     * @param id the ID of the employee to delete
     * @throws ResourceNotFoundException if the employee is not found
     */
    void deleteEmployee(Long id) throws ResourceNotFoundException;





}
