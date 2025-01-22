import React from "react";
import { Header } from "../components/Header.tsx";
import Button from "../components/Button.tsx";
import { useNavigate } from "react-router-dom";

export const Landing = () => {
  const navigate = useNavigate();

  return (
    <div className="min-h-screen bg-gradient-to-b from-white to-gray-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <main className="flex flex-col md:flex-row items-center justify-between py-12 md:py-16 lg:py-20">
          <div className="w-full md:w-1/2 space-y-8 md:pr-8">
            <div className="space-y-6">
              <Header />
              <h2 className="text-gray-600 text-lg md:text-xl max-w-md">
                Twoje zdrowie jest naszym priorytetem. Skorzystaj z AI lub umów
                się na wizytę już teraz.
              </h2>
            </div>

            <div className="grid gap-4 max-w-sm">
              <Button
                variant="primary"
                onClick={() => navigate("/ai")}
                className="group relative overflow-hidden"
              >
                <span className="relative z-10">Zapytaj AI</span>
                <div className="absolute inset-0 bg-blue-500 opacity-0 group-hover:opacity-20 transition-opacity" />
              </Button>

              <Button
                variant="secondary"
                onClick={() => navigate("/book")}
                className="hover:bg-gray-100"
              >
                Umów wizytę
              </Button>

              <div className="flex gap-4 mt-4">
                <Button
                  variant="secondary"
                  onClick={() => navigate("/admin")}
                  size="sm"
                  className="flex-1"
                >
                  Panel admina
                </Button>
              </div>
            </div>
          </div>

          <div className="hidden md:block w-full md:w-full mt-12 md:mt-0">
            <div className="relative">
              <div className="absolute -inset-4 bg-blue-100 rounded-full opacity-20 blur-3xl" />
              <div
                className="relative rounded-2xl bg-header-doc bg-cover bg-center h-[600px]
                shadow-lg transition-transform hover:scale-[1.02] duration-500"
              />
            </div>
          </div>
        </main>
      </div>
    </div>
  );
};
