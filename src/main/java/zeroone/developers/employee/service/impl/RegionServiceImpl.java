package zeroone.developers.employee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zeroone.developers.employee.entity.Region;
import zeroone.developers.employee.exception.RegionException;
import zeroone.developers.employee.exception.ResourceNotFoundException;
import zeroone.developers.employee.repository.RegionRepository;
import zeroone.developers.employee.service.RegionService;

import java.util.List;
import java.util.Optional;
/**
 * Implementation of the RegionService interface for managing regions.
 * This service handles CRUD operations for Region entities.
 */
@Service
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    /**
     * Constructor with dependency injection for RegionRepository.
     *
     * @param regionRepository the repository for Region operations
     */
    @Autowired
    public RegionServiceImpl(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }



    /**
     * Retrieve all regions.
     *
     * @return a list of all regions
     */
    @Override
    public List<Region> findAllRegions() {
        return regionRepository.findAll();
    }



    /**
     * Retrieve a region by ID.
     *
     * @param id the ID of the region
     * @return an Optional containing the region if found
     * @throws ResourceNotFoundException if the region is not found with the given ID
     */
    @Override
    public Optional<Region> findRegionById(Long id) {
        return Optional.ofNullable(regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Region not found with id " + id)));
    }


    /**
     * Save a new region.
     *
     * @param region the region to save
     * @return the saved region
     * @throws RegionException if the region name is null or already exists
     */
    @Override
    public Region saveRegion(Region region) throws RegionException{
        if (region.getName() == null) {
            throw new RegionException("Region name must not be null");
        }
        // name mavjudligini tekshirish
        boolean exists = regionRepository.existsByName(region.getName());
        if (exists) {
            throw new RegionException("Organization with this name already exists");
        }
        return regionRepository.save(region);
    }



    /**
     * Update an existing region's details.
     *
     * @param id the ID of the region to update
     * @param regionDetails the new region details
     * @return the updated region
     * @throws ResourceNotFoundException if the region is not found with the given ID
     * @throws RegionException if the region data is invalid
     */
    @Override
    public Region updateRegion(Long id, Region regionDetails) throws RegionException{
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Region not found with id " + id));

        // update region details
        region.setName(regionDetails.getName()); // Update region name
        return regionRepository.save(region);
    }



    /**
     * Delete a region by ID.
     *
     * @param id the ID of the region to delete
     * @throws ResourceNotFoundException if the region is not found with the given ID
     */
    @Override
    public void deleteRegion(Long id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Region not found with id " + id));

        regionRepository.delete(region);
    }
}
