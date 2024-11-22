import React, { useState } from "react";
import { Header } from "../components/Header.tsx";
import Button from "../components/Button.tsx";
import { Alert } from "../components/UI/Alert.tsx";
import { ChatBlob } from "../components/UI/ChatBlob.tsx";
import BackButton from "../components/UI/BackButton.tsx";

export const AIModule = () => {
  const [message, setMessage] = useState("");
  const [selectedOption, setSelectedOption] = useState("");

  const handleInputChange = (e) => {
    setMessage(e.target.value);
  };

  const handleOptionChange = (e) => {
    setSelectedOption(e.target.value);
  };

  const handleSubmit = () => {
    console.log("Wysłano wiadomość:", message);
    console.log("Wybrana opcja:", selectedOption);
    setMessage("");
  };

  return (
    <div className="min-h-screen w-full bg-white flex justify-center items-center p-4">
      <div className="w-full max-w-6xl flex flex-col-reverse lg:flex-row gap-6 bg-[#F0EFFF] shadow-md rounded-md p-4 md:p-6 lg:p-10">
        <div className="w-full lg:w-1/2 bg-white rounded-lg p-4 md:p-6">
          <BackButton />
          <Alert />

          <div className="mb-4">
            <label
              htmlFor="message"
              className="block text-gray-700 font-medium mb-2"
            >
              Treść wiadomości:
            </label>
            <textarea
              id="message"
              rows={3}
              className="w-full border text-black border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-blue-500 focus:border-blue-500"
              value={message}
              onChange={handleInputChange}
            ></textarea>
          </div>

          <div className="mb-4">
            <label
              htmlFor="option"
              className="block text-gray-700 font-medium mb-2"
            >
              Wybierz opcję:
            </label>
            <select
              id="option"
              className="w-full border text-black border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-blue-500 focus:border-blue-500"
              value={selectedOption}
              onChange={handleOptionChange}
            >
              <option value="">Wybierz opcję</option>
              <option value="option1">Opcja 1</option>
              <option value="option2">Opcja 2</option>
              <option value="option3">Opcja 3</option>
            </select>
          </div>

          <Button onClick={handleSubmit} variant="primary" className="w-full">
            Zapytaj AI
          </Button>
        </div>

        <div className="w-full lg:w-1/2 bg-[#F1F1F1] rounded-lg p-4 md:p-6 lg:p-10 overflow-y-auto max-h-[600px]">
          <div className="space-y-4">
            <ChatBlob name="You" message="Jakas wiadomosc" role="You" />
            <ChatBlob name="Dr.Mengele" message="Response" role={"AI"} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default AIModule;
