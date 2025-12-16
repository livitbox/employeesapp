import type { PageRequest } from '@/services/pageRequest.ts'
import type { PageResponse } from '@/services/pageResponse.ts'
import type { EmployeeDto } from '@/data/employeeDto.ts'

const API_BASE_URL = 'http://localhost:8080/api/employees'

export const employeeService = {
  /**
   * Get all employees with pagination
   */
  async getAllEmployees(pageRequest: PageRequest): Promise<PageResponse<EmployeeDto>> {
    const params = new URLSearchParams({
      page: pageRequest.page.toString(),
      size: pageRequest.size.toString(),
    })

    if (pageRequest.sort) {
      params.append('sort', pageRequest.sort)
    }

    const response = await fetch(`${API_BASE_URL}?${params.toString()}`)
    if (!response.ok) {
      throw new Error(`Failed to fetch employees: ${response.statusText}`)
    }
    return response.json()
  },

  /**
   * Get a single employee by ID
   */
  async getEmployeeById(id: number): Promise<EmployeeDto> {
    const response = await fetch(`${API_BASE_URL}/${id}`)
    if (!response.ok) {
      throw new Error(`Failed to fetch employee: ${response.statusText}`)
    }
    return response.json()
  },

  /**
   * Create a new employee
   */
  async createEmployee(employee: Omit<EmployeeDto, 'id'>): Promise<EmployeeDto> {
    const response = await fetch(API_BASE_URL, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(employee),
    })
    if (!response.ok) {
      throw new Error(`Failed to create employee: ${response.statusText}`)
    }
    return response.json()
  },

  /**
   * Update an existing employee
   */
  async updateEmployee(id: number, employee: Omit<EmployeeDto, 'id'>): Promise<EmployeeDto> {
    const response = await fetch(`${API_BASE_URL}/${id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(employee),
    })
    if (!response.ok) {
      throw new Error(`Failed to update employee: ${response.statusText}`)
    }
    return response.json()
  },

  /**
   * Delete an employee
   */
  async deleteEmployee(id: number): Promise<void> {
    const response = await fetch(`${API_BASE_URL}/${id}`, {
      method: 'DELETE',
    })
    if (!response.ok) {
      throw new Error(`Failed to delete employee: ${response.statusText}`)
    }
  },
}
