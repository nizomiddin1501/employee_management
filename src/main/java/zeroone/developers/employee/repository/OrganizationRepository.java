package zeroone.developers.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zeroone.developers.employee.entity.Organization;


public interface OrganizationRepository extends BaseRepository<Organization,Long> {
}
