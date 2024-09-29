package zeroone.developers.employee.service;

import zeroone.developers.employee.entity.Organization;
import zeroone.developers.employee.exception.OrganizationException;
import zeroone.developers.employee.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
/**
 * Service interface for managing organizations.
 * Provides methods for performing CRUD operations on organizations.
 */
public interface OrganizationService {



    /**
     * Retrieve all organizations.
     *
     * @return a list of all organizations
     */
    List<Organization> findAllOrganizations();



    /**
     * Retrieve an organization by its ID.
     *
     * @param id the ID of the organization
     * @return an Optional containing the organization if found
     * @throws ResourceNotFoundException if the organization is not found
     */
    Optional<Organization> findOrganizationById(Long id) throws ResourceNotFoundException;


    /**
     * Save a new organization.
     *
     * @param organization the organization to save
     * @return the saved organization
     * @throws OrganizationException if the organization data is invalid
     */
    Organization saveOrganization(Organization organization) throws OrganizationException;


    /**
     * Update an existing organization.
     *
     * @param id the ID of the organization to update
     * @param organizationDetails the new organization details
     * @return the updated organization
     * @throws ResourceNotFoundException if the organization is not found
     */
    Organization updateOrganization(Long id, Organization organizationDetails) throws ResourceNotFoundException;


    /**
     * Delete an organization by their ID.
     *
     * @param id the ID of the organization to delete
     * @throws ResourceNotFoundException if the organization is not found
     */
    void deleteOrganization(Long id) throws ResourceNotFoundException;









}
