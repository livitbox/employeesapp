package org.livitbox.employeesservice.converter;

import org.livitbox.employeesservice.dto.EmployeeDTO;
import org.livitbox.employeesservice.entity.Employee;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class Employee2EmployeeDtoConverter implements Converter<Employee, EmployeeDTO> {

    @Override
    @NonNull
    public EmployeeDTO convert(Employee source) {
        return new EmployeeDTO(
                source.getId(),
                source.getName(),
                source.getEmail(),
                source.getPhone(),
                source.getAddress(),
                source.getCompany(),
                source.getDate(),
                source.getCategory(),
                source.getComment()
        );
    }
}
