package com.mnt.springboot.repository;

import com.mnt.springboot.model.Employee;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee;

    @BeforeEach
    public void init() {
        employee = new Employee(null, "John", "Doe", "john.doe@example.com");
    }

    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        //when
        Employee savedEmployee = employeeRepository.save(employee);

        //then
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    //Junit test for findAll
    @Test
    public void givenEmployeeList_whenFindAll_thenReturnEmployeesList() {
        //given - precondition for setup
        List<Employee> employees = new ArrayList<>();
        Employee employee2 = new Employee(null, "Jane", "Smith", "jane.smith@example.com");

        employees.add(employee);
        employees.add(employee2);

        employeeRepository.saveAll(employees);
        //when  - action or behaviour that we are going to test
        List<Employee> employeeList = employeeRepository.findAll();

        //then - verify the output
        assertThat(employeeList.size()).isEqualTo(2);
    }

    //Junit test for findById
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployee() {
        employeeRepository.save(employee);
        //when  - action or behaviour that we are going to test
        Optional<Employee> employeeById = employeeRepository.findById(employee.getId());
        //then - verify the output
        assertThat(employeeById.isPresent()).isTrue();
    }
    //Junit test for get employee by email
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployee() {
        employeeRepository.save(employee);
        //when  - action or behaviour that we are going to test
        Optional<Employee> employeeByEmail = employeeRepository.findByEmail(employee.getEmail());
        //then - verify the output
        assertThat(employeeByEmail).isPresent();
    }

    //Junit test for update employee
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        employeeRepository.save(employee);
        //when  - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("test@gmail.com");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);
        //then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("test@gmail.com");
    }

    //Junit test for update employee
    @Test
    public void givenEmployeeObject_whenDelete_thenDeleteEmployee() {
        employeeRepository.save(employee);
        //when  - action or behaviour that we are going to test
        employeeRepository.delete(employee);
        Optional<Employee> savedEmployee = employeeRepository.findById(employee.getId());
        //then - verify the output
        assertThat(savedEmployee).isEmpty();
    }

    //Junit test for
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployee() {
        employeeRepository.save(employee);
        //when  - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByJPQL(employee.getFirstName(), employee.getLastName());
        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    //Junit test for
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParameters_thenReturnEmployee() {
        employeeRepository.save(employee);
        //when  - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByJPQLNamedParameters(employee.getFirstName(), employee.getLastName());
        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployee() {
        employeeRepository.save(employee);
        //when  - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByNativeQuery(employee.getFirstName(), employee.getLastName());
        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }
}
