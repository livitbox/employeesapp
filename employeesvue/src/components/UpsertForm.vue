<script setup lang="ts">
import { reactive, ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import InputText from 'primevue/inputtext'
import Button from 'primevue/button'
import DatePicker from 'primevue/datepicker'
import SelectButton from 'primevue/selectbutton'
import Textarea from 'primevue/textarea'
import { InputGroup, InputGroupAddon } from 'primevue'
import { employeeService } from '@/services/employeeService'
import type { EmployeeDto } from '@/data/employeeDto.ts'
import { DateService } from '@/services/dateService.ts'

const route = useRoute()
const router = useRouter()

const options = ['Software Engineer', 'DevOps', 'Human Resources']

const formData = reactive<EmployeeDto>({
  id: null,
  name: '',
  email: '',
  phone: '',
  address: '',
  company: '',
  date: null,
  category: options[0]!,
  comment: '',
})

const date = ref<Date | null>(null)
const loading = ref(false)
const isEditMode = computed(() => !!route.params.id)

const loadEmployee = async (id: number) => {
  loading.value = true
  try {
    const employee = await employeeService.getEmployeeById(id)
    Object.assign(formData, employee)
    date.value = DateService.stringToDate(employee.date)
  } catch (error) {
    console.error('Failed to load employee:', error)
    alert('Failed to load employee data')
  } finally {
    loading.value = false
  }
}

const handleSubmit = async () => {
  if (!formData.category) {
    alert('Please select a category')
    return
  }

  loading.value = true
  try {
    if (isEditMode.value && formData.id) {
      // Update existing employee
      const { id, ...employeeData } = formData
      employeeData.date = DateService.dateToString(date.value)
      await employeeService.updateEmployee(id, employeeData)
      console.log('Employee updated successfully')
      alert('Employee updated successfully!')
      await router.push({ name: 'home' })
    } else {
      // Create new employee
      const { ...employeeData } = formData
      employeeData.date = DateService.dateToString(date.value)
      const createdEmployee = await employeeService.createEmployee(employeeData)
      console.log('Employee created successfully:', createdEmployee)
      alert('Employee created successfully!')

      // Reset form
      Object.assign(formData, {
        id: null,
        name: '',
        email: '',
        phone: '',
        address: '',
        company: '',
        date: null,
        category: options[0]!,
        comment: '',
      })
      date.value = null
    }
  } catch (error) {
    console.error(`Failed to save employee:`, error)
    alert(`Failed to save employee. Please try again.`)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  if (route.params.id) {
    const id = Number.parseInt(route.params.id as string)
    if (!Number.isNaN(id)) {
      loadEmployee(id)
    }
  }
})
</script>

<template>
  <div class="form-container">
    <h2>{{ isEditMode ? 'Edit Employee' : 'Insert Employee' }}</h2>
    <form @submit.prevent="handleSubmit" class="form-grid">
      <div class="field">
        <label for="name">Name</label>
        <InputGroup>
          <InputGroupAddon>
            <i class="pi pi-user"></i>
          </InputGroupAddon>
          <InputText id="name" v-model="formData.name" placeholder="Enter name" required />
        </InputGroup>
      </div>

      <div class="field">
        <label for="email">Email</label>
        <InputGroup>
          <InputGroupAddon>
            <i class="pi pi-inbox"></i>
          </InputGroupAddon>
          <InputText
            id="email"
            v-model="formData.email"
            type="email"
            placeholder="Enter email"
            required
          />
        </InputGroup>
      </div>

      <div class="field">
        <label for="phone">Phone</label>
        <InputGroup>
          <InputGroupAddon>
            <i class="pi pi-phone"></i>
          </InputGroupAddon>
          <InputText id="phone" v-model="formData.phone" placeholder="Enter phone" required />
        </InputGroup>
      </div>

      <div class="field">
        <label for="address">Address</label>
        <InputGroup>
          <InputGroupAddon>
            <i class="pi pi-map-marker"></i>
          </InputGroupAddon>
          <InputText id="address" v-model="formData.address" placeholder="Enter address" required />
        </InputGroup>
      </div>

      <div class="field">
        <label for="company">Company</label>
        <InputGroup>
          <InputGroupAddon>
            <i class="pi pi-building"></i>
          </InputGroupAddon>
          <InputText id="company" v-model="formData.company" placeholder="Enter company" required />
        </InputGroup>
      </div>

      <div class="field">
        <label>Date</label>
        <InputGroup>
          <InputGroupAddon>
            <i class="pi pi-calendar"></i>
          </InputGroupAddon>
          <DatePicker v-model="date" dateFormat="dd.mm.yy" showIcon fluid required />
        </InputGroup>
      </div>

      <div class="field">
        <label>Category</label>
        <SelectButton
          v-model="formData.category"
          :options="options"
          :invalid="formData.category == null"
          required
        />
      </div>

      <div class="field">
        <label for="comment">Comment</label>
        <Textarea id="comment" v-model="formData.comment" rows="5" fluid required />
      </div>

      <Button type="submit" :label="isEditMode ? 'Update' : 'Submit'" :loading="loading" />
    </form>
  </div>
</template>

<style scoped>
.form-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 2rem;
}

.form-grid {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.field {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

label {
  font-weight: 600;
}
</style>
