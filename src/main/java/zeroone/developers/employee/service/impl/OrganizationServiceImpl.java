package zeroone.developers.employee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zeroone.developers.employee.entity.Organization;
import zeroone.developers.employee.exception.ResourceNotFoundException;
import zeroone.developers.employee.repository.OrganizationRepository;
import zeroone.developers.employee.service.OrganizationService;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }


    @Override
    public List<Organization> findAllOrganizations() {
        return organizationRepository.findAll();
    }

    @Override
    public Optional<Organization> findOrganizationById(Long id) {
        return Optional.ofNullable(organizationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id " + id)));
    }

    @Override
    public Organization saveOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }

    //update
    @Override
    public Organization updateOrganization(Long id, Organization organizationDetails) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id " + id));

        // update organization details
        organization.setName(organizationDetails.getName());
        organization.setRegion(organizationDetails.getRegion()); // Update region reference
        organization.setParent(organizationDetails.getParent()); // Update parent organization

        return organizationRepository.save(organization);
    }

    @Override
    public void deleteOrganization(Long id) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id " + id));

        organizationRepository.delete(organization);
    }
}
