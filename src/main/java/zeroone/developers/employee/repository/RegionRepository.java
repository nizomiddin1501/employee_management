package zeroone.developers.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zeroone.developers.employee.entity.Region;

public interface RegionRepository extends BaseRepository<Region,Long> {



    /**
     * Check if a region exists with the specified name.
     *
     * This method queries the database to determine if there are any region records
     * in the region table that match the provided name.
     *
     * @param newName the name of the region to check
     * @return true if a region with the specified name exists, false otherwise
     */
    @Query(value = "select count(*) > 0 from region r where r.name = :newName", nativeQuery = true)
    boolean existsByName(@Param("newName") String newName);




}
