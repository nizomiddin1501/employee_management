package zeroone.developers.employee.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zeroone.developers.employee.entity.Region;
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
     * @return a ResponseEntity containing a list of RegionDto representing all regions
     */
    @Operation(summary = "Get all Regions", description = "Retrieve a list of all regions.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of regions.")
    @GetMapping
    public ResponseEntity<List<RegionDto>> getAllRegions() {
        List<RegionDto> regionDtos = regionService.findAllRegions();
        return new ResponseEntity<>(regionDtos, HttpStatus.OK);
    }



    /**
     * Retrieve a region by their unique ID using the provided RegionDto.
     *
     * This method retrieves a region's details based on their ID and returns
     * the corresponding RegionDto if found. If the region does not exist,
     * it returns a 404 Not Found status.
     *
     * @param id the ID of the region to retrieve
     * @return a ResponseEntity containing the RegionDto and an HTTP status of OK,
     *         or NOT FOUND if the region does not exist
     */
    @Operation(summary = "Get Region by ID", description = "Retrieve a region by their unique identifier.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the region.")
    @ApiResponse(responseCode = "404", description = "Region not found.")
    @GetMapping("/{id}")
    public ResponseEntity<RegionDto> getRegionById(@PathVariable Long id) {
        Optional<RegionDto> regionDto = regionService.findRegionById(id);
        return regionDto.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    /**
     * Creates a new region.
     *
     * This method validates the incoming region data (received via DTO) and saves it to the database
     * if valid.
     *
     * @param regionDto the DTO containing the region information to be saved
     * @return a ResponseEntity containing the saved region data
     */
    @Operation(summary = "Create a new Region", description = "Create a new region record.")
    @ApiResponse(responseCode = "201", description = "Region created successfully.")
    @PostMapping
    public ResponseEntity<RegionDto> createRegion(@RequestBody RegionDto regionDto) {
        RegionDto savedRegion = regionService.saveRegion(regionDto);
        return new ResponseEntity<>(savedRegion, HttpStatus.CREATED);
    }



    /**
     * Update the details of an existing region using the provided RegionDto.
     *
     * This method accepts the region's ID and a DTO containing updated region details.
     * It updates the region record if it exists and returns the updated RegionDto object.
     *
     * @param id the ID of the region to be updated
     * @param regionDto the DTO containing updated region details
     * @return a ResponseEntity containing the updated RegionDto and an HTTP status of OK,
     *         or NOT FOUND if the region does not exist
     */
    @Operation(summary = "Update region", description = "Update the details of an existing region.")
    @ApiResponse(responseCode = "200", description = "Region updated successfully")
    @ApiResponse(responseCode = "404", description = "Region not found")
    @PutMapping("/{id}")
    public ResponseEntity<RegionDto> updateRegion(
            @PathVariable Long id,
            @RequestBody RegionDto regionDto) {
        Optional<RegionDto> regionDtoOptional = regionService.findRegionById(id);
        if (regionDtoOptional.isPresent()) {
            RegionDto updatedRegion = regionService.updateRegion(id, regionDto);
            return new ResponseEntity<>(updatedRegion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    /**
     * Delete an region by their ID.
     *
     * This method deletes the region record based on the given ID if it exists.
     *
     * @param id the ID of the region to delete
     * @return a ResponseEntity with HTTP status NO CONTENT if successful,
     *         or NOT FOUND if the region does not exist
     */
    @Operation(summary = "Delete Region", description = "Delete a region by its ID.")
    @ApiResponse(responseCode = "204", description = "Region deleted successfully.")
    @ApiResponse(responseCode = "404", description = "Region not found.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegion(@PathVariable Long id) {
        Optional<RegionDto> regionDto = regionService.findRegionById(id);
        if (regionDto.isPresent()) {
            regionService.deleteRegion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
