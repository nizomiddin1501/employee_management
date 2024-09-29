package zeroone.developers.employee.service;
import zeroone.developers.employee.entity.Region;
import zeroone.developers.employee.exception.RegionException;
import zeroone.developers.employee.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
/**
 * Service interface for managing regions.
 * Provides methods for performing CRUD operations on regions.
 */
public interface RegionService {


    /**
     * Retrieve all regions.
     *
     * @return a list of all regions
     */
    List<Region> findAllRegions();


    /**
     * Retrieve a region by its ID.
     *
     * @param id the ID of the region
     * @return an Optional containing the region if found
     * @throws ResourceNotFoundException if the region is not found
     */
    Optional<Region> findRegionById(Long id) throws ResourceNotFoundException;


    /**
     * Save a new region.
     *
     * @param region the region to save
     * @return the saved region
     * @throws RegionException if the region data is invalid
     */
    Region saveRegion(Region region) throws RegionException;


    /**
     * Update an existing region.
     *
     * @param id the ID of the region to update
     * @param regionDetails the new region details
     * @return the updated region
     * @throws ResourceNotFoundException if the region is not found
     */
    Region updateRegion(Long id, Region regionDetails) throws ResourceNotFoundException;


    /**
     * Delete a region by its ID.
     *
     * @param id the ID of the region to delete
     * @throws ResourceNotFoundException if the region is not found
     */
    void deleteRegion(Long id) throws ResourceNotFoundException;








}
