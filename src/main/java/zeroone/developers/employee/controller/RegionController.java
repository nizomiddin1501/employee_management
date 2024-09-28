package zeroone.developers.employee.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zeroone.developers.employee.entity.Region;
import zeroone.developers.employee.service.RegionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/regions")
public class RegionController {

    private final RegionService regionService;

    @Autowired
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    //read all
    @Operation(summary = "Get all Regions", description = "Retrieve a list of all regions.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of regions.")
    @GetMapping
    public List<Region> getAllRegions() {
        return regionService.findAllRegions();
    }

    //read by ID
    @Operation(summary = "Get Region by ID", description = "Retrieve a region by their unique identifier.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the region.")
    @ApiResponse(responseCode = "404", description = "Region not found.")
    @GetMapping("/{id}")
    public ResponseEntity<Region> getRegionById(@PathVariable Long id) {
        Optional<Region> region = regionService.findRegionById(id);
        return region.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //create
    @Operation(summary = "Create a new Region", description = "Create a new region record.")
    @ApiResponse(responseCode = "201", description = "Region created successfully.")
    @PostMapping
    public ResponseEntity<Region> createRegion(@RequestBody Region region) {
        Region savedRegion = regionService.saveRegion(region);
        return new ResponseEntity<>(savedRegion, HttpStatus.CREATED);
    }

    //update
    @Operation(summary = "Update region", description = "Update the details of an existing region.")
    @ApiResponse(responseCode = "200", description = "Region updated successfully")
    @ApiResponse(responseCode = "404", description = "Region not found")
    @PutMapping("/{id}")
    public ResponseEntity<Region> updateRegion(
            @PathVariable Long id,
            @RequestBody Region regionDetails) {
        Optional<Region> region = regionService.findRegionById(id);
        if (region.isPresent()) {
            Region updatedRegion = regionService.updateRegion(id, regionDetails);
            return new ResponseEntity<>(updatedRegion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete Region", description = "Delete a region by its ID.")
    @ApiResponse(responseCode = "204", description = "Region deleted successfully.")
    @ApiResponse(responseCode = "404", description = "Region not found.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegion(@PathVariable Long id) {
        Optional<Region> region = regionService.findRegionById(id);
        if (region.isPresent()) {
            regionService.deleteRegion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
