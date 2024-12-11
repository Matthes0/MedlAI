export const CancelAssignment = () => {
  return (
    <div className="h-screen w-screen flex justify-center items-center bg-white">
      <main className="flex flex-col justify-center items-center w-full">
        <div className="flex p-10 bg-[#F0EFFF] shadow-lg rounded-md">
          <div className="rounded-lg p-6 w-full max-h-fit flex flex-col justify-center">
            <div className="w-full max-w-2xl mx-auto p-5 bg-white rounded-lg shadow-sm text-black">
              <h2 className="text-xl font-semibold mb-4">
                Zarządzanie wizytą #ID
              </h2>
              <h2 className="text-lg font-semibold mb-4">Imie i nazwisko</h2>
              <div className="border-b-2 border-blue-600 mb-5"></div>

              <div className="space-y-4">
                <div>
                  <h3 className="font-medium">Lekarz</h3>
                  <p>Dane Lekarza</p>
                </div>

                <div>
                  <h3 className="font-medium">Termin</h3>
                  <p>
                    {new Date().toLocaleDateString("pl-PL", {
                      weekday: "long",
                      day: "numeric",
                      month: "long",
                      year: "numeric",
                    })}{" "}
                    {new Date().toLocaleTimeString("pl-PL")}
                  </p>
                </div>
              </div>
            </div>
            <div className="flex space-x-6 mt-5">
              <button
                className="px-6 py-2 bg-red-500 text-white rounded-md transition-all hover:bg-red-600"
                onClick={() => alert("Wizyta została umówiona!")}
              >
                Anuluj wizytę
              </button>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
};
