package org.livitbox.employeesservice.service;

import org.livitbox.employeesservice.converter.Employee2EmployeeDtoConverter;
import org.livitbox.employeesservice.converter.EmployeeDto2EmployeeConverter;
import org.livitbox.employeesservice.dto.EmployeeDTO;
import org.livitbox.employeesservice.entity.Employee;
import org.livitbox.employeesservice.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final Employee2EmployeeDtoConverter employee2EmployeeDtoConverter;
    private final EmployeeDto2EmployeeConverter employeeDto2EmployeeConverter;

    public EmployeeService(EmployeeRepository employeeRepository,
                          Employee2EmployeeDtoConverter employee2EmployeeDtoConverter,
                          EmployeeDto2EmployeeConverter employeeDto2EmployeeConverter) {
        this.employeeRepository = employeeRepository;
        this.employee2EmployeeDtoConverter = employee2EmployeeDtoConverter;
        this.employeeDto2EmployeeConverter = employeeDto2EmployeeConverter;
    }

    public Page<EmployeeDTO> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable)
                .map(employee2EmployeeDtoConverter::convert);
    }

    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(employee2EmployeeDtoConverter::convert);
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeDto2EmployeeConverter.convert(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return employee2EmployeeDtoConverter.convert(savedEmployee);
    }

    public Optional<EmployeeDTO> updateEmployee(Long id, EmployeeDTO employeeDTO) {
        return employeeRepository.findById(id)
                .map(existingEmployee -> {
                    existingEmployee.setName(employeeDTO.getName());
                    existingEmployee.setEmail(employeeDTO.getEmail());
                    existingEmployee.setPhone(employeeDTO.getPhone());
                    existingEmployee.setAddress(employeeDTO.getAddress());
                    existingEmployee.setCompany(employeeDTO.getCompany());
                    existingEmployee.setDate(employeeDTO.getDate());
                    existingEmployee.setCategory(employeeDTO.getCategory());
                    existingEmployee.setComment(employeeDTO.getComment());
                    Employee updatedEmployee = employeeRepository.save(existingEmployee);
                    return employee2EmployeeDtoConverter.convert(updatedEmployee);
                });
    }

    public boolean deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}