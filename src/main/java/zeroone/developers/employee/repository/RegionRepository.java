package zeroone.developers.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zeroone.developers.employee.entity.Region;

public interface RegionRepository extends BaseRepository<Region,Long> {


    @Query(value = "select count(*) > 0 from region r where r.name = :newName", nativeQuery = true)
    boolean existsByName(@Param("newName") String newName);




}
