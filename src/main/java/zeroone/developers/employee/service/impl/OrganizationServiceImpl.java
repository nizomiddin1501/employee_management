package zeroone.developers.employee.service.impl;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zeroone.developers.employee.entity.Organization;
import zeroone.developers.employee.exception.OrganizationException;
import zeroone.developers.employee.exception.ResourceNotFoundException;
import zeroone.developers.employee.payload.OrganizationDto;
import zeroone.developers.employee.repository.OrganizationRepository;
import zeroone.developers.employee.service.OrganizationService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the OrganizationService interface for managing organizations.
 * This service handles CRUD operations for Organization entities.
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private ModelMapper modelMapper;

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
     * Retrieve all organization records as DTOs.
     * <p>
     * This method retrieves all organization entities from the database,
     * converts them to DTOs, and returns the list of OrganizationDto objects.
     *
     * @return a list of OrganizationDto representing all organizations
     */
    @Override
    public List<OrganizationDto> findAllOrganizations() {
        List<Organization> organizations = organizationRepository.findAll();

        // Convert the list of Organization entities to OrganizationDto
        return organizations.stream()
                .map(this::organizationToDto)
                .collect(Collectors.toList());
    }


    /**
     * Retrieve an organization by ID.
     * <p>
     * This method fetches the organization entity by ID, maps it to a DTO, and returns it.
     *
     * @param id the ID of the organization
     * @return an Optional containing the organization as a DTO if found
     * @throws ResourceNotFoundException if the organization is not found with the given ID
     */
    @Override
    public Optional<OrganizationDto> findOrganizationById(Long id) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id " + id));

        // Convert Organization entity to OrganizationDto
        OrganizationDto organizationDto = organizationToDto(organization);
        return Optional.ofNullable(organizationDto);
    }


    /**
     * Save a new organization or update an existing one.
     * <p>
     * This method validates that the organization's name is not null,
     * and checks if an organization with the same name already exists
     * in the database.
     *
     * @param organizationDto the DTO containing the organization information to be saved
     * @return the saved organization object
     * @throws OrganizationException if the organization data is invalid,
     *                               or if an organization with the same name already exists
     */
    @Override
    public OrganizationDto saveOrganization(@Valid OrganizationDto organizationDto) throws OrganizationException {
        // 1. Convert DTO to entity
        Organization organization = dtoToOrganization(organizationDto);

        // 2. Perform business checks on the entity
        if (organization.getName() == null) {
            throw new OrganizationException("Organization name and address must not be null");
        }

        // 3. Checking that the name column do not exist
        boolean exists = organizationRepository.existsByName(organization.getName());
        if (exists) {
            throw new OrganizationException("Organization with this name already exists");
        }

        // 4. Save Organization
        Organization savedOrganization = organizationRepository.save(organization);

        // 5. Convert the saved Organization to DTO and return
        return organizationToDto(savedOrganization);
    }


    /**
     * Update an existing organization's details.
     *
     * This method updates the organization's details based on the provided DTO.
     * It validates that the organization exists, and updates the necessary fields
     * before saving the changes to the database.
     *
     * @param id the ID of the organization to update
     * @param organizationDto the DTO containing the new organization details
     * @return the updated organization as a DTO
     * @throws ResourceNotFoundException if the organization is not found with the given ID
     * @throws OrganizationException if the organization data is invalid
     */
    @Override
    public OrganizationDto updateOrganization(Long id, OrganizationDto organizationDto) throws OrganizationException {
        Organization existingOrganization = organizationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id " + id));

        // Conversion DTO to entity
        Organization organizationDetails = dtoToOrganization(organizationDto);

        // Update organization details
        existingOrganization.setName(organizationDetails.getName());
        existingOrganization.setRegion(organizationDetails.getRegion()); // Update region reference
        existingOrganization.setParent(organizationDetails.getParent()); // Update parent organization

        // Save updated organization
        Organization updatedOrganization = organizationRepository.save(existingOrganization);

        // Convert updated organization entity to DTO and return
        return organizationToDto(updatedOrganization);
    }


    /**
     * Delete an organization by their ID.
     *
     * This method looks up the organization by their ID. If the organization is found, it is deleted
     * from the database. If not, a ResourceNotFoundException is thrown.
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


    // DTO to Entity conversion
    public Organization dtoToOrganization(OrganizationDto organizationDto) {
        return modelMapper.map(organizationDto, Organization.class);
    }

    // Entity to DTO conversion
    public OrganizationDto organizationToDto(Organization organization) {
        return modelMapper.map(organization, OrganizationDto.class);
    }


}
