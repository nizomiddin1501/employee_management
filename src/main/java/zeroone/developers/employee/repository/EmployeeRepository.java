package zeroone.developers.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zeroone.developers.employee.entity.Employee;


public interface EmployeeRepository extends BaseRepository<Employee,Long>{



    @Query(value = "select count(*) > 0 from employee e where e.first_name = :firstName and e.last_name = :lastName", nativeQuery = true)
    boolean existsByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);


    // new query to check if a specific employee has non-null firstName and lastName
    @Query(value = "select count(*) > 0 from employee e where e.first_name is NOT NULL AND e.last_name IS NOT NULL", nativeQuery = true)
    boolean existsValidFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

}
