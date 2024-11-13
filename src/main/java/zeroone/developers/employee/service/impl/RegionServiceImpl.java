package zeroone.developers.employee.service.impl;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zeroone.developers.employee.entity.Region;
import zeroone.developers.employee.exception.RegionException;
import zeroone.developers.employee.exception.ResourceNotFoundException;
import zeroone.developers.employee.payload.RegionDto;
import zeroone.developers.employee.repository.RegionRepository;
import zeroone.developers.employee.service.RegionService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the RegionService interface for managing regions.
 * This service handles CRUD operations for Region entities.
 */
@Service
public class RegionServiceImpl implements RegionService {


    @Autowired
    private ModelMapper modelMapper;

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
     * Retrieve all region records as DTOs.
     *
     * This method retrieves all region entities from the database,
     * converts them to DTOs, and returns the list of RegionDto objects.
     *
     * @return a list of RegionDto representing all regions
     */
    @Override
    public List<RegionDto> findAllRegions() {
        List<Region> regions = regionRepository.findAll();

        // Convert the list of Region entities to RegionDto
        return regions.stream()
                .map(this::regionToDto)
                .collect(Collectors.toList());
    }


    /**
     * Retrieve a region by ID.
     *
     * This method fetches the region entity by ID, maps it to a DTO, and returns it.
     *
     * @param id the ID of the region
     * @return an Optional containing the region as a DTO if found
     * @throws ResourceNotFoundException if the region is not found with the given ID
     */
    @Override
    public Optional<RegionDto> findRegionById(Long id) throws ResourceNotFoundException {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Region not found with id " + id));
        return Optional.of(regionToDto(region));
    }


    /**
     * Save a new region or update an existing one.
     *
     * This method validates that the region's name null,
     * and checks if a region with the same name already exists
     * in the database.
     *
     * @param regionDto the DTO containing the region information to be saved
     * @return the saved employee object
     * @throws RegionException if the region data is invalid,
     *                           or if a region with the same namealready exists
     */
    @Override
    public RegionDto saveRegion(@Valid RegionDto regionDto) throws RegionException {
        // 1. Convert DTO to entity
        Region region = dtoToRegion(regionDto);

        // 2. Perform business checks on the entity
        if (region.getName() == null) {
            throw new RegionException("Region name must not be null");
        }

        // 3. Checking that the name column do not exist
        boolean exists = regionRepository.existsByName(region.getName());
        if (exists) {
            throw new RegionException("Organization with this name already exists");
        }

        // 4. Save Region
        Region savedRegion = regionRepository.save(region);

        // 4. Convert the saved Region to DTO and return
        return regionToDto(savedRegion);
    }


    /**
     * Update an existing region's details.
     *
     * This method updates the region's details based on the provided DTO.
     * It validates that the region exists, and updates the necessary fields
     * before saving the changes to the database.
     *
     * @param id the ID of the region to update
     * @param regionDto the DTO containing the new region details
     * @return the updated region as a DTO
     * @throws ResourceNotFoundException if the region is not found with the given ID
     * @throws RegionException if the region data is invalid
     */
    @Override
    public RegionDto updateRegion(Long id, RegionDto regionDto) throws RegionException {
        Region existingRegion = regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Region not found with id " + id));

        // Conversion DTO to entity
        Region regionDetails = dtoToRegion(regionDto);

        // update region details
        existingRegion.setName(regionDetails.getName());

        // Save updated region
        Region updatedRegion = regionRepository.save(existingRegion);

        // Convert updated region entity to DTO and return
        return regionToDto(updatedRegion);
    }


    /**
     * Delete a region by their ID.
     *
     * This method looks up the region by their ID. If the region is found, it is deleted
     * from the database. If not, a ResourceNotFoundException is thrown.
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


    // DTO to Entity conversion
    public Region dtoToRegion(RegionDto regionDto) {
        return modelMapper.map(regionDto, Region.class);
    }

    // Entity to DTO conversion
    public RegionDto regionToDto(Region region) {
        return modelMapper.map(region, RegionDto.class);
    }


}
