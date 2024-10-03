package zeroone.developers.employee.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zeroone.developers.employee.payload.CustomApiResponse;
import zeroone.developers.employee.payload.EmployeeDto;
import zeroone.developers.employee.service.EmployeeService;

import java.util.List;
import java.util.Optional;

/**
 * Controller for handling requests related to Employee operations.
 * This controller provides RESTful endpoints to manage employee records,
 * including creating, updating, retrieving, and deleting employee information.
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {


    private final EmployeeService employeeService;

    /**
     * Constructor for EmployeeController.
     *
     * @param employeeService the service to manage employee records
     * @Autowired automatically injects the EmployeeService bean
     */
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    /**
     * Retrieve a list of all employees.
     *
     * This method fetches all employee records and returns them as a list of EmployeeDto.
     *
     * @return a ResponseEntity containing a list of EmployeeDto representing all employees
     */
    @Operation(summary = "Get all Employees", description = "Retrieve a list of all employees.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of employees.")
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employeeDtos = employeeService.findAllEmployees();
        return new ResponseEntity<>(employeeDtos, HttpStatus.OK);
    }


    /**
     * Retrieve an employee by their unique ID using the provided EmployeeDto.
     *
     * This method retrieves an employee's details based on their ID and returns
     * the corresponding EmployeeDto if found. If the employee does not exist,
     * it returns a 404 Not Found status.
     *
     * @param id the ID of the employee to retrieve
     * @return a ResponseEntity containing the EmployeeDto and an HTTP status of OK,
     *         or NOT FOUND if the employee does not exist
     */
    @Operation(summary = "Get Employee by ID", description = "Retrieve an employee by their unique identifier.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the employee.")
    @ApiResponse(responseCode = "404", description = "Employee not found.")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        Optional<EmployeeDto> employeeDto = employeeService.findEmployeeById(id);
        return employeeDto.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    /**
     * Creates a new employee.
     *
     * This method validates the incoming employee data (received via DTO) and saves it to the database
     * if valid.
     *
     * @param employeeDto the DTO containing the employee information to be saved
     * @return a ResponseEntity containing the saved employee data
     */
    @Operation(summary = "Create a new Employee", description = "Create a new employee record.")
    @ApiResponse(responseCode = "201", description = "Employee created successfully.")
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }



    /**
     * Update the details of an existing employee using the provided EmployeeDto.
     *
     * This method accepts the employee's ID and a DTO containing updated employee details.
     * It updates the employee record if it exists and returns the updated EmployeeDto object.
     *
     * @param id the ID of the employee to be updated
     * @param employeeDto the DTO containing updated employee details
     * @return a ResponseEntity containing the updated EmployeeDto and an HTTP status of OK,
     *         or NOT FOUND if the employee does not exist
     */
    @Operation(summary = "Update an existing Employee", description = "Update the details of an existing employee.")
    @ApiResponse(responseCode = "200", description = "Employee updated successfully.")
    @ApiResponse(responseCode = "404", description = "Employee not found")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeDto employeeDto) {
        Optional<EmployeeDto> employeeDtoOptional = employeeService.findEmployeeById(id);
        if (employeeDtoOptional.isPresent()) {
            EmployeeDto updatedEmployee = employeeService.updateEmployee(id, employeeDto);
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    /**
     * Delete an employee by their ID.
     *
     * This method deletes the employee record based on the given ID if it exists.
     *
     * @param id the ID of the employee to delete
     * @return a ResponseEntity with HTTP status NO CONTENT if successful,
     *         or NOT FOUND if the employee does not exist
     */
    @Operation(summary = "Delete Employee", description = "Delete an employee by its ID.")
    @ApiResponse(responseCode = "204", description = "Employee deleted successfully.")
    @ApiResponse(responseCode = "404", description = "Employee not found.")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse> deleteEmployee(@PathVariable Long id) {
        Optional<EmployeeDto> employeeDto = employeeService.findEmployeeById(id);
        if (employeeDto.isPresent()) {
            employeeService.deleteEmployee(id);
            CustomApiResponse customApiResponse = new CustomApiResponse("Employee deleted successfully.", true);
            return new ResponseEntity<>(customApiResponse, HttpStatus.NO_CONTENT);
        } else {
            // Agar xodim topilmasa, CustomApiResponse qaytaramiz
            CustomApiResponse customApiResponse = new CustomApiResponse("Employee not found with ID: " + id, false);
            return new ResponseEntity<>(customApiResponse, HttpStatus.NOT_FOUND);
        }
    }

}
