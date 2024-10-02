package zeroone.developers.employee.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import zeroone.developers.employee.entity.Employee;
import zeroone.developers.employee.entity.Organization;
import zeroone.developers.employee.payload.EmployeeDto;
import zeroone.developers.employee.payload.OrganizationDto;
import zeroone.developers.employee.repository.EmployeeRepository;
import zeroone.developers.employee.service.impl.EmployeeServiceImpl;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllEmployees() throws ParseException {

        // 1. Create List object
        List<EmployeeDto> employeeDtos = new ArrayList<>();

        // 2. Set objects
        // object 1
        EmployeeDto employeeDto1 = new EmployeeDto();
        employeeDto1.setId(1L);
        employeeDto1.setFirstName("Nizomiddin");
        employeeDto1.setLastName("Mirzanazarov");
        employeeDto1.setPinfl("1234567890");
        employeeDto1.setHireDate(Date.valueOf("2022-01-01"));

        // OrganizationDto obyektini yaratish va set qilish
        OrganizationDto organizationDto1 = new OrganizationDto();
        organizationDto1.setName("Zero:One Group");
        employeeDto1.setOrganization(organizationDto1);

        // object 2
        EmployeeDto employeeDto2 = new EmployeeDto();
        employeeDto2.setId(1L);
        employeeDto2.setFirstName("Dilmurod");
        employeeDto2.setLastName("Rustamov");
        employeeDto2.setPinfl("1234567123");
        employeeDto2.setHireDate(Date.valueOf("2022-09-06"));

        // OrganizationDto obyektini yaratish va set qilish
        OrganizationDto organizationDto2 = new OrganizationDto();
        organizationDto2.setName("Fido Biznes");
        employeeDto1.setOrganization(organizationDto2);

        // 3. Add employee list
        employeeDtos.add(employeeDto1);
        employeeDtos.add(employeeDto2);

        // 4. Mock qilingan repositorydan findAll() metodini chaqirganda employeeDtos ro'yxatini qaytaramiz
        // DTO dan Employee ga o'tkazish uchun konversiya qilish
        List<Employee> employees = employeeDtos.stream()
                .map(employeeDto -> employeeService.dtoToEmployee(employeeDto)) // dtoToEmployee metodini chaqiramiz
                .collect(Collectors.toList());

        when(employeeRepository.findAll()).thenReturn(employees);

        // 5. EmployeeDto ro'yxatini olish
        List<EmployeeDto> result = employeeService.findAllEmployees();

        // 6. Natijalarni tekshiramiz
        assertEquals(2, result.size()); // Employee ro'yxati o'lchamini tekshiramiz

        // EmployeeDto1 uchun tekshirishlar
        assertEquals("Nizomiddin", result.get(0).getFirstName()); // Birinchi EmployeeDto ning ismini tekshiramiz
        assertEquals("Zero:One Group", result.get(0).getOrganization().getName()); // Birinchi EmployeeDto ning organization ni tekshiramiz

        // EmployeeDto2 uchun tekshirishlar
        assertEquals("Dilmurod", result.get(1).getFirstName()); // Ikkinchi EmployeeDto ning ismini tekshiramiz
        assertEquals("Fido Biznes", result.get(1).getOrganization().getName()); // Ikkinchi EmployeeDto ning organization ni tekshiramiz
    }








}
