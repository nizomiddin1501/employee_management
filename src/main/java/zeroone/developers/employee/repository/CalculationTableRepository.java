package zeroone.developers.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import zeroone.developers.employee.entity.CalculationTable;

import java.util.List;

public interface CalculationTableRepository extends JpaRepository<CalculationTable,Long> {


    //native queries

    //1. So'ralgan oyda n ish stavkasidan ko'p ishlagan bir xil pinfl larning umumiy ish stavkasi
    @Query("select e.pinfl, sum(c.amount) from CalculationTable c " +
            "join Employee e ON c.employee.id = e.id " +
            "where month(c.date) = :month " +
            "group by e.pinfl " +
            "having sum(c.amount) > :threshold")
    List<Object[]> findEmployeesWithHigherSalary(@Param("month") int month, @Param("threshold") double threshold);


    //2. So'ralgan oyda turli regionda ishlagan bir xil pinfl larning umumiy organizatsiya soni va umumiy oylik ish haqisi
    @Query("select e.pinfl, count(distinct e.organization.id), sum(c.amount) from CalculationTable c " +
            "join Employee e ON c.employee.id = e.id " +
            "where month(c.date) = :month " +
            "group by e.pinfl")
    List<Object[]> findEmployeesByRegion(@Param("month") int month);


    //3. So'ralgan oyda berilgan organizatsiyaga tegishli bo'lgan barcha organizatsiyalarning o'rtacha oylik ish haqisi
    @Query("select o.id, o.name, avg(c.amount) from CalculationTable c " +
            "join Employee e on c.employee.id = e.id " +
            "join Organization o on e.organization.id = o.id " +
            "where month(c.date) = :month and o.id = :organizationId " +
            "group by o.id, o.name")
    List<Object[]> findAverageSalaryByOrganization(@Param("month") int month, @Param("organizationId") Long organizationId);


    //4. So'ralgan oyda ish haqi (salary) va otpusk (vacation) olgan employeelarning malumotlari
    @Query("select e, c.amount from CalculationTable c " +
            "join Employee e ON c.employee.id = e.id " +
            "where month(c.date) = :month and (c.calculationType = 'SALARY' or c.calculationType = 'VACATION')")
    List<Object[]> findEmployeesWithSalariesAndVacations(@Param("month") int month);


}
