package zeroone.developers.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zeroone.developers.employee.entity.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region,Long> {

}
