package zeroone.developers.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zeroone.developers.employee.entity.Organization;


public interface OrganizationRepository extends BaseRepository<Organization,Long> {


    @Query(value = "select count(*) > 0 from organization o where o.name = :newName", nativeQuery = true)
    boolean existsByName(@Param("newName") String newName);

}
