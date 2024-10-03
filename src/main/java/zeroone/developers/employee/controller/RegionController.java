package zeroone.developers.employee.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zeroone.developers.employee.payload.CustomApiResponse;
import zeroone.developers.employee.payload.RegionDto;
import zeroone.developers.employee.service.RegionService;

import java.util.List;
import java.util.Optional;
/**
 * Controller for handling requests related to Region operations.
 * This controller provides RESTful endpoints to manage region records,
 * including creating, updating, retrieving, and deleting region information.
 */
@RestController
@RequestMapping("/api/regions")
public class RegionController {

    private final RegionService regionService;

    /**
     * Constructor for RegionController.
     *
     * @param regionService the service to manage region records
     * @Autowired automatically injects the RegionService bean
     */
    @Autowired
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }


    /**
     * Retrieve a list of all regions.
     *
     * This method fetches all region records and returns them as a list of RegionDto.
     *
     * @return a ResponseEntity containing a CustomApiResponse with the list of RegionDto representing all regions
     */
    @Operation(summary = "Get all Regions", description = "Retrieve a list of all regions.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of regions.")
    @GetMapping
    public ResponseEntity<CustomApiResponse<List<RegionDto>>> getAllRegions() {
        List<RegionDto> regionDtos = regionService.findAllRegions();
        CustomApiResponse<List<RegionDto>> response = new CustomApiResponse<>(
                "Successfully retrieved the list of regions.",
                true,
                regionDtos
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    /**
     * Retrieve a region by their unique ID using the provided RegionDto.
     *
     * This method retrieves a region's details based on their ID and returns
     * a CustomApiResponse containing the corresponding RegionDto if found.
     * If the region does not exist, it returns a CustomApiResponse with a
     * message indicating that the region was not found and a 404 Not Found status.
     *
     * @param id the ID of the region to retrieve
     * @return a ResponseEntity containing a CustomApiResponse with the RegionDto and
     *         an HTTP status of OK, or a NOT FOUND status if the region does not exist.
     */
    @Operation(summary = "Get Region by ID", description = "Retrieve a region by their unique identifier.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the region.")
    @ApiResponse(responseCode = "404", description = "Region not found.")
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<RegionDto>> getRegionById(@PathVariable Long id) {
        Optional<RegionDto> regionDto = regionService.findRegionById(id);
        if (regionDto.isPresent()){
            CustomApiResponse<RegionDto> response = new CustomApiResponse<>(
                    "Successfully retrieved the region.",
                    true,
                    regionDto.get()
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            CustomApiResponse<RegionDto> response = new CustomApiResponse<>(
                    "Region not found.",
                    false,
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }



    /**
     * Creates a new region.
     *
     * This method validates the incoming region data (received via DTO) and saves it to the database
     * if valid.
     *
     * @param regionDto the DTO containing the region information to be saved
     * @return a ResponseEntity containing a CustomApiResponse with the saved region data
     */
    @Operation(summary = "Create a new Region", description = "Create a new region record.")
    @ApiResponse(responseCode = "201", description = "Region created successfully.")
    @PostMapping
    public ResponseEntity<CustomApiResponse<RegionDto>> createRegion(@RequestBody RegionDto regionDto) {
        RegionDto savedRegion = regionService.saveRegion(regionDto);
        CustomApiResponse<RegionDto> response = new CustomApiResponse<>(
                "Region created successfully",
                true,
                savedRegion
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    /**
     * Update the details of an existing region using the provided RegionDto.
     *
     * This method accepts the region's ID and a DTO containing updated region details.
     * It updates the region record if it exists and returns the updated RegionDto object.
     *
     * @param id the ID of the region to be updated
     * @param regionDto the DTO containing updated region details
     * @return a ResponseEntity containing a CustomApiResponse with the updated RegionDto,
     *         or a NOT FOUND response if the region does not exist
     */
    @Operation(summary = "Update region", description = "Update the details of an existing region.")
    @ApiResponse(responseCode = "200", description = "Region updated successfully")
    @ApiResponse(responseCode = "404", description = "Region not found")
    @PutMapping("/{id}")
    public ResponseEntity<CustomApiResponse<RegionDto>> updateRegion(
            @PathVariable Long id,
            @RequestBody RegionDto regionDto) {
        Optional<RegionDto> regionDtoOptional = regionService.findRegionById(id);
        if (regionDtoOptional.isPresent()) {
            RegionDto updatedRegion = regionService.updateRegion(id, regionDto);
            CustomApiResponse<RegionDto> response = new CustomApiResponse<>(
                    "Region updated successfully",
                    true,
                    updatedRegion
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            CustomApiResponse<RegionDto> response = new CustomApiResponse<>(
                    "Region not found",
                    false,
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }



    /**
     * Delete a region by their ID.
     *
     * This method deletes the region record based on the given ID if it exists.
     *
     * @param id the ID of the region to delete
     * @return a ResponseEntity containing a CustomApiResponse with the status of the operation,
     *         or NOT FOUND if the region does not exist
     */
    @Operation(summary = "Delete Region", description = "Delete a region by its ID.")
    @ApiResponse(responseCode = "204", description = "Region deleted successfully.")
    @ApiResponse(responseCode = "404", description = "Region not found.")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Void>> deleteRegion(@PathVariable Long id) {
        Optional<RegionDto> regionDto = regionService.findRegionById(id);
        if (regionDto.isPresent()) {
            regionService.deleteRegion(id);
            CustomApiResponse<Void> customApiResponse = new CustomApiResponse<>(
                    "Region deleted successfully.",
                    true,
                    null);
            return new ResponseEntity<>(customApiResponse, HttpStatus.NO_CONTENT);
        } else {
            CustomApiResponse<Void> customApiResponse = new CustomApiResponse<>(
                    "Region not found with ID: " + id,
                    false,
                    null);
            return new ResponseEntity<>(customApiResponse, HttpStatus.NOT_FOUND);
        }
    }




}
