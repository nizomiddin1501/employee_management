package zeroone.developers.employee.service;

import zeroone.developers.employee.entity.Organization;

import java.util.List;
import java.util.Optional;

public interface OrganizationService {


    List<Organization> findAllOrganizations();
    Optional<Organization> findOrganizationById(Long id);
    Organization saveOrganization(Organization organization);
    Organization updateOrganization(Long id, Organization organizationDetails);
    void deleteOrganization(Long id);









}
