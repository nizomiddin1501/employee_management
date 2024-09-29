package zeroone.developers.employee.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zeroone.developers.employee.entity.Employee;
import zeroone.developers.employee.exception.EmployeeException;
import zeroone.developers.employee.exception.ResourceNotFoundException;
import zeroone.developers.employee.repository.EmployeeRepository;
import zeroone.developers.employee.service.EmployeeService;

import java.util.List;
import java.util.Optional;
/**
 * Implementation of the EmployeeService interface for managing employees.
 * This service handles CRUD operations for Employee entities.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    /**
     * Constructor with dependency injection for EmployeeRepository.
     *
     * @param employeeRepository the repository for Employee operations
     */
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    /**
     * Retrieve all employees.
     *
     * @return a list of all employees
     */
    @Override
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }


    /**
     * Retrieve an employee by ID.
     *
     * @param id the ID of the employee
     * @return an Optional containing the employee if found
     * @throws ResourceNotFoundException if the employee is not found with the given ID
     */
    @Override
    public Optional<Employee> findEmployeeById(Long id) {
        return Optional.ofNullable(employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id)));
    }


    /**
     * Save a new employee or update an existing one.
     *
     * @param employee the employee to save
     * @return the saved employee
     * @throws EmployeeException if the employee data is invalid
     */
    @Override
    public Employee saveEmployee(Employee employee) throws EmployeeException{
        if (employee.getFirstName() == null || employee.getLastName() == null) {
            throw new EmployeeException("Employee first name and last name must not be null");
        }
        return employeeRepository.save(employee);
    }


    /**
     * Update an existing employee's details.
     *
     * @param id the ID of the employee to update
     * @param employeeDetails the new employee details
     * @return the updated employee
     * @throws ResourceNotFoundException if the employee is not found with the given ID
     * @throws EmployeeException if the employee data is invalid
     */
    @Override
    public Employee updateEmployee(Long id, Employee employeeDetails) throws EmployeeException{
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));

        // update employee details
        existingEmployee.setFirstName(employeeDetails.getFirstName());
        existingEmployee.setLastName(employeeDetails.getLastName());
        existingEmployee.setPinfl(employeeDetails.getPinfl());
        existingEmployee.setHireDate(employeeDetails.getHireDate());
        existingEmployee.setOrganization(employeeDetails.getOrganization());

        return employeeRepository.save(existingEmployee);
    }


    /**
     * Delete an employee by ID.
     *
     * @param id the ID of the employee to delete
     * @throws ResourceNotFoundException if the employee is not found with the given ID
     */
    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));

        employeeRepository.delete(employee);
    }
}
