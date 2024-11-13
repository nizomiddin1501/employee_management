package zeroone.developers.employee.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zeroone.developers.employee.exception.EmployeeException;
import zeroone.developers.employee.exception.RegionException;
import zeroone.developers.employee.payload.CustomApiResponse;
import zeroone.developers.employee.payload.EmployeeDto;
import zeroone.developers.employee.service.EmployeeService;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing employees, offering endpoints for
 * creating, updating, retrieving, and deleting employee records.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {


    private final EmployeeService employeeService;


    /**
     * Retrieve a list of all employees.
     * <p>
     * This method fetches all employee records and returns them as a list of EmployeeDto.
     *
     * @return a ResponseEntity containing a CustomApiResponse with the list of EmployeeDto representing all employees
     */
    @Operation(summary = "Get all Employees", description = "Retrieve a list of all employees.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of employees.")
    @GetMapping
    public ResponseEntity<CustomApiResponse<List<EmployeeDto>>> getAllEmployees() {
        List<EmployeeDto> employeeDtos = employeeService.findAllEmployees();
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Successfully retrieved the list of employees.",
                true,
                employeeDtos), HttpStatus.OK);
    }


//    @Operation(summary = "Get all Employees with Pagination", description = "Retrieve a paginated list of all employees.")
//    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of employees.")
//    @GetMapping
//    public ResponseEntity<CustomApiResponse<Page<EmployeeDto>>> getAllEmployees(
//            @RequestParam(value = "page", defaultValue = "0") int page,
//            @RequestParam(value = "size", defaultValue = "10") int size
//    ) {
//        Page<EmployeeDto> employeeDtos = employeeService.getAllEmployees(page,size);
//        return new ResponseEntity<>(new CustomApiResponse<>(
//                "Successfully retrieved the list of employees.",
//                true,
//                employeeDtos), HttpStatus.OK);
//    }


    /**
     * Retrieve an employee by their unique ID using the provided EmployeeDto.
     *
     * @param id the ID of the employee to retrieve
     * @return a ResponseEntity containing a CustomApiResponse with the EmployeeDto and
     * an HTTP status of OK
     */
    @Operation(summary = "Get Employee by ID", description = "Retrieve an employee by their unique identifier.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the employee.")
    @ApiResponse(responseCode = "404", description = "Employee not found.")
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<EmployeeDto>> getEmployeeById(@PathVariable Long id) {
        EmployeeDto employeeDto = employeeService.findEmployeeById(id)
                .orElseThrow(() -> new EmployeeException("Employee not found"));
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Successfully retrieved the employee.",
                true,
                employeeDto), HttpStatus.OK);
    }


    /**
     * Creates a new employee.
     *
     * @param employeeDto the DTO containing the employee information to be saved
     * @return a ResponseEntity containing a CustomApiResponse with the saved employee data
     */
    @Operation(summary = "Create a new Employee", description = "Create a new employee record.")
    @ApiResponse(responseCode = "201", description = "Employee created successfully.")
    @PostMapping
    public ResponseEntity<CustomApiResponse<EmployeeDto>> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Employee created successfully",
                true,
                savedEmployee), HttpStatus.CREATED);
    }


    /**
     * Update the details of an existing employee using the provided EmployeeDto.
     *
     * @param id          the ID of the employee to be updated
     * @param employeeDto the DTO containing updated employee details
     * @return a ResponseEntity containing a CustomApiResponse with the updated EmployeeDto
     */
    @Operation(summary = "Update an existing Employee", description = "Update the details of an existing employee.")
    @ApiResponse(responseCode = "200", description = "Employee updated successfully.")
    @ApiResponse(responseCode = "404", description = "Employee not found")
    @PutMapping("/{id}")
    public ResponseEntity<CustomApiResponse<EmployeeDto>> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeDto employeeDto) {
        EmployeeDto updatedEmployee = employeeService.updateEmployee(id, employeeDto);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Employee updated successfully",
                true,
                updatedEmployee), HttpStatus.OK);
    }


    /**
     * Delete an employee by their ID.
     *
     * @param id the ID of the employee to delete
     * @return a ResponseEntity containing a CustomApiResponse with the status of the operation
     */
    @Operation(summary = "Delete Employee", description = "Delete an employee by its ID.")
    @ApiResponse(responseCode = "204", description = "Employee deleted successfully.")
    @ApiResponse(responseCode = "404", description = "Employee not found.")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Void>> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Employee deleted successfully.",
                true,
                null), HttpStatus.NO_CONTENT);
    }

}
