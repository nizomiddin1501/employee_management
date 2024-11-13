package zeroone.developers.employee.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zeroone.developers.employee.exception.CalculationTableException;
import zeroone.developers.employee.exception.RegionException;
import zeroone.developers.employee.payload.CalculationTableDto;
import zeroone.developers.employee.payload.CustomApiResponse;
import zeroone.developers.employee.service.CalculationTableService;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing calculations, offering endpoints for
 * creating, updating, retrieving, and deleting calculation records.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calculations")
public class CalculationTableController {


    private final CalculationTableService calculationTableService;


    /**
     * Get employees with higher salary than the specified threshold.
     * <p>
     * This method retrieves a list of employees who earned more than a specified threshold
     * in the given month. If no employees are found, it returns a 404 Not Found status with a message.
     *
     * @param month     the month for which the report is generated
     * @param threshold the salary threshold
     * @return a ResponseEntity containing a CustomApiResponse with either the list of employees
     * with higher salary and an HTTP status of OK, or a message and NOT FOUND status if no employees are found.
     */
    @Operation(summary = "Get employees with higher salary than the specified threshold",
            description = "Returns a list of employees who earned more than a specified threshold in the given month.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "404", description = "Data not found for the provided parameters")
    })
    @GetMapping("/reports/high-salary")
    public ResponseEntity<CustomApiResponse<List<Object[]>>> getEmployeesWithHigherSalary(
            @RequestParam int month, @RequestParam double threshold) {
        List<Object[]> results = calculationTableService.getEmployeesWithHigherSalary(month, threshold);

        if (results.isEmpty()) {
            CustomApiResponse<List<Object[]>> response = new CustomApiResponse<>(
                    "No employees found with higher salary for the provided month and threshold.",
                    false,
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Success response
        CustomApiResponse<List<Object[]>> response = new CustomApiResponse<>(
                "Successfully retrieved list of employees with higher salary.",
                true,
                results
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Get employees by region.
     * <p>
     * This method retrieves a list of employees who worked in different regions
     * during the specified month. If no employees are found for the given month,
     * it returns a 404 Not Found status with a corresponding message.
     *
     * @param month the month for which the report is generated
     * @return a ResponseEntity containing a CustomApiResponse with either the list of
     * employees by region and an HTTP status of OK, or a message and NOT FOUND status
     * if no employees are found.
     */
    @Operation(summary = "Get employees by region",
            description = "Returns a list of employees who worked in different regions during the specified month.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "404", description = "Data not found for the provided parameters")
    })
    @GetMapping("/reports/region")
    public ResponseEntity<CustomApiResponse<List<Object[]>>> getEmployeesByRegion(@RequestParam int month) {
        List<Object[]> results = calculationTableService.getEmployeesByRegion(month);

        if (results.isEmpty()) {
            // No data found
            CustomApiResponse<List<Object[]>> response = new CustomApiResponse<>(
                    "No employees found for the specified month and region.",
                    false,
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Success response
        CustomApiResponse<List<Object[]>> response = new CustomApiResponse<>(
                "Successfully retrieved list of employees with same region worked.",
                true,
                results
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Retrieves the average salary for a specified organization and month.
     * <p>
     * This method calculates and returns the average salary of employees
     * who worked for a given organization during the specified month.
     * If no data is found for the provided organization and month, it returns a
     * 404 Not Found status with an appropriate message.
     *
     * @param month          the month for which the average salary report is generated
     * @param organizationId the ID of the organization
     * @return a ResponseEntity containing a CustomApiResponse with either the average salary data
     * and an HTTP status of OK, or a message and NOT FOUND status if no data is found
     */
    @Operation(summary = "Get average salary for organization",
            description = "Returns the average salary of employees in a given organization for the specified month.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "404", description = "Organization or data not found")
    })
    @GetMapping("/reports/average-salary")
    public ResponseEntity<CustomApiResponse<List<Object[]>>> getAverageSalaryByOrganization(
            @RequestParam int month, @RequestParam Long organizationId) {
        List<Object[]> results = calculationTableService.getAverageSalaryByOrganization(month, organizationId);

        if (results.isEmpty()) {
            // No data found
            CustomApiResponse<List<Object[]>> response = new CustomApiResponse<>(
                    "No data found for the specified organization and month.",
                    false,
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Success response
        CustomApiResponse<List<Object[]>> response = new CustomApiResponse<>(
                "Successfully retrieved average salary for the specified organization.",
                true,
                results
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Get employees who received both salary and vacation payments.
     * <p>
     * This method retrieves a list of employees who received both salary
     * and vacation payments for the specified month. If no such employees
     * are found, it returns a 404 Not Found status with an appropriate message.
     *
     * @param month the month for which the report is generated
     * @return a ResponseEntity containing a CustomApiResponse with either the data
     * of employees or a message if no data is found
     */
    @Operation(summary = "Get employees with salaries and vacations",
            description = "Returns a list of employees who received both salary and vacation payments in the specified month.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "404", description = "Data not found")
    })
    @GetMapping("/reports/salaries-vacations")
    public ResponseEntity<CustomApiResponse<List<Object[]>>> getEmployeesWithSalariesAndVacations(@RequestParam int month) {
        List<Object[]> results = calculationTableService.getEmployeesWithSalariesAndVacations(month);

        if (results.isEmpty()) {
            // No data found
            CustomApiResponse<List<Object[]>> response = new CustomApiResponse<>(
                    "No employees found with both salary and vacation payments for the provided month.",
                    false,
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Success response
        CustomApiResponse<List<Object[]>> response = new CustomApiResponse<>(
                "Successfully retrieved list of employees with both salary and vacation payments.",
                true,
                results
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




    /**
     * Retrieve a list of all calculationTables.
     * <p>
     * This method fetches all calculationTable records and returns them as a list of CalculationTableDto.
     *
     * @return a ResponseEntity containing a CustomApiResponse with the list of CalculationTableDto representing all calculationTables
     */
    @Operation(summary = "Get all Calculations", description = "Retrieve a list of all calculations.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of calculations.")
    @GetMapping
    public ResponseEntity<CustomApiResponse<List<CalculationTableDto>>> getAllCalculations() {
        List<CalculationTableDto> calculationTableDtos = calculationTableService.findAllCalculations();
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Successfully retrieved the list of calculations.",
                true,
                calculationTableDtos), HttpStatus.OK);
    }


    /**
     * Retrieve a calculation by their unique ID using the provided CalculationTableDto.
     *
     * @param id the ID of the calculation to retrieve
     * @return a ResponseEntity containing a CustomApiResponse with the CalculationTableDto and
     * an HTTP status of OK
     */
    @Operation(summary = "Get Calculation by ID", description = "Retrieve a calculation by their unique identifier.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the calculation.")
    @ApiResponse(responseCode = "404", description = "Calculation not found.")
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<CalculationTableDto>> getCalculationById(@PathVariable Long id) {
        CalculationTableDto calculationTableDto = calculationTableService.findCalculationById(id)
                .orElseThrow(() -> new CalculationTableException("CalculationTable not found"));
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Successfully retrieved the calculationTable.",
                true,
                calculationTableDto), HttpStatus.OK);
    }


    /**
     * Creates a new calculationTable.
     *
     * @param calculationTableDto the DTO containing the room information to be saved
     * @return a ResponseEntity containing a CustomApiResponse with the saved calculationTable data
     */
    @Operation(summary = "Create a new Calculation", description = "Create a new calculation record.")
    @ApiResponse(responseCode = "201", description = "Calculation created successfully.")
    @PostMapping
    public ResponseEntity<CustomApiResponse<CalculationTableDto>> createCalculation(@Valid @RequestBody CalculationTableDto calculationTableDto) {
        CalculationTableDto savedCalculation = calculationTableService.saveCalculation(calculationTableDto);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Calculation created successfully",
                true,
                savedCalculation), HttpStatus.CREATED);
    }


    /**
     * Update the details of an existing calculationTable using the provided CalculationTableDto.
     *
     * @param id                  the ID of the calculationTable to be updated
     * @param calculationTableDto the DTO containing updated calculationTable details
     * @return a ResponseEntity containing a CustomApiResponse with the updated CalculationTableDto
     */
    @Operation(summary = "Update a calculation table entry", description = "Update the details of an existing Calculation.")
    @ApiResponse(responseCode = "200", description = "Calculation table updated successfully")
    @ApiResponse(responseCode = "404", description = "Calculation table not found")
    @PutMapping("/{id}")
    public ResponseEntity<CustomApiResponse<CalculationTableDto>> updateCalculation(
            @PathVariable Long id,
            @RequestBody CalculationTableDto calculationTableDto) {
        CalculationTableDto updateCalculationTable = calculationTableService.updateCalculationTable(id, calculationTableDto);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Calculation updated successfully",
                true,
                updateCalculationTable), HttpStatus.OK);
    }


    /**
     * Delete a calculationTable by their ID.
     *
     * @param id the ID of the calculationTable to delete
     * @return a ResponseEntity containing a CustomApiResponse with the status of the operation
     */
    @Operation(summary = "Delete Calculation", description = "Delete a calculation by its ID.")
    @ApiResponse(responseCode = "204", description = "Calculation deleted successfully.")
    @ApiResponse(responseCode = "404", description = "Calculation not found.")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Void>> deleteCalculation(@PathVariable Long id) {
        calculationTableService.deleteCalculation(id);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Calculation deleted successfully.",
                true,
                null), HttpStatus.NO_CONTENT);
    }
}
