package zeroone.developers.employee.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zeroone.developers.employee.entity.CalculationTable;
import zeroone.developers.employee.payload.CalculationTableDto;
import zeroone.developers.employee.payload.CustomApiResponse;
import zeroone.developers.employee.payload.EmployeeDto;
import zeroone.developers.employee.service.CalculationTableService;

import java.util.List;
import java.util.Optional;
/**
 * Controller for handling requests related to CalculationTable operations.
 * This controller provides RESTful endpoints to manage calculation records
 * and generate various reports based on calculations.
 */
@RestController
@RequestMapping("/api/calculations")
public class CalculationTableController {


    private final CalculationTableService calculationTableService;

    /**
     * Constructor for CalculationTableController.
     *
     * @param calculationTableService the service to manage calculation records
     * @Autowired automatically injects the CalculationTableService bean
     */
    @Autowired
    public CalculationTableController(CalculationTableService calculationTableService) {
        this.calculationTableService = calculationTableService;
    }

    //native query methods


    /**
     * Get employees with higher salary than the specified threshold.
     *
     * @param month    the month for which the report is generated
     * @param threshold the salary threshold
     * @return a ResponseEntity containing a list of employees with salaries above the threshold
     */
    @Operation(summary = "Get employees with higher salary than the specified threshold",
            description = "Returns a list of employees who earned more than a specified threshold in the given month.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "404", description = "Data not found for the provided parameters")
    })
    @GetMapping("/reports/high-salary")
    public ResponseEntity<List<Object[]>> getEmployeesWithHigherSalary(
            @RequestParam int month, @RequestParam double threshold) {
        List<Object[]> results = calculationTableService.getEmployeesWithHigherSalary(month, threshold);
        return ResponseEntity.ok(results);
    }



    /**
     * Get employees by region.
     *
     * @param month the month for which the report is generated
     * @return a ResponseEntity containing a list of employees by region
     */
    @Operation(summary = "Get employees by region",
            description = "Returns a list of employees who worked in different regions during the specified month.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "404", description = "Data not found for the provided parameters")
    })
    @GetMapping("/reports/region")
    public ResponseEntity<List<Object[]>> getEmployeesByRegion(@RequestParam int month) {
        List<Object[]> results = calculationTableService.getEmployeesByRegion(month);
        return ResponseEntity.ok(results);
    }



    /**
     * Get average salary for organization.
     *
     * @param month          the month for which the report is generated
     * @param organizationId the ID of the organization
     * @return a ResponseEntity containing the average salary for the specified organization
     */
    @Operation(summary = "Get average salary for organization",
            description = "Returns the average salary of employees in a given organization for the specified month.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "404", description = "Organization or data not found")
    })
    @GetMapping("/reports/average-salary")
    public ResponseEntity<List<Object[]>> getAverageSalaryByOrganization(
            @RequestParam int month, @RequestParam Long organizationId) {
        List<Object[]> results = calculationTableService.getAverageSalaryByOrganization(month, organizationId);
        return ResponseEntity.ok(results);
    }



    /**
     * Get employees with salaries and vacations.
     *
     * @param month the month for which the report is generated
     * @return a ResponseEntity containing a list of employees who received both salary and vacation payments
     */
    @Operation(summary = "Get employees with salaries and vacations",
            description = "Returns a list of employees who received both salary and vacation payments in the specified month.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "404", description = "Data not found")
    })
    @GetMapping("/reports/salaries-vacations")
    public ResponseEntity<List<Object[]>> getEmployeesWithSalariesAndVacations(@RequestParam int month) {
        List<Object[]> results = calculationTableService.getEmployeesWithSalariesAndVacations(month);
        return ResponseEntity.ok(results);
    }


    //CRUD methods


    /**
     * Retrieve a list of all calculationTables.
     *
     * This method fetches all calculationTable records and returns them as a list of CalculationTableDto.
     *
     * @return a ResponseEntity containing a CustomApiResponse with the list of CalculationTableDto representing all calculationTables
     */
    @Operation(summary = "Get all Calculations", description = "Retrieve a list of all calculations.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of calculations.")
    @GetMapping
    public ResponseEntity<CustomApiResponse<List<CalculationTableDto>>> getAllCalculations() {
        List<CalculationTableDto> calculationTableDtos = calculationTableService.findAllCalculations();
        CustomApiResponse<List<CalculationTableDto>> response = new CustomApiResponse<>(
                "Successfully retrieved the list of calculationTables.",
                true,
                calculationTableDtos
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    /**
     * Retrieve an calculationTable by their unique ID using the provided CalculationTableDto.
     *
     * This method retrieves an calculationTable's details based on their ID and returns
     * a CustomApiResponse containing the corresponding CalculationTableDto if found.
     * If the calculationTable does not exist, it returns a CustomApiResponse with a
     * message indicating that the calculationTable was not found and a 404 Not Found status.
     *
     * @param id the ID of the calculationTable to retrieve
     * @return a ResponseEntity containing a CustomApiResponse with the CalculationTableDto and
     *         an HTTP status of OK, or a NOT FOUND status if the calculationTable does not exist.
     */
    @Operation(summary = "Get Calculation by ID", description = "Retrieve a calculation by their unique identifier.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the calculation.")
    @ApiResponse(responseCode = "404", description = "Calculation not found.")
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<CalculationTableDto>> getCalculationById(@PathVariable Long id) {
        Optional<CalculationTableDto> calculationTableDto = calculationTableService.findCalculationById(id);
        if (calculationTableDto.isPresent()){
            CustomApiResponse<CalculationTableDto> response = new CustomApiResponse<>(
                    "Successfully retrieved the calculationTable.",
                    true,
                    calculationTableDto.get()
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            CustomApiResponse<CalculationTableDto> response = new CustomApiResponse<>(
                    "CalculationTable not found.",
                    false,
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }



    /**
     * Creates a new calculationTable.
     *
     * This method validates the incoming calculationTable data (received via DTO) and saves it to the database
     * if valid.
     *
     * @param calculationTableDto the DTO containing the calculationTable information to be saved
     * @return a ResponseEntity containing a CustomApiResponse with the saved calculationTable data
     */
    @Operation(summary = "Create a new Calculation", description = "Create a new calculation record.")
    @ApiResponse(responseCode = "201", description = "Calculation created successfully.")
    @PostMapping
    public ResponseEntity<CustomApiResponse<CalculationTableDto>> createCalculation(@Valid @RequestBody CalculationTableDto calculationTableDto) {
        CalculationTableDto savedCalculation = calculationTableService.saveCalculation(calculationTableDto);
        CustomApiResponse<CalculationTableDto> response = new CustomApiResponse<>(
                "CalculationTable created successfully",
                true,
                savedCalculation
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    /**
     * Update the details of an existing calculationTable using the provided CalculationTableDto.
     *
     * This method accepts the calculationTable's ID and a DTO containing updated calculationTable details.
     * It updates the calculationTable record if it exists and returns the updated CalculationTableDto object.
     *
     * @param id the ID of the calculationTable to be updated
     * @param calculationTableDto the DTO containing updated calculationTable details
     * @return a ResponseEntity containing a CustomApiResponse with the updated CalculationTableDto,
     *         or a NOT FOUND response if the calculationTable does not exist
     */
    @Operation(summary = "Update a calculation table entry", description = "Update the details of an existing Calculation.")
    @ApiResponse(responseCode = "200", description = "Calculation table updated successfully")
    @ApiResponse(responseCode = "404", description = "Calculation table not found")
    @PutMapping("/{id}")
    public ResponseEntity<CustomApiResponse<CalculationTableDto>> updateCalculation(
            @PathVariable Long id,
            @RequestBody CalculationTableDto calculationTableDto) {
        Optional<CalculationTableDto> calculationTableDtoOptional = calculationTableService.findCalculationById(id);
        if (calculationTableDtoOptional.isPresent()) {
            CalculationTableDto updateCalculationTable = calculationTableService.updateCalculationTable(id, calculationTableDto);
            CustomApiResponse<CalculationTableDto> response = new CustomApiResponse<>(
                    "CalculationTable updated successfully",
                    true,
                    updateCalculationTable
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            CustomApiResponse<CalculationTableDto> response = new CustomApiResponse<>(
                    "CalculationTable not found",
                    false,
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }



    /**
     * Delete an calculationTable by their ID.
     *
     * This method deletes the calculationTable record based on the given ID if it exists.
     *
     * @param id the ID of the calculationTable to delete
     * @return a ResponseEntity containing a CustomApiResponse with the status of the operation,
     *         or NOT FOUND if the calculationTable does not exist
     */
    @Operation(summary = "Delete Calculation", description = "Delete a calculation by its ID.")
    @ApiResponse(responseCode = "204", description = "Calculation deleted successfully.")
    @ApiResponse(responseCode = "404", description = "Calculation not found.")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Void>> deleteCalculation(@PathVariable Long id) {
        Optional<CalculationTableDto> calculationTableDto = calculationTableService.findCalculationById(id);
        if (calculationTableDto.isPresent()) {
            calculationTableService.deleteCalculation(id);
            CustomApiResponse<Void> customApiResponse = new CustomApiResponse<>(
                    "CalculationTable deleted successfully.",
                    true,
                    null);
            return new ResponseEntity<>(customApiResponse, HttpStatus.NO_CONTENT);
        } else {
            CustomApiResponse<Void> customApiResponse = new CustomApiResponse<>(
                    "CalculationTable not found with ID: " + id,
                    false,
                    null);
            return new ResponseEntity<>(customApiResponse, HttpStatus.NOT_FOUND);
        }
    }


}
