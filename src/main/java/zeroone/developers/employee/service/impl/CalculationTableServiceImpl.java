package zeroone.developers.employee.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zeroone.developers.employee.entity.CalculationTable;
import zeroone.developers.employee.exception.CalculationTableException;
import zeroone.developers.employee.exception.ResourceNotFoundException;
import zeroone.developers.employee.payload.CalculationTableDto;
import zeroone.developers.employee.repository.CalculationTableRepository;
import zeroone.developers.employee.service.CalculationTableService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the CalculationTableService interface.
 * Provides CRUD operations and custom queries for CalculationTable entities.
 */
@Service
public class CalculationTableServiceImpl implements CalculationTableService {

    @Autowired
    private ModelMapper modelMapper;

    private final CalculationTableRepository calculationTableRepository;

    /**
     * Constructor with dependency injection for CalculationTableRepository.
     *
     * @param calculationTableRepository the repository for CalculationTable operations
     */
    @Autowired
    public CalculationTableServiceImpl(CalculationTableRepository calculationTableRepository) {
        this.calculationTableRepository = calculationTableRepository;
    }


    //jpa native query methods

    /**
     * Retrieves a list of employees with salary higher than a given threshold for a specific month.
     *
     * @param month      the month for which the data is being retrieved
     * @param threshold  the salary threshold to filter employees
     * @return a list of employees with higher salary in the form of Object arrays
     */
    @Override
    public List<Object[]> getEmployeesWithHigherSalary(int month, double threshold) {
        return calculationTableRepository.findEmployeesWithHigherSalary(month, threshold);
    }


    /**
     * Retrieves the number of organizations and total salary for employees with the same PINFL
     * who worked in different regions for a specific month.
     *
     * @param month the month for which the data is being retrieved
     * @return a list of employees' data by region in the form of Object arrays
     */
    @Override
    public List<Object[]> getEmployeesByRegion(int month) {
        return calculationTableRepository.findEmployeesByRegion(month);
    }


    /**
     * Retrieves the average salary for all organizations related to a given organization in a specific month.
     *
     * @param month          the month for which the data is being retrieved
     * @param organizationId the ID of the organization for which the data is being retrieved
     * @return a list of average salaries by organization in the form of Object arrays
     */
    @Override
    public List<Object[]> getAverageSalaryByOrganization(int month, Long organizationId) {
        return calculationTableRepository.findAverageSalaryByOrganization(month, organizationId);
    }


    /**
     * Retrieves information about employees who received salaries and took vacations in a specific month.
     *
     * @param month the month for which the data is being retrieved
     * @return a list of employees' data with salaries and vacations in the form of Object arrays
     */
    @Override
    public List<Object[]> getEmployeesWithSalariesAndVacations(int month) {
        return calculationTableRepository.findEmployeesWithSalariesAndVacations(month);
    }


    //crud methods

    /**
     * Retrieve all calculationTable records as DTOs.
     *
     * This method retrieves all calculationTable entities from the database,
     * converts them to DTOs, and returns the list of CalculationTableDto objects.
     *
     * @return a list of CalculationTableDto representing all calculationTables
     */
    @Override
    public List<CalculationTableDto> findAllCalculations() {
        List<CalculationTable> calculationTables = calculationTableRepository.findAll();
        return calculationTables.stream()
                .map(this::calculationTableToDto)
                .collect(Collectors.toList());
    }


    /**
     * Retrieve a calculationTable by ID.
     *
     * This method fetches the calculationTable entity by ID, maps it to a DTO, and returns it.
     *
     * @param id the ID of the calculationTable
     * @return an Optional containing the calculationTable as a DTO if found
     * @throws ResourceNotFoundException if the calculationTable is not found with the given ID
     */
    @Override
    public Optional<CalculationTableDto> findCalculationById(Long id) throws ResourceNotFoundException {
        CalculationTable calculationTable = calculationTableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Calculation not found with id " + id));
        return Optional.of(calculationTableToDto(calculationTable));
    }

 /// bunga alohida doc yoz
    /**
     * Save a new calculation record.
     *
     * This method saves a new calculationTable based on the provided DTO.
     * It validates that the amount is positive and that an employee is associated
     * with the calculation before saving the new record to the database.
     *
     * @param calculationTableDto the DTO containing the calculationTable details to save
     * @return the saved calculationTable as a DTO
     * @throws CalculationTableException if the calculationTable data is invalid,
     *         such as a non-positive amount or missing employee
     */
    @Override
    public CalculationTableDto saveCalculation(CalculationTableDto calculationTableDto) throws CalculationTableException {
        CalculationTable calculationTable = dtoToCalculationTable(calculationTableDto);
        if (calculationTableRepository.existsInvalidAmount()) {
            throw new CalculationTableException("Calculation amount must be positive");
        }
        if (!calculationTableRepository.existsByEmployeeId(calculationTable.getEmployee().getId())) {
            throw new CalculationTableException("Employee must be provided for the calculation");
        }
        CalculationTable savedCalculationTable = calculationTableRepository.save(calculationTable);
        return calculationTableToDto(savedCalculationTable);
    }


    /**
     * Update an existing calculationTable's details.
     *
     * This method updates the calculationTable's details based on the provided DTO.
     * It validates that the calculationTable exists, and updates the necessary fields
     * before saving the changes to the database.
     *
     * @param id the ID of the calculationTable to update
     * @param calculationTableDto the DTO containing the new calculationTable details
     * @return the updated calculationTable as a DTO
     * @throws ResourceNotFoundException if the calculationTable is not found with the given ID
     * @throws CalculationTableException if the calculationTable data is invalid
     */
    @Override
    public CalculationTableDto updateCalculationTable(Long id, CalculationTableDto calculationTableDto) throws CalculationTableException{
        CalculationTable existingCalculationTable = calculationTableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CalculationTable not found with id: " + id));

        // Conversion DTO to entity
        CalculationTable calculationTableDetails = dtoToCalculationTable(calculationTableDto);

        // Update calculationTable details
        existingCalculationTable.setAmount(calculationTableDetails.getAmount());
        existingCalculationTable.setRate(calculationTableDetails.getRate());
        existingCalculationTable.setDate(calculationTableDetails.getDate());
        existingCalculationTable.setEmployee(calculationTableDetails.getEmployee());
        existingCalculationTable.setOrganization(calculationTableDetails.getOrganization());
        existingCalculationTable.setCalculationType(calculationTableDetails.getCalculationType());

        // Save updated calculationTable
        CalculationTable updatedCalculationTable = calculationTableRepository.save(existingCalculationTable);

        // Convert updated calculationTable entity to DTO and return
        return calculationTableToDto(updatedCalculationTable);
    }


    /**
     * Delete an calculationTable by their ID.
     *
     * This method looks up the calculationTable by their ID. If the calculationTable is found, it is deleted
     * from the database. If not, a ResourceNotFoundException is thrown.
     *
     * @param id the ID of the calculationTable to delete
     * @throws ResourceNotFoundException if the calculationTable is not found with the given ID
     */
    @Override
    public void deleteCalculation(Long id) {
        CalculationTable calculation = calculationTableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Calculation not found with id " + id));
        calculationTableRepository.deleteById(id);
    }



    // DTO to Entity conversion
    public CalculationTable dtoToCalculationTable(CalculationTableDto calculationTableDto) {
        return modelMapper.map(calculationTableDto, CalculationTable.class);
    }

    // Entity to DTO conversion
    public CalculationTableDto calculationTableToDto(CalculationTable calculationTable) {
        return modelMapper.map(calculationTable, CalculationTableDto.class);
    }



}
