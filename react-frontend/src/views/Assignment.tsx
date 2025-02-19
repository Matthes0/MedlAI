import React, { useState, useCallback, useMemo } from "react";
import { Calendar } from "lucide-react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { useForm, SubmitHandler } from "react-hook-form";
import BackButton from "../components/UI/BackButton.tsx";
import { DoctorCard } from "../components/DoctorCard.tsx";
import { TimeSlot } from "../components/TimeSlot.tsx";
import { Stepper } from "../components/Stepper.tsx";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";

// Types
export interface Doctor {
  id: number;
  name: string;
  title: string;
}

export interface TimeSlotProps {
  id: number;
  time: string;
  status: "available" | "selected" | "unavailable";
  onClick?: () => void;
}

interface PersonalDataForm {
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  pesel: string;
  address: string;
}

interface Appointment {
  doctor_id: number;
  start_date: string;
  patient_first_name: string;
  patient_last_name: string;
  patient_email: string;
  patient_phone: string;
  patient_pesel: string;
  patient_address: string;
  status: string;
}

// Validation constants
const VALIDATION_RULES = {
  firstName: {
    required: "Imię jest wymagane",
    minLength: { value: 2, message: "Imię musi mieć minimum 2 znaki" },
  },
  lastName: {
    required: "Nazwisko jest wymagane",
    minLength: { value: 2, message: "Nazwisko musi mieć minimum 2 znaki" },
  },
  email: {
    required: "Email jest wymagany",
    pattern: {
      value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
      message: "Nieprawidłowy format email",
    },
  },
  phone: {
    required: "Numer telefonu jest wymagany",
    pattern: {
      value: /^[0-9]{9,11}$/,
      message: "Numer telefonu musi zawierać 9-11 cyfr",
    },
  },
  pesel: {
    required: "PESEL jest wymagany",
    pattern: {
      value: /^[0-9]{11}$/,
      message: "PESEL musi składać się z 11 cyfr",
    },
  },
  address: {
    required: "Adres jest wymagany",
    minLength: { value: 10, message: "Adres musi być bardziej szczegółowy" },
  },
};

const CustomDateInput = React.forwardRef<
  HTMLInputElement,
  { value?: string; onClick?: () => void }
>(({ value, onClick }, ref) => (
  <div className="relative">
    <input
      type="text"
      className="w-full px-4 py-2 rounded-md border border-gray-200 text-black cursor-pointer"
      onClick={onClick}
      value={value}
      ref={ref}
      readOnly
      placeholder="Wybierz datę"
    />
    <Calendar className="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-5 h-5" />
  </div>
));

const AppointmentBooking: React.FC = () => {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const [step, setStep] = useState(1);
  const [searchedDoctor, setSearchedDoctor] = useState<string>("");
  const [selectedDoctor, setSelectedDoctor] = useState<Doctor | null>(null);
  const [selectedDate, setSelectedDate] = useState<Date | null>(null);
  const [selectedTime, setSelectedTime] = useState<string | null>(null);
  const [personalData, setPersonalData] = useState<PersonalDataForm | null>(
    null
  );
  // const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);
  const queryDoctor = useQuery({
    queryKey: ["doctor"],
    queryFn: async () => {
      const response = await fetch("http://10.50.50.123:8080/api/doctor/get");
      if (!response.ok) {
        throw new Error("Failed to fetch doctors");
      }
      return response.json();
    },
  });

  const convertDateFormat = (date: Date): string => {
    const day = date.getDate().toString().padStart(2, "0");
    const month = (date.getMonth() + 1).toString().padStart(2, "0");
    const year = date.getFullYear();

    return `${year}-${month}-${day}`;
  };
  const queryAppointment = useQuery({
    queryKey: ["appointment", selectedDoctor?.id, selectedDate?.toISOString()], // Add dependencies to queryKey
    queryFn: async () => {
      console.log("QueryFn executing with:", {
        doctorId: selectedDoctor?.id,
        date: selectedDate ? convertDateFormat(selectedDate) : null,
      });

      if (!selectedDoctor || !selectedDate) {
        console.log("Early return due to missing data");
        return [];
      }

      try {
        const url = `http://10.50.50.123:8080/api/appointment/get?doctorID=${
          selectedDoctor.id
        }&date=${convertDateFormat(selectedDate)}`;

        console.log("Fetching from URL:", url);

        const response = await fetch(url);
        console.log("Response status:", response.status);

        if (!response.ok) {
          const errorText = await response.text();
          console.error("Response error:", errorText);
          throw new Error(
            `Failed to fetch appointments: ${response.status} - ${errorText}`
          );
        }

        const data = await response.json();
        console.log("Received data:", data);
        return data;
      } catch (error) {
        console.error("Query error:", error);
        throw error;
      }
    },
    enabled: selectedDoctor !== null && selectedDate !== null,
  });
  const filteredAppointments = queryAppointment.data || [];

  const handleTimeSelection = (time: string) => {
    setSelectedTime(selectedTime === time ? null : time);
  };

  const mutation = useMutation({
    mutationFn: (appointment: Appointment) =>
      fetch("http://10.50.50.123:8080/api/appointment/add", {
        method: "POST",
        headers: {
          "Content-Type": "application/json", // Set the correct Content-Type
        },
        body: JSON.stringify(appointment),
      }).then((res) => {
        if (!res.ok) {
          throw new Error("Failed to create appointment");
        }
        return res.json();
      }),
    onSuccess: () => {
      alert("Wizyta została umówiona! Potwierdź ją teraz mailowo.");
      queryClient.invalidateQueries({ queryKey: ["appointment"] });
      navigate("/");
    },
    onError: (error) => {
      alert(`Wystąpił błąd: ${error.message}`);
    },
  });

  const {
    register,
    handleSubmit,
    formState: { errors, isValid },
  } = useForm<PersonalDataForm>({
    mode: "onChange",
    reValidateMode: "onChange",
  });

  const STEPS = ["Lekarz i Termin", "Dane osobowe", "Potwierdzenie"];

  const handleNextStep = useCallback(() => {
    console.log(selectedDate?.toString());
    console.log(selectedTime?.toString());
    if (step < 3) {
      const canProceed =
        (step === 1 && selectedDoctor && selectedDate && selectedTime) ||
        (step === 2 && isValid);
      if (canProceed) {
        if (step === 2) {
          // Save personal data without submitting
          handleSubmit((data) => {
            setPersonalData(data);
            console.log("Personal data saved:", data);
          })();
        }
        setStep((prevStep) => prevStep + 1);
      }
    }
  }, [step, selectedDoctor, selectedDate, handleSubmit, selectedTime, isValid]);

  const handlePreviousStep = useCallback(() => {
    if (step > 1) {
      setStep((prevStep) => prevStep - 1);
    }
  }, [step]);

  const onSubmit: SubmitHandler<PersonalDataForm> = (data) => {
    console.log("Form submitted:", data);
    handleNextStep();
  };
  const submitAppointment = handleSubmit((data) => {
    if (selectedDoctor && selectedDate && selectedTime) {
      const appointment: Appointment = {
        doctor_id: selectedDoctor.id,
        start_date:
          convertDateFormat(selectedDate) + "T" + selectedTime.toString(),
        patient_first_name: data.firstName,
        patient_last_name: data.lastName,
        patient_email: data.email,
        patient_phone: data.phone,
        patient_pesel: data.pesel,
        patient_address: data.address,
        status: "TO_BE_CONFIRMED",
      };

      console.log("Appointment data:", appointment);
      mutation.mutate(appointment);
    } else {
      alert("Please complete all fields before confirming.");
    }
  });
  const filteredDoctors = useMemo(() => {
    return (
      queryDoctor.data?.filter((item: Doctor) => {
        const doctorName = item.name.replace(/^Dr\.\s*/i, "");
        const searchTerm = searchedDoctor.replace(/^Dr\.\s*/i, "");
        return doctorName.toLowerCase().includes(searchTerm.toLowerCase());
      }) ?? []
    );
  }, [queryDoctor.data, searchedDoctor]);

  const renderStep = () => {
    switch (step) {
      case 1:
        return (
          <div className="flex flex-col md:flex-row w-full space-y-5 md:space-y-0 md:space-x-5 justify-center">
            <div className="w-full md:w-1/2">
              <div className="mb-6">
                <input
                  type="text"
                  placeholder="Wyszukaj..."
                  onChange={(val) => setSearchedDoctor(val.target.value)}
                  className="w-full px-4 py-2 rounded-md border border-gray-200 text-black"
                />
              </div>

              <div>
                {(filteredDoctors.length > 0
                  ? filteredDoctors
                  : queryDoctor.data
                )?.map((doctor: Doctor) => (
                  <DoctorCard
                    key={doctor.id}
                    doctor={doctor}
                    isSelected={selectedDoctor?.id === doctor.id}
                    onSelect={() => {
                      setSelectedDoctor(doctor);
                    }}
                  />
                ))}
              </div>
            </div>
            {selectedDoctor && (
              <div className="w-full md:w-1/2">
                <div className="w-full">
                  <DatePicker
                    selected={selectedDate}
                    onChange={setSelectedDate}
                    dateFormat="dd.MM.yyyy"
                    minDate={new Date()}
                    customInput={<CustomDateInput />}
                    filterDate={(date: Date) =>
                      date.getDay() !== 0 && date.getDay() !== 6
                    }
                    className="w-full"
                  />
                </div>
                {selectedDate && (
                  <div className="mt-4 space-y-4 max-h-[300px] overflow-y-auto">
                    <h3 className="font-medium mb-4 text-black">
                      {selectedDate.toLocaleDateString("pl-PL", {
                        weekday: "long",
                        day: "numeric",
                        month: "numeric",
                      })}
                    </h3>
                    {filteredAppointments.map((appointment: any) => (
                      <TimeSlot
                        key={appointment.id}
                        id={appointment.id}
                        time={appointment.startTime.toString()}
                        status="available"
                        isSelected={
                          selectedTime === appointment.startTime.toString()
                        }
                        onClick={() =>
                          handleTimeSelection(appointment.startTime.toString())
                        }
                      />
                    ))}
                  </div>
                )}
              </div>
            )}
          </div>
        );

      case 2:
        return (
          <form
            onSubmit={handleSubmit(onSubmit)}
            className="w-full max-w-2xl mx-auto px-4 sm:px-0"
          >
            <div className="space-y-4">
              {Object.entries(VALIDATION_RULES).map(([field, rules]) => (
                <div key={field}>
                  <label className="block text-sm font-medium text-gray-700 mb-1">
                    {field === "firstName" && "Imię"}
                    {field === "lastName" && "Nazwisko"}
                    {field === "email" && "Email"}
                    {field === "phone" && "Numer telefonu"}
                    {field === "pesel" && "PESEL"}
                    {field === "address" && "Adres"}
                  </label>
                  {field !== "address" ? (
                    <input
                      {...register(field as keyof PersonalDataForm, rules)}
                      className="w-full px-4 py-2 rounded-md border border-gray-200 text-black"
                    />
                  ) : (
                    <textarea
                      {...register(field, rules)}
                      className="w-full px-4 py-2 rounded-md border border-gray-200 text-black"
                      rows={3}
                    />
                  )}
                  {errors[field as keyof PersonalDataForm] && (
                    <p className="text-red-500 text-sm mt-1">
                      {errors[field as keyof PersonalDataForm]?.message}
                    </p>
                  )}
                </div>
              ))}
            </div>
            <button type="submit" className="hidden" />
          </form>
        );

      case 3:
        return (
          <div className="w-full mx-auto p-6 bg-white rounded-lg shadow-sm text-black px-4 sm:px-6">
            <h2 className="text-xl font-semibold mb-4">Podsumowanie wizyty</h2>

            <div className="space-y-4">
              <div>
                <h3 className="font-medium">Lekarz</h3>
                <p>
                  {selectedDoctor?.name} - {selectedDoctor?.title}
                </p>
              </div>

              <div>
                <h3 className="font-medium">Termin</h3>
                <p>
                  {selectedDate?.toLocaleDateString("pl-PL", {
                    weekday: "long",
                    day: "numeric",
                    month: "long",
                    year: "numeric",
                  })}{" "}
                  o godzinie {selectedTime}
                </p>
              </div>
              <div>
                <h3 className="font-medium">Dane pacjenta</h3>
                {personalData && (
                  <p>
                    Imię: {personalData.firstName} <br />
                    Nazwisko: {personalData.lastName} <br />
                    Email: {personalData.email} <br />
                    Telefon: {personalData.phone} <br />
                    PESEL: {personalData.pesel} <br />
                    Adres: {personalData.address}
                  </p>
                )}
              </div>
            </div>
          </div>
        );

      default:
        return null;
    }
  };
  return (
    <div className="min-h-screen w-full flex justify-center items-center  p-4">
      <main className="flex flex-col justify-center items-center w-full max-w-5xl">
        <div className="flex w-full bg-[#F0EFFF] shadow-md rounded-md p-4 md:p-10">
          <div className="rounded-lg p-4 md:p-6 w-full max-h-fit">
            <BackButton />
            <Stepper steps={STEPS} step={step} />
            {renderStep()}
            <div className="mt-6 flex flex-col-reverse md:flex-row justify-end space-y-4 md:space-y-0 md:space-x-4">
              {step > 1 && (
                <button
                  type="button"
                  className="w-full md:w-auto px-6 py-2 my-2 md:my-0 bg-gray-500 text-white rounded-md hover:bg-gray-600"
                  onClick={handlePreviousStep}
                >
                  Wstecz
                </button>
              )}
              {step < 3 && (
                <button
                  type="button"
                  className="w-full md:w-auto px-6 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 disabled:bg-gray-400"
                  onClick={handleNextStep}
                  disabled={
                    (step === 1 && (!selectedDoctor || !selectedDate)) ||
                    (step === 2 && !isValid)
                  }
                >
                  Dalej
                </button>
              )}
              {step === 3 && (
                <button
                  type="button"
                  className="w-full md:w-auto px-6 py-2 bg-green-600 text-white rounded-md hover:bg-green-700"
                  onClick={submitAppointment}
                >
                  Potwierdź rezerwację
                </button>
              )}
            </div>
          </div>
        </div>
      </main>
    </div>
  );
};

export default AppointmentBooking;
