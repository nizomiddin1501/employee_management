package zeroone.developers.employee.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zeroone.developers.employee.exception.OrganizationException;
import zeroone.developers.employee.payload.CustomApiResponse;
import zeroone.developers.employee.payload.OrganizationDto;
import zeroone.developers.employee.service.OrganizationService;

import java.util.List;

/**
 * REST controller for managing organizations, offering endpoints for
 * creating, updating, retrieving, and deleting organization records.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/organizations")
public class OrganizationController {


    private final OrganizationService organizationService;


    /**
     * Retrieve a list of all organizations.
     * <p>
     * This method fetches all organization records and returns them as a list of OrganizationDto.
     *
     * @return a ResponseEntity containing a CustomApiResponse with the list of OrganizationDto representing all organizations
     */
    @Operation(summary = "Get all Organizations", description = "Retrieve a list of all organizations.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of organizations.")
    @GetMapping
    public ResponseEntity<CustomApiResponse<List<OrganizationDto>>> getAllOrganizations() {
        List<OrganizationDto> organizationDtos = organizationService.findAllOrganizations();
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Successfully retrieved the list of organizations.",
                true,
                organizationDtos), HttpStatus.OK);
    }


    /**
     * Retrieve an organization by their unique ID using the provided OrganizationDto.
     *
     * @param id the ID of the organization to retrieve
     * @return a ResponseEntity containing a CustomApiResponse with the OrganizationDto and
     * an HTTP status of OK
     */
    @Operation(summary = "Get Organization by ID", description = "Retrieve an organization by their unique identifier.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the organization.")
    @ApiResponse(responseCode = "404", description = "Organization not found.")
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<OrganizationDto>> getOrganizationById(@PathVariable Long id) {
        OrganizationDto organizationDto = organizationService.findOrganizationById(id)
                .orElseThrow(() -> new OrganizationException("Organization not found"));
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Successfully retrieved the organization.",
                true,
                organizationDto), HttpStatus.OK);
    }


    /**
     * Creates a new organization.
     *
     * @param organizationDto the DTO containing the organization information to be saved
     * @return a ResponseEntity containing a CustomApiResponse with the saved organization data
     */
    @Operation(summary = "Create a new Organization", description = "Create a new organization record.")
    @ApiResponse(responseCode = "201", description = "Organization created successfully.")
    @PostMapping
    public ResponseEntity<CustomApiResponse<OrganizationDto>> createOrganization(@Valid @RequestBody OrganizationDto organizationDto) {
        OrganizationDto savedOrganization = organizationService.saveOrganization(organizationDto);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Organization created successfully",
                true,
                savedOrganization), HttpStatus.CREATED);
    }


    /**
     * Update the details of an existing organization using the provided OrganizationDto.
     *
     * @param id              the ID of the organization to be updated
     * @param organizationDto the DTO containing updated organization details
     * @return a ResponseEntity containing a CustomApiResponse with the updated OrganizationDto
     */
    @Operation(summary = "Update organization", description = "Update the details of an existing organization.")
    @ApiResponse(responseCode = "200", description = "Organization updated successfully")
    @ApiResponse(responseCode = "404", description = "Organization not found")
    @PutMapping("/{id}")
    public ResponseEntity<CustomApiResponse<OrganizationDto>> updateOrganization(
            @PathVariable Long id,
            @RequestBody OrganizationDto organizationDto) {
        OrganizationDto updatedOrganization = organizationService.updateOrganization(id, organizationDto);
        return new ResponseEntity<>(new CustomApiResponse<>(
                "Room updated successfully",
                true,
                updatedOrganization), HttpStatus.OK);
}


/**
 * Delete an organization by their ID.
 *
 * @param id the ID of the organization to delete
 * @return a ResponseEntity containing a CustomApiResponse with the status of the operation
 */
@Operation(summary = "Delete Organization", description = "Delete an organization by its ID.")
@ApiResponse(responseCode = "204", description = "Organization deleted successfully.")
@ApiResponse(responseCode = "404", description = "Organization not found.")
@DeleteMapping("/{id}")
public ResponseEntity<CustomApiResponse<Void>> deleteOrganization(@PathVariable Long id) {
    organizationService.deleteOrganization(id);
    return new ResponseEntity<>(new CustomApiResponse<>(
            "Organization deleted successfully.",
            true,
            null), HttpStatus.NO_CONTENT);
   }
}


