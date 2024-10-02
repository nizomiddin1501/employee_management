package zeroone.developers.employee.service;

import zeroone.developers.employee.entity.Organization;
import zeroone.developers.employee.exception.EmployeeException;
import zeroone.developers.employee.exception.OrganizationException;
import zeroone.developers.employee.exception.ResourceNotFoundException;
import zeroone.developers.employee.payload.OrganizationDto;

import java.util.List;
import java.util.Optional;
/**
 * Service interface for managing organizations.
 * Provides methods for performing CRUD operations on organizations.
 */
public interface OrganizationService {



    /**
     * Retrieve all organization records as DTOs.
     *
     * This method retrieves all organization entities from the database,
     * converts them to OrganizationDto objects, and returns the list of DTOs.
     *
     * @return a list of OrganizationDto representing all organization records
     */
    List<OrganizationDto> findAllOrganizations();



    /**
     * Retrieve an organization by their ID.
     *
     * This method fetches the organization data by ID and returns it as a DTO.
     *
     * @param id the ID of the organization
     * @return an Optional containing the organization as a DTO if found, otherwise empty
     * @throws ResourceNotFoundException if the organization with the given ID does not exist
     */
    Optional<OrganizationDto> findOrganizationById(Long id) throws ResourceNotFoundException;


    /**
     * Save a new organization record.
     *
     * This method saves a new organization using the information provided in the OrganizationDto
     * and returns the saved organization as an OrganizationDto.
     *
     * @param organizationDto the DTO containing the organization information to be saved
     * @return the saved organization as a DTO
     * @throws EmployeeException if the organization data is invalid
     */
    OrganizationDto saveOrganization(OrganizationDto organizationDto) throws OrganizationException;


    /**
     * Update an existing organization record.
     *
     * This method updates the details of an organization based on the provided organization ID
     * and the new details contained in the OrganizationDto. It returns the updated organization
     * as an OrganizationDto.
     *
     * @param id the ID of the organization to be updated
     * @param organizationDto the new details for the organization
     * @return the updated organization as a DTO
     * @throws ResourceNotFoundException if the organization is not found with the given ID
     */
    OrganizationDto updateOrganization(Long id, OrganizationDto organizationDto) throws ResourceNotFoundException;


    /**
     * Delete an organization by their ID.
     *
     * This method finds the organization by their ID and removes the organization from the database.
     *
     * @param id the ID of the organization to delete
     * @throws ResourceNotFoundException if the organization is not found
     */
    void deleteOrganization(Long id) throws ResourceNotFoundException;









}
