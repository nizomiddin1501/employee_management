package zeroone.developers.employee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zeroone.developers.employee.entity.CalculationTable;
import zeroone.developers.employee.exception.ResourceNotFoundException;
import zeroone.developers.employee.repository.CalculationTableRepository;
import zeroone.developers.employee.service.CalculationTableService;

import java.util.List;
import java.util.Optional;

@Service
public class CalculationTableServiceImpl implements CalculationTableService {

    private final CalculationTableRepository calculationTableRepository;

    @Autowired
    public CalculationTableServiceImpl(CalculationTableRepository calculationTableRepository) {
        this.calculationTableRepository = calculationTableRepository;
    }


    //jpa native query methods

    //get n dan yuqori ish stavkasi
    @Override
    public List<Object[]> getEmployeesWithHigherSalary(int month, double threshold) {
        return calculationTableRepository.findEmployeesWithHigherSalary(month, threshold);
    }

    //get ushbu oyda turli region da ishlagan bir xil pinfl larning organizatsiya soni va umumiy oyligi
    @Override
    public List<Object[]> getEmployeesByRegion(int month) {
        return calculationTableRepository.findEmployeesByRegion(month);
    }

    //ushbu oydagi berilgan organizatsiyaga tegishli barcha organizatsiyalarning urtacha oyligi
    @Override
    public List<Object[]> getAverageSalaryByOrganization(int month, Long organizationId) {
        return calculationTableRepository.findAverageSalaryByOrganization(month, organizationId);
    }

    //ushbu oyda oylik(salary) olgan va otpuska(vacation) olgan ishchilarning malumotlari
    @Override
    public List<Object[]> getEmployeesWithSalariesAndVacations(int month) {
        return calculationTableRepository.findEmployeesWithSalariesAndVacations(month);
    }


    //crud methods
    @Override
    public List<CalculationTable> findAllCalculations() {
        return calculationTableRepository.findAll();
    }

    @Override
    public Optional<CalculationTable> findCalculationById(Long id) {
        return Optional.ofNullable(calculationTableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Calculation not found with id " + id)));
    }

    @Override
    public CalculationTable saveCalculation(CalculationTable calculationTable) {
        return calculationTableRepository.save(calculationTable);
    }

    @Override
    public CalculationTable updateCalculationTable(Long id, CalculationTable calculationTableDetails) {
        CalculationTable calculationTable = calculationTableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CalculationTable not found with id: " + id));

        // Updating the fields
        calculationTable.setAmount(calculationTableDetails.getAmount());
        calculationTable.setRate(calculationTableDetails.getRate());
        calculationTable.setDate(calculationTableDetails.getDate());
        calculationTable.setEmployee(calculationTableDetails.getEmployee());
        calculationTable.setOrganization(calculationTableDetails.getOrganization());
        calculationTable.setCalculationType(calculationTableDetails.getCalculationType());

        return calculationTableRepository.save(calculationTable);
    }

    @Override
    public void deleteCalculation(Long id) {
        calculationTableRepository.deleteById(id);
    }


}
