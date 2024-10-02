package zeroone.developers.employee.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertFalse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zeroone.developers.employee.entity.Organization;
import zeroone.developers.employee.payload.OrganizationDto;
import zeroone.developers.employee.service.OrganizationService;

import java.util.List;
import java.util.Optional;
/**
 * Controller for handling requests related to Organization operations.
 * This controller provides RESTful endpoints to manage organization records,
 * including creating, updating, retrieving, and deleting organization information.
 */
@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {


    private final OrganizationService organizationService;

    /**
     * Constructor for OrganizationController.
     *
     * @param organizationService the service to manage organization records
     * @Autowired automatically injects the OrganizationService bean
     */
    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }




    /**
     * Retrieve a list of all organizations.
     *
     * This method fetches all organization records and returns them as a list of OrganizationDto.
     *
     * @return a ResponseEntity containing a list of OrganizationDto representing all organizations
     */
    @Operation(summary = "Get all Organizations", description = "Retrieve a list of all organizations.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of organizations.")
    @GetMapping
    public ResponseEntity<List<OrganizationDto>> getAllOrganizations() {
        List<OrganizationDto> organizationDtos = organizationService.findAllOrganizations();
        return new ResponseEntity<>(organizationDtos, HttpStatus.OK);
    }



    /**
     * Retrieve an organization by their unique ID using the provided OrganizationDto.
     *
     * This method retrieves an organization's details based on their ID and returns
     * the corresponding OrganizationDto if found. If the organization does not exist,
     * it returns a 404 Not Found status.
     *
     * @param id the ID of the organization to retrieve
     * @return a ResponseEntity containing the OrganizationDto and an HTTP status of OK,
     *         or NOT FOUND if the organization does not exist
     */
    @Operation(summary = "Get Organization by ID", description = "Retrieve an organization by their unique identifier.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the organization.")
    @ApiResponse(responseCode = "404", description = "Organization not found.")
    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDto> getOrganizationById(@PathVariable Long id) {
        Optional<OrganizationDto> organizationDto = organizationService.findOrganizationById(id);
        return organizationDto.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    /**
     * Creates a new organization.
     *
     * This method validates the incoming organization data (received via DTO) and saves it to the database
     * if valid.
     *
     * @param organizationDto the DTO containing the organization information to be saved
     * @return a ResponseEntity containing the saved organization data
     */
    @Operation(summary = "Create a new Organization", description = "Create a new organization record.")
    @ApiResponse(responseCode = "201", description = "Organization created successfully.")
    @PostMapping
    public ResponseEntity<OrganizationDto> createOrganization(@Valid @RequestBody OrganizationDto organizationDto) {
        OrganizationDto savedOrganization = organizationService.saveOrganization(organizationDto);
        return new ResponseEntity<>(savedOrganization, HttpStatus.CREATED);
    }



    /**
     * Update the details of an existing organization using the provided OrganizationDto.
     *
     * This method accepts the organization's ID and a DTO containing updated organization details.
     * It updates the organization record if it exists and returns the updated OrganizationDto object.
     *
     * @param id the ID of the organization to be updated
     * @param organizationDto the DTO containing updated organization details
     * @return a ResponseEntity containing the updated OrganizationDto and an HTTP status of OK,
     *         or NOT FOUND if the organization does not exist
     */
    @Operation(summary = "Update organization", description = "Update the details of an existing organization.")
    @ApiResponse(responseCode = "200", description = "Organization updated successfully")
    @ApiResponse(responseCode = "404", description = "Organization not found")
    @PutMapping("/{id}")
    public ResponseEntity<OrganizationDto> updateOrganization(
            @PathVariable Long id,
            @RequestBody OrganizationDto organizationDto) {
        Optional<OrganizationDto> organizationDtoOptional = organizationService.findOrganizationById(id);
        if (organizationDtoOptional.isPresent()) {
            OrganizationDto updatedOrganization = organizationService.updateOrganization(id, organizationDto);
            return new ResponseEntity<>(updatedOrganization, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    /**
     * Delete an organization by their ID.
     *
     * This method deletes the organization record based on the given ID if it exists.
     *
     * @param id the ID of the organization to delete
     * @return a ResponseEntity with HTTP status NO CONTENT if successful,
     *         or NOT FOUND if the organization does not exist
     */
    @Operation(summary = "Delete Organization", description = "Delete an organization by its ID.")
    @ApiResponse(responseCode = "204", description = "Organization deleted successfully.")
    @ApiResponse(responseCode = "404", description = "Organization not found.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
        Optional<OrganizationDto> organizationDto = organizationService.findOrganizationById(id);
        if (organizationDto.isPresent()) {
            organizationService.deleteOrganization(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }











}
