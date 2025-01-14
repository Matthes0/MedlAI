import React from "react";
import { Header } from "../components/Header.tsx";
import Button from "../components/Button.tsx";
import { useNavigate } from "react-router-dom";

export const Landing = () => {
  const navigate = useNavigate();
  return (
    <div className="h-screen w-screen bg-white">
      <div className=" flex-col h-full w-full">
        <main className="flex justify-around w-full h-full">
          <div className="space-y-8 flex justify-between w-full h-full">
            <div className="space-y-4 flex justify-center flex-col md:ml-[65px] p-5 ">
              <Header />
              <Button variant="primary" onClick={() => navigate("/ai")}>
                Zapytaj AI
              </Button>
              <Button variant="secondary" onClick={() => navigate("/book")}>
                Umów wizytę
              </Button>
              <Button variant="secondary" onClick={() => navigate("/admin")}>
                Admin panel
              </Button>
              <Button variant="secondary" onClick={() => navigate("/login")}>
                Zaloguj się
              </Button>
            </div>
            <div className="flex-col justify-end items-end hidden md:block">
              <div className="flex justify-end self-end bg-header-doc bg-[length:700px_505px] bg-center bg-no-repeat h-[500px] w-[900px]" />
            </div>
          </div>
          {/* <DoctorIllustration /> */}
        </main>
      </div>
    </div>
  );
};
