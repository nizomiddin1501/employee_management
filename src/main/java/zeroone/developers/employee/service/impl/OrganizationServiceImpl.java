package zeroone.developers.employee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zeroone.developers.employee.entity.Organization;
import zeroone.developers.employee.exception.OrganizationException;
import zeroone.developers.employee.exception.ResourceNotFoundException;
import zeroone.developers.employee.repository.OrganizationRepository;
import zeroone.developers.employee.service.OrganizationService;

import java.util.List;
import java.util.Optional;
/**
 * Implementation of the OrganizationService interface for managing organizations.
 * This service handles CRUD operations for Organization entities.
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    /**
     * Constructor with dependency injection for OrganizationRepository.
     *
     * @param organizationRepository the repository for Organization operations
     */
    @Autowired
    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }


    /**
     * Retrieve all organizations.
     *
     * @return a list of all organizations
     */
    @Override
    public List<Organization> findAllOrganizations() {
        return organizationRepository.findAll();
    }


    /**
     * Retrieve an organization by ID.
     *
     * @param id the ID of the organization
     * @return an Optional containing the organization if found
     * @throws ResourceNotFoundException if the organization is not found with the given ID
     */
    @Override
    public Optional<Organization> findOrganizationById(Long id) {
        return Optional.ofNullable(organizationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id " + id)));
    }


    /**
     * Save a new organization.
     *
     * @param organization the organization to save
     * @return the saved organization
     * @throws OrganizationException if the organization data is invalid
     */
    @Override
    public Organization saveOrganization(Organization organization) throws OrganizationException{
        if (organization.getName() == null) {
            throw new OrganizationException("Organization name and address must not be null");
        }
        return organizationRepository.save(organization);
    }


    /**
     * Update an existing organization's details.
     *
     * @param id the ID of the organization to update
     * @param organizationDetails the new organization details
     * @return the updated organization
     * @throws ResourceNotFoundException if the organization is not found with the given ID
     * @throws OrganizationException if the organization data is invalid
     */
    @Override
    public Organization updateOrganization(Long id, Organization organizationDetails) throws OrganizationException{
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id " + id));

        // update organization details
        organization.setName(organizationDetails.getName());
        organization.setRegion(organizationDetails.getRegion()); // Update region reference
        organization.setParent(organizationDetails.getParent()); // Update parent organization

        return organizationRepository.save(organization);
    }


    /**
     * Delete an organization by ID.
     *
     * @param id the ID of the organization to delete
     * @throws ResourceNotFoundException if the organization is not found with the given ID
     */
    @Override
    public void deleteOrganization(Long id) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id " + id));

        organizationRepository.delete(organization);
    }
}
