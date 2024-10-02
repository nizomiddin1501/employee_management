package zeroone.developers.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zeroone.developers.employee.entity.Employee;


public interface EmployeeRepository extends BaseRepository<Employee,Long>{



    /**
     * Check if an employee exists with the specified first and last name.
     *
     * This method queries the database to determine if there are any employee records
     * in the employee table that match the provided first name and last name.
     *
     * @param firstName the first name of the employee to check
     * @param lastName the last name of the employee to check
     * @return true if an employee with the specified first and last name exists, false otherwise
     */
    @Query(value = "select count(*) > 0 from employee e where e.first_name = :firstName and e.last_name = :lastName", nativeQuery = true)
    boolean existsByFirstNameAndLastName(@Param("firstName") String firstName,
                                         @Param("lastName") String lastName);

}
