export interface Doctor {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  specialization: Specialization;
}

export interface Appointment {
  id: number;
  startDate: string;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  address: string;
  pesel: string;
  doctor_id: number;
  status: AppointmentStatus;
}

export interface Absence {
  id: number;
  doctor_id: number;
  startDate: string;
  endDate: string;
  reason: AbsenceReason;
}

export enum Specialization {
  GENERAL = "General",
  CARDIOLOGIST = "Cardiologist",
  DERMATOLOGIST = "Dermatologist",
  NEUROLOGIST = "Neurologist",
  PEDIATRICIAN = "Pediatrics",
  PSYCHIATRIST = "Psychiatry",
  OPHTHALMOLOGIST = "Ophthalmologist",
  GASTROENTEROLOGIST = "Gastroenterologist",
  ENDOCRINOLOGIST = "Endocrinologist"

}

export enum AbsenceReason {
  VACATION = "Vacation",
  L4 = "L4",
  CHILDCARE = "Conference",
  OTHER = "Other",
}

export enum AppointmentStatus {
  SCHEDULED = "SCHEDULED",
  COMPLETED = "COMPLETED",
  CANCELLED = "CANCELLED",
  NO_SHOW = "NO_SHOW",
}
