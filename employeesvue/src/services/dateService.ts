import { format, parse } from 'date-fns'

export class DateService {
  static dateToString(date: Date | null, pattern: string = 'yyyy-MM-dd'): string {
    if (!date) return ''
    return format(date, pattern)
  }

  static stringToDate(value: string | null, pattern: string = 'yyyy-MM-dd'): Date | null {
    if (!value) return null

    const parsed = parse(value, pattern, new Date())

    return isNaN(parsed.getTime()) ? null : parsed
  }
}
