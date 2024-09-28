package zeroone.developers.employee.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.AssertFalse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zeroone.developers.employee.entity.Organization;
import zeroone.developers.employee.service.OrganizationService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {


    private final OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }


    //read all
    @Operation(summary = "Get all Organizations", description = "Retrieve a list of all organizations.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of organizations.")
    @GetMapping
    public List<Organization> getAllOrganizations() {
        return organizationService.findAllOrganizations();
    }

    //read by ID
    @Operation(summary = "Get Organization by ID", description = "Retrieve an organization by their unique identifier.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the organization.")
    @ApiResponse(responseCode = "404", description = "Organization not found.")
    @GetMapping("/{id}")
    public ResponseEntity<Organization> getOrganizationById(@PathVariable Long id) {
        Optional<Organization> organization = organizationService.findOrganizationById(id);
        return organization.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //create
    @Operation(summary = "Create a new Organization", description = "Create a new organization record.")
    @ApiResponse(responseCode = "201", description = "Organization created successfully.")
    @PostMapping
    public ResponseEntity<Organization> createOrganization(@RequestBody Organization organization) {
        Organization saveOrganization = organizationService.saveOrganization(organization);
        return new ResponseEntity<>(saveOrganization, HttpStatus.CREATED);
    }

    //update
    @Operation(summary = "Update organization", description = "Update the details of an existing organization.")
    @ApiResponse(responseCode = "200", description = "Organization updated successfully")
    @ApiResponse(responseCode = "404", description = "Organization not found")
    @PutMapping("/{id}")
    public ResponseEntity<Organization> updateOrganization(
            @PathVariable Long id,
            @RequestBody Organization organizationDetails) {
        Optional<Organization> organization = organizationService.findOrganizationById(id);
        if (organization.isPresent()) {
            Organization updatedOrganization = organizationService.updateOrganization(id, organizationDetails);
            return new ResponseEntity<>(updatedOrganization, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //delete
    @Operation(summary = "Delete Organization", description = "Delete an organization by its ID.")
    @ApiResponse(responseCode = "204", description = "Organization deleted successfully.")
    @ApiResponse(responseCode = "404", description = "Organization not found.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
        Optional<Organization> organization = organizationService.findOrganizationById(id);
        if (organization.isPresent()) {
            organizationService.deleteOrganization(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }











}
