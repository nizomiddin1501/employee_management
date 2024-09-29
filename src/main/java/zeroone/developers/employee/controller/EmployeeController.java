package zeroone.developers.employee.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zeroone.developers.employee.entity.Employee;
import zeroone.developers.employee.service.EmployeeService;

import java.util.List;
import java.util.Optional;
/**
 * EmployeeController RESTfull API orqali xodimlar bilan bog'liq CRUD operatsiyalarini boshqaradi.
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {


    private final EmployeeService employeeService;

    /**
     * EmployeeController konstruktorida EmployeeService obyektini in'ektsiya qiladi.
     *
     * @param employeeService xodimlar bilan bog'liq CRUD operatsiyalarni amalga oshirish uchun EmployeeService obyekt.
     */
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    /**
     * Barcha xodimlarni qaytaradi.
     *
     * @return xodimlar ro'yxatini qaytaradigan ResponseEntity obyekti
     *
     * @example
     * <pre>
     * GET /api/employees
     * </pre>
     */
    @Operation(summary = "Get all Employees", description = "Retrieve a list of all employees.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of employees.")
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.findAllEmployees();
    }


    /**
     * ID bo'yicha xodimni qaytaradi.
     *
     * @param id qidirilayotgan xodimning ID raqami
     * @return topilgan xodimni qaytaradigan ResponseEntity obyekti
     *
     * @example
     * <pre>
     * GET /api/employees/{id}
     * </pre>
     */
    @Operation(summary = "Get Employee by ID", description = "Retrieve an employee by their unique identifier.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the employee.")
    @ApiResponse(responseCode = "404", description = "Employee not found.")
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.findEmployeeById(id);
        return employee.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    /**
     * Yangi xodim qo'shadi.
     *
     * @param employee saqlanadigan xodim ma'lumotlari
     * @return saqlangan xodimni qaytaradigan ResponseEntity obyekti
     *
     * @example
     * <pre>
     * POST /api/employees
     * </pre>
     */
    @Operation(summary = "Create a new Employee", description = "Create a new employee record.")
    @ApiResponse(responseCode = "201", description = "Employee created successfully.")
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }



    /**
     * Mavjud xodimni yangilaydi.
     *
     * @param id yangilanishi kerak bo'lgan xodimning ID raqami
     * @param employeeDetails yangilanadigan xodim ma'lumotlari
     * @return yangilangan xodimni qaytaradigan ResponseEntity obyekti
     *
     * @example
     * <pre>
     * PUT /api/employees/{id}
     * </pre>
     */
    @Operation(summary = "Update an existing Employee", description = "Update the details of an existing employee.")
    @ApiResponse(responseCode = "200", description = "Employee updated successfully.")
    @ApiResponse(responseCode = "404", description = "Employee not found")
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee employeeDetails) {
        Optional<Employee> employee = employeeService.findEmployeeById(id);
        if (employee.isPresent()) {
            Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    /**
     * Xodimni o'chiradi.
     *
     * @param id o'chirilishi kerak bo'lgan xodimning ID raqami
     * @return hech qanday mazmun qaytarmaydigan ResponseEntity obyekti
     *
     * @example
     * <pre>
     * DELETE /api/employees/{id}
     * </pre>
     */
    @Operation(summary = "Delete Employee", description = "Delete an employee by its ID.")
    @ApiResponse(responseCode = "204", description = "Employee deleted successfully.")
    @ApiResponse(responseCode = "404", description = "Employee not found.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.findEmployeeById(id);
        if (employee.isPresent()) {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


















}
