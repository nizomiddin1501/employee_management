package zeroone.developers.employee.service;
import zeroone.developers.employee.exception.RegionException;
import zeroone.developers.employee.exception.ResourceNotFoundException;
import zeroone.developers.employee.payload.RegionDto;

import java.util.List;
import java.util.Optional;
/**
 * Service interface for managing regions.
 * Provides methods for performing CRUD operations on regions.
 */
public interface RegionService {



    /**
     * Retrieve all region records as DTOs.
     *
     * This method retrieves all region entities from the database,
     * converts them to RegionDto objects, and returns the list of DTOs.
     *
     * @return a list of RegionDto representing all region records
     */
    List<RegionDto> findAllRegions();



    /**
     * Retrieve a region by their ID.
     *
     * This method fetches the region data by ID and returns it as a DTO.
     *
     * @param id the ID of the region
     * @return an Optional containing the region as a DTO if found, otherwise empty
     * @throws ResourceNotFoundException if the region with the given ID does not exist
     */
    Optional<RegionDto> findRegionById(Long id) throws ResourceNotFoundException;



    /**
     * Save a new region record.
     *
     * This method saves a new region using the information provided in the regionDto
     * and returns the saved region as an RegionDto.
     *
     * @param regionDto the DTO containing the region information to be saved
     * @return the saved region as a DTO
     * @throws RegionException if the region data is invalid
     */
    RegionDto saveRegion(RegionDto regionDto) throws RegionException;




    /**
     * Update an existing region record.
     *
     * This method updates the details of a region based on the provided region ID
     * and the new details contained in the RegionDto. It returns the updated region
     * as an RegionDto.
     *
     * @param id the ID of the region to be updated
     * @param regionDto the new details for the region
     * @return the updated region as a DTO
     * @throws ResourceNotFoundException if the region is not found with the given ID
     */
    RegionDto updateRegion(Long id, RegionDto regionDto) throws ResourceNotFoundException;


    /**
     * Delete a region by their ID.
     *
     * This method finds the region by their ID and removes the region from the database.
     *
     * @param id the ID of the region to delete
     * @throws ResourceNotFoundException if the region is not found
     */
    void deleteRegion(Long id) throws ResourceNotFoundException;








}
