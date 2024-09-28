package zeroone.developers.employee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zeroone.developers.employee.entity.Region;
import zeroone.developers.employee.exception.ResourceNotFoundException;
import zeroone.developers.employee.repository.RegionRepository;
import zeroone.developers.employee.service.RegionService;

import java.util.List;
import java.util.Optional;

@Service
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Autowired
    public RegionServiceImpl(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    //get all regions
    @Override
    public List<Region> findAllRegions() {
        return regionRepository.findAll();
    }

    //get region by ID
    @Override
    public Optional<Region> findRegionById(Long id) {
        return Optional.ofNullable(regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Region not found with id " + id)));
    }

    //save new region
    @Override
    public Region saveRegion(Region region) {
        return regionRepository.save(region);
    }

    //update region
    @Override
    public Region updateRegion(Long id, Region regionDetails) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Region not found with id " + id));

        // update region details
        region.setName(regionDetails.getName()); // Update region name
        return regionRepository.save(region);
    }

    //delete region
    @Override
    public void deleteRegion(Long id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Region not found with id " + id));

        regionRepository.delete(region);
    }
}
