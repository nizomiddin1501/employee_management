package zeroone.developers.employee.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zeroone.developers.employee.exception.RegionException;
import zeroone.developers.employee.payload.CustomApiResponse;
import zeroone.developers.employee.payload.RegionDto;
import zeroone.developers.employee.service.RegionService;

import java.util.List;

/**
 * REST controller for managing regions, offering endpoints for
 * creating, updating, retrieving, and deleting region records.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/regions")
public class RegionController {

    private final RegionService regionService;


    /**
     * Retrieve a list of all regions.
     * <p>
     * This method fetches all region records and returns them as a list of RegionDto.
     *
     * @return a ResponseEntity containing a CustomApiResponse with the list of RegionDto representing all regions
     */
    @Operation(summary = "Get all Regions", description = "Retrieve a list of all regions.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of regions.")
    @GetMapping
    public ResponseEntity<CustomApiResponse<List<RegionDto>>> getAllRegions() {
        List<RegionDto> regionDtos = regionService.findAllRegions();
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Successfully retrieved the list of regions.",
                true,
                regionDtos), HttpStatus.OK);
    }


    /**
     * Retrieve a region by their unique ID using the provided RegionDto.
     *
     * @param id the ID of the region to retrieve
     * @return a ResponseEntity containing a CustomApiResponse with the RegionDto and
     * an HTTP status of OK
     */
    @Operation(summary = "Get Region by ID", description = "Retrieve a region by their unique identifier.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the region.")
    @ApiResponse(responseCode = "404", description = "Region not found.")
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<RegionDto>> getRegionById(@PathVariable Long id) {
        RegionDto regionDto = regionService.findRegionById(id)
                .orElseThrow(() -> new RegionException("Region not found"));
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Successfully retrieved the region.",
                true,
                regionDto), HttpStatus.OK);
    }


    /**
     * Creates a new region.
     *
     * @param regionDto the DTO containing the region information to be saved
     * @return a ResponseEntity containing a CustomApiResponse with the saved region data
     */
    @Operation(summary = "Create a new Region", description = "Create a new region record.")
    @ApiResponse(responseCode = "201", description = "Region created successfully.")
    @PostMapping
    public ResponseEntity<CustomApiResponse<RegionDto>> createRegion(@RequestBody RegionDto regionDto) {
        RegionDto savedRegion = regionService.saveRegion(regionDto);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Region created successfully",
                true,
                savedRegion), HttpStatus.CREATED);
    }


    /**
     * Update the details of an existing region using the provided RegionDto.
     *
     * @param id        the ID of the region to be updated
     * @param regionDto the DTO containing updated region details
     * @return a ResponseEntity containing a CustomApiResponse with the updated RegionDto
     */
    @Operation(summary = "Update region", description = "Update the details of an existing region.")
    @ApiResponse(responseCode = "200", description = "Region updated successfully")
    @ApiResponse(responseCode = "404", description = "Region not found")
    @PutMapping("/{id}")
    public ResponseEntity<CustomApiResponse<RegionDto>> updateRegion(
            @PathVariable Long id,
            @RequestBody RegionDto regionDto) {
        RegionDto updatedRegion = regionService.updateRegion(id, regionDto);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Region updated successfully",
                true,
                updatedRegion), HttpStatus.OK);
    }


    /**
     * Delete a region by their ID.
     *
     * @param id the ID of the region to delete
     * @return a ResponseEntity containing a CustomApiResponse with the status of the operation
     */
    @Operation(summary = "Delete Region", description = "Delete a region by its ID.")
    @ApiResponse(responseCode = "204", description = "Region deleted successfully.")
    @ApiResponse(responseCode = "404", description = "Region not found.")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Void>> deleteRegion(@PathVariable Long id) {
        regionService.deleteRegion(id);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Region deleted successfully.",
                true,
                null), HttpStatus.NO_CONTENT);
    }
}

