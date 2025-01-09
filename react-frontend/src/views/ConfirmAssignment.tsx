import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";

export const ConfirmAssignment = () => {
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

    const mutation = useMutation({
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
        },
        onError: (error) => {
            alert(`Wystąpił błąd: ${error.message}`);
        },
    });
    const confirmAppointment = () => {
        if (token) {
            console.log("Appointment token:", token);
            mutation.mutate(token);
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
                            <h2 className="text-lg font-semibold mb-4">Imię i nazwisko</h2>
                            <p>{appointmentData.patient_first_name} {appointmentData.patient_last_name}</p>
                            <p>{appointmentData.patient_email}</p>
                            <p>{appointmentData.patient_phone}</p>
                            <p>{appointmentData.patient_pesel}</p>
                            <p>{appointmentData.patient_address}</p>
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
                            <button
                                className="px-6 py-2 bg-red-500 text-white rounded-md transition-all hover:bg-red-600"
                                onClick={confirmAppointment}
                            >
                                Potwierdź wizytę
                            </button>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    );
};
