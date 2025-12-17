<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import Button from 'primevue/button'
import { employeeService } from '@/services/employeeService'
import type { EmployeeDto } from '@/data/employeeDto'
import type { PageResponse } from '@/services/pageResponse'
import { DateService } from '@/services/dateService.ts'
import type { PageState } from 'primevue'

const router = useRouter()

const employees = ref<EmployeeDto[]>([])
const loading = ref(false)
const totalRecords = ref(0)
const rows = ref(10)
const page = ref(0)

const loadEmployees = async () => {
  loading.value = true
  try {
    const response: PageResponse<EmployeeDto> = await employeeService.getAllEmployees({
      page: page.value,
      size: rows.value,
    })
    employees.value = response.content
    totalRecords.value = response.page.totalElements
  } catch (error) {
    console.error('Failed to load employees:', error)
  } finally {
    loading.value = false
  }
}

const onPage = (event: PageState) => {
  page.value = event.page
  rows.value = event.rows
  loadEmployees()
}

const editEmployee = (id: number) => {
  router.push({ name: 'upsert', params: { id: id.toString() } })
}

const deleteEmployee = async (id: number) => {
  if (confirm('Are you sure you want to delete this employee?')) {
    try {
      await employeeService.deleteEmployee(id)
      await loadEmployees()
      alert('Employee deleted successfully')
    } catch (error) {
      console.error('Failed to delete employee:', error)
      alert('Failed to delete employee. Please try again.')
    }
  }
}

onMounted(() => {
  loadEmployees()
})
</script>

<template>
  <div class="data-form-container">
    <h2>Employee List</h2>
    <DataTable
      :value="employees"
      :loading="loading"
      :paginator="true"
      :rows="rows"
      :totalRecords="totalRecords"
      :lazy="true"
      @page="onPage"
      dataKey="id"
      class="p-datatable-sm"
    >
      <Column field="id" header="ID" />
      <Column field="name" header="Name" />
      <Column field="email" header="Email" />
      <Column field="phone" header="Phone" :bodyStyle="{ whiteSpace: 'nowrap' }" />
      <Column field="address" header="Address" />
      <Column field="company" header="Company" />
      <Column field="date" header="Date" :bodyStyle="{ whiteSpace: 'nowrap' }">
        <template #body="{ data }">
          {{ data.date ? DateService.dateToString(data.date, 'dd.MM.yyyy') : '-' }}
        </template>
      </Column>
      <Column field="category" header="Category" />
      <Column field="comment" header="Comment" />
      <Column header="Actions">
        <template #body="{ data }">
          <div class="action-buttons">
            <Button
              icon="pi pi-pencil"
              class="p-button-sm p-button-text"
              @click="editEmployee(data.id)"
            />
            <Button
              icon="pi pi-trash"
              class="p-button-sm p-button-text p-button-danger"
              @click="deleteEmployee(data.id)"
            />
          </div>
        </template>
      </Column>
    </DataTable>
  </div>
</template>

<style scoped>
.data-form-container {
  padding: 2rem;
}

h2 {
  margin-bottom: 1.5rem;
}

.action-buttons {
  display: flex;
  gap: 0.5rem;
  white-space: nowrap;
}
</style>
