package zeroone.developers.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zeroone.developers.employee.entity.Organization;


public interface OrganizationRepository extends BaseRepository<Organization,Long> {


    /**
     * Check if an organization exists with the specified name.
     *
     * This method queries the database to determine if there are any organization records
     * in the organization table that match the provided name.
     *
     * @param newName the name of the organization to check
     * @return true if an organization with the specified name exists, false otherwise
     */
    @Query(value = "select count(*) > 0 from organization o where o.name = :newName", nativeQuery = true)
    boolean existsByName(@Param("newName") String newName);

}
