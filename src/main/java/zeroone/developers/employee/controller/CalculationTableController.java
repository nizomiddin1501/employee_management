package zeroone.developers.employee.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zeroone.developers.employee.entity.CalculationTable;
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
     * Get all calculations.
     *
     * @return a list of all calculations
     */
    @Operation(summary = "Get all Calculations", description = "Retrieve a list of all calculations.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of calculations.")
    @GetMapping
    public List<CalculationTable> getAllCalculations() {
        return calculationTableService.findAllCalculations();
    }



    /**
     * Get Calculation by ID.
     *
     * @param id the ID of the calculation
     * @return a ResponseEntity containing the calculation if found
     */
    @Operation(summary = "Get Calculation by ID", description = "Retrieve a calculation by their unique identifier.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the calculation.")
    @ApiResponse(responseCode = "404", description = "Calculation not found.")
    @GetMapping("/{id}")
    public ResponseEntity<CalculationTable> getCalculationById(@PathVariable Long id) {
        Optional<CalculationTable> calculation = calculationTableService.findCalculationById(id);
        return calculation.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    /**
     * Create a new Calculation.
     *
     * @param calculationTable the calculation record to be created
     * @return a ResponseEntity containing the created calculation
     */
    @Operation(summary = "Create a new Calculation", description = "Create a new calculation record.")
    @ApiResponse(responseCode = "201", description = "Calculation created successfully.")
    @PostMapping
    public ResponseEntity<CalculationTable> createCalculation(@RequestBody CalculationTable calculationTable) {
        CalculationTable savedCalculation = calculationTableService.saveCalculation(calculationTable);
        return new ResponseEntity<>(savedCalculation, HttpStatus.CREATED);
    }



    /**
     * Update a calculation table entry.
     *
     * @param id                   the ID of the calculation to be updated
     * @param calculationTableDetails the new details for the calculation
     * @return a ResponseEntity containing the updated calculation
     */
    @Operation(summary = "Update a calculation table entry", description = "Update the details of an existing Calculation.")
    @ApiResponse(responseCode = "200", description = "Calculation table updated successfully")
    @ApiResponse(responseCode = "404", description = "Calculation table not found")
    @PutMapping("/{id}")
    public ResponseEntity<CalculationTable> updateCalculation(
            @PathVariable Long id,
            @RequestBody CalculationTable calculationTableDetails) {
        Optional<CalculationTable> calculation = calculationTableService.findCalculationById(id);
        if (calculation.isPresent()) {
            CalculationTable updatedCalculationTable = calculationTableService.updateCalculationTable(id, calculationTableDetails);
            return new ResponseEntity<>(updatedCalculationTable, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    /**
     * Delete Calculation.
     *
     * @param id the ID of the calculation to be deleted
     * @return a ResponseEntity with no content if deleted successfully
     */
    @Operation(summary = "Delete Calculation", description = "Delete a calculation by its ID.")
    @ApiResponse(responseCode = "204", description = "Calculation deleted successfully.")
    @ApiResponse(responseCode = "404", description = "Calculation not found.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalculation(@PathVariable Long id) {
        Optional<CalculationTable> calculation = calculationTableService.findCalculationById(id);
        if (calculation.isPresent()) {
            calculationTableService.deleteCalculation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
