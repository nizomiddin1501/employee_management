package zeroone.developers.employee.service.impl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import zeroone.developers.employee.entity.Employee;
import zeroone.developers.employee.exception.EmployeeException;
import zeroone.developers.employee.exception.ResourceNotFoundException;
import zeroone.developers.employee.payload.EmployeeDto;
import zeroone.developers.employee.repository.EmployeeRepository;
import zeroone.developers.employee.service.EmployeeService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the EmployeeService interface for managing employees.
 * This service handles CRUD operations for Employee entities.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {



    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;

    /**
     * Constructor with dependency injection for EmployeeRepository and ModelMapper.
     *
     * @param employeeRepository the repository for Employee operations
     * @param modelMapper the mapper for conversion operations dto to entity
     */
    @Autowired
    public EmployeeServiceImpl(ModelMapper modelMapper, EmployeeRepository employeeRepository) {
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Retrieve all employee records as DTOs.
     *
     * This method retrieves all employee entities from the database,
     * converts them to DTOs, and returns the list of EmployeeDto objects.
     *
     * @return a list of EmployeeDto representing all employees
     */
    @Override
    public List<EmployeeDto> findAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(this::employeeToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<EmployeeDto> getAllEmployees(int page, int size) {
        Page<Employee> productsPage = employeeRepository.findAll(PageRequest.of(page, size));
        return productsPage.map(this::employeeToDto);
    }




    /**
     * Retrieve an employee by ID.
     *
     * This method fetches the employee entity by ID, maps it to a DTO, and returns it.
     *
     * @param id the ID of the employee
     * @return an Optional containing the employee as a DTO if found
     * @throws ResourceNotFoundException if the employee is not found with the given ID
     */
    @Override
    public Optional<EmployeeDto> findEmployeeById(Long id) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
        return Optional.of(employeeToDto(employee));

    }


    /**
     * Save a new employee or update an existing one.
     *
     * This method validates that the employee's first name and last name are not null,
     * and checks if an employee with the same first name and last name already exists
     * in the database.
     *
     * @param employeeDto the DTO containing the employee information to be saved
     * @return the saved employee object
     * @throws EmployeeException if the employee data is invalid,
     *                           or if an employee with the same first name and last name already exists
     */
    @Override
    public EmployeeDto saveEmployee(@Valid EmployeeDto employeeDto) throws EmployeeException{
        // 1. Convert DTO to entity
        Employee employee = dtoToEmployee(employeeDto);

        // 2. Perform business checks on the entity
        if (employee.getFirstName() == null || employee.getLastName() == null) {
            throw new EmployeeException("Employee first name and last name must not be null");
        }

        // 3. Checking that the firstName and lastName columns do not exist
        boolean exists = employeeRepository.existsByFirstNameAndLastName(employee.getFirstName(), employee.getLastName());
        if (exists) {
            throw new EmployeeException("Employee with this first name and last name already exists");
        }

        // 4. Save Employee
        Employee savedEmployee = employeeRepository.save(employee);

        // 5. Convert the saved Employee to DTO and return
        return employeeToDto(savedEmployee);


    }


    /**
     * Update an existing employee's details.
     *
     * This method updates the employee's details based on the provided DTO.
     * It validates that the employee exists, and updates the necessary fields
     * before saving the changes to the database.
     *
     * @param id the ID of the employee to update
     * @param employeeDto the DTO containing the new employee details
     * @return the updated employee as a DTO
     * @throws ResourceNotFoundException if the employee is not found with the given ID
     * @throws EmployeeException if the employee data is invalid
     */
    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) throws EmployeeException{
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));

        // Use ModelMapper to map DTO to entity
        Employee employeeDetails = dtoToEmployee(employeeDto);

        // update employee details
        existingEmployee.setFirstName(employeeDetails.getFirstName());
        existingEmployee.setLastName(employeeDetails.getLastName());
        existingEmployee.setPinfl(employeeDetails.getPinfl());
        existingEmployee.setHireDate(employeeDetails.getHireDate());
        existingEmployee.setOrganization(employeeDetails.getOrganization());

        // Save updated employee
        Employee updatedEmployee = employeeRepository.save(existingEmployee);

        // Convert updated employee entity to DTO and return
        return employeeToDto(updatedEmployee);
    }


    /**
     * Delete an employee by their ID.
     *
     * This method looks up the employee by their ID. If the employee is found, it is deleted
     * from the database. If not, a ResourceNotFoundException is thrown.
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




    // DTO to Entity conversion
    public Employee dtoToEmployee(EmployeeDto employeeDto) {
        return modelMapper.map(employeeDto, Employee.class);
    }


    // Entity to DTO conversion
    public EmployeeDto employeeToDto(Employee employee) {
        return modelMapper.map(employee, EmployeeDto.class);
    }
}
