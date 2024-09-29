package zeroone.developers.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import zeroone.developers.employee.entity.CalculationTable;

import java.util.List;
/**
 * Repository interface for accessing CalculationTable entities.
 * It provides methods to perform CRUD operations and custom queries.
 */
public interface CalculationTableRepository extends BaseRepository<CalculationTable,Long> {



    //native queries

    /**
     * Retrieves the total salary amount for employees who worked more than the specified threshold
     * in a given month.
     *
     * @param month the month to filter records by
     * @param threshold the minimum total salary amount to filter employees
     * @return a list of Object arrays containing the pinfl of the employee and their total salary amount
     */
    @Query(value = "select e.pinfl, sum(c.amount) " +
            "from calculation_table c " +
            "join employee e on c.employee_id = e.id " +
            "where extract(month from c.date) = :month " +
            "group by e.pinfl " +
            "having sum(c.amount) > :threshold",
            nativeQuery = true)
    List<Object[]> findEmployeesWithHigherSalary(@Param("month") int month, @Param("threshold") double threshold);


    /**
     * Retrieves the number of unique organizations and total salary for employees who worked in
     * various regions during a given month.
     *
     * @param month the month to filter records by
     * @return a list of Object arrays containing the pinfl of the employee, the count of unique organizations,
     *         and their total salary amount
     */
    @Query(value = "select e.pinfl, count(distinct e.organization_id), sum(c.amount) " +
            "from calculation_table c " +
            "join employee e on c.employee_id = e.id " +
            "where extract(month from c.date) = :month " +
            "group by e.pinfl", nativeQuery = true)
    List<Object[]> findEmployeesByRegion(@Param("month") int month);



    /**
     * Retrieves the average salary of employees belonging to a specific organization for a given month.
     *
     * @param month the month to filter records by
     * @param organizationId the ID of the organization to filter employees
     * @return a list of Object arrays containing the organization ID, name, and the average salary amount
     */
    @Query(value = "select o.id, o.name, avg(c.amount) " +
            "from calculation_table c " +
            "join employee e on c.employee_id = e.id " +
            "join organization o on e.organization_id = o.id " +
            "where extract(month from c.date) = :month and o.id = :organizationId " +
            "group by o.id, o.name", nativeQuery = true)
    List<Object[]> findAverageSalaryByOrganization(@Param("month") int month, @Param("organizationId") Long organizationId);



    /**
     * Retrieves the details of employees who received a salary or vacation for a given month.
     *
     * @param month the month to filter records by
     * @return a list of Object arrays containing employee details and their salary amount
     */
    @Query(value = "select e.*, c.amount from calculation_table c " +
            "join employee e on c.employee_id = e.id " +
            "where extract(month from c.date) = :month " +
            "and (c.calculation_type = 'SALARY' OR c.calculation_type = 'VACATION')",
            nativeQuery = true)
    List<Object[]> findEmployeesWithSalariesAndVacations(@Param("month") int month);


}
