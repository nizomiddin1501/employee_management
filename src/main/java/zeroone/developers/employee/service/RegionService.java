package zeroone.developers.employee.service;
import zeroone.developers.employee.entity.Region;
import java.util.List;
import java.util.Optional;
public interface RegionService {


    List<Region> findAllRegions();
    Optional<Region> findRegionById(Long id);
    Region saveRegion(Region region);
    Region updateRegion(Long id, Region regionDetails);
    void deleteRegion(Long id);








}
