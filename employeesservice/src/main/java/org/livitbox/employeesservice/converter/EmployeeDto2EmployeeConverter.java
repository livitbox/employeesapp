package org.livitbox.employeesservice.converter;

import org.livitbox.employeesservice.dto.EmployeeDTO;
import org.livitbox.employeesservice.entity.Employee;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDto2EmployeeConverter implements Converter<EmployeeDTO, Employee> {

    @Override
    @NonNull
    public Employee convert(EmployeeDTO source) {
        return new Employee(
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
