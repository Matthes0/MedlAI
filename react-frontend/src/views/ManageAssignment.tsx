import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import {useNavigate} from "react-router-dom";

export const ManageAssignment = () => {
    const navigate = useNavigate()
    const queryClient = useQueryClient();
    const token = new URLSearchParams(window.location.search).get("token");
    const queryAppointment = useQuery({
        queryKey: ["appointment"],
        queryFn: async () => {
            const response = await fetch(`http://localhost:8080/api/appointment/getconfirmation?token=${token}`);
            if (!response.ok) {
                throw new Error("Failed to fetch appointment");
            }
            return response.json();
        },
    });

    const mutationConfirm = useMutation({
        mutationFn: (token: string) =>
            fetch("http://localhost:8080/api/appointment/confirm", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json", // Set the correct Content-Type
                },
                body: JSON.stringify(token),
            }).then((res) => {
                if (!res.ok) {
                    throw new Error("Failed to confirm appointment");
                }
                return res.json();
            }),
        onSuccess: () => {
            alert("Wizyta została potwierdzona!");
            queryClient.invalidateQueries(["appointment"]);
            navigate("/");
        },
        onError: (error) => {
            alert(`Wystąpił błąd: ${error.message}`);
        },
    });

    const mutationCancel = useMutation({
        mutationFn: (token: string) =>
            fetch("http://localhost:8080/api/appointment/cancel", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json", // Set the correct Content-Type
                },
                body: JSON.stringify(token),
            }).then((res) => {
                if (!res.ok) {
                    throw new Error("Failed to cancel appointment");
                }
                return res.json();
            }),
        onSuccess: () => {
            alert("Wizyta została anulowana!");
            queryClient.invalidateQueries(["appointment"]);
            navigate("/");

        },
        onError: (error) => {
            alert(`Wystąpił błąd: ${error.message}`);
        },
    });
    const handleConfirm = () => {
        if (token) {
            mutationConfirm.mutate(token);
        } else {
            alert("Invalid token");
        }
    };

    const handleCancel = () => {
        if (token) {
            mutationCancel.mutate(token);
        } else {
            alert("Invalid token");
        }
    };

    if (queryAppointment.isLoading) {
        return <div>Loading appointment details...</div>;
    }

    if (queryAppointment.isError) {
        return <div>Error: {queryAppointment.error.message}</div>;
    }

    const appointmentData = queryAppointment.data;

    return (
        <div className="h-screen w-screen flex justify-center items-center bg-white">
            <main className="flex flex-col justify-center items-center w-full">
                <div className="flex p-10 bg-[#F0EFFF] shadow-lg rounded-md">
                    <div className="rounded-lg p-6 w-full max-h-fit flex flex-col justify-center">
                        <div className="w-full max-w-2xl mx-auto p-5 bg-white rounded-lg shadow-sm text-black">
                            <h2 className="text-xl font-semibold mb-4">
                                Potwierdź wizytę
                            </h2>
                            <div>
                                <h3 className="text-lg font-semibold mb-4">Imię i nazwisko</h3>
                                <p>{appointmentData.patient_first_name} {appointmentData.patient_last_name}</p>
                            </div>
                            <div>
                                <h3 className="text-lg font-semibold mb-4">Email</h3>
                                <p>{appointmentData.patient_email}</p>
                            </div>
                            <div>
                                <h3 className="text-lg font-semibold mb-4">Numer telefonu</h3>
                                <p>{appointmentData.patient_phone}</p>
                            </div>
                            <div>
                                <h3 className="text-lg font-semibold mb-4">Numer pesel</h3>
                                <p>{appointmentData.patient_pesel}</p>
                            </div>
                            <div>
                                <h3 className="text-lg font-semibold mb-4">Adres</h3>
                                <p>{appointmentData.patient_address}</p>
                            </div>

                            <div className="border-b-2 border-blue-600 mb-5"></div>

                            <div className="space-y-4">
                                <div>
                                    <h3 className="font-medium">Lekarz</h3>
                                    <p>{appointmentData.doctor_first_name} {appointmentData.doctor_last_name}</p>
                                </div>

                                <div>
                                    <h3 className="font-medium">Termin</h3>
                                    <p>
                                        {appointmentData.start_date}
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div className="flex space-x-6 mt-5">
                            {appointmentData.status === "TO_BE_CONFIRMED" && (
                                <button
                                    className="px-6 py-2 bg-green-500 text-white rounded-md transition-all hover:bg-green-600"
                                    onClick={handleConfirm}
                                >
                                    Potwierdź wizytę
                                </button>
                            )}
                            {appointmentData.status === "SCHEDULED" && (
                                <button
                                    className="px-6 py-2 bg-red-500 text-white rounded-md transition-all hover:bg-red-600"
                                    onClick={handleCancel}
                                >
                                    Anuluj wizytę
                                </button>
                            )}
                        </div>
                    </div>
                </div>
            </main>
        </div>
    );
};