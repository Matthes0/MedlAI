import React, { useState } from "react";
import { useMutation, UseMutationResult } from "@tanstack/react-query";
import Button from "../components/Button";
import { ChatBlob } from "../components/UI/ChatBlob";
import BackButton from "../components/UI/BackButton";
import { Alert } from "../components/UI/Alert";
import { ErrorInfo } from "../components/UI/ErrorInfo";

interface ChatMessage {
  message: string;
  role: "Ty" | "AI";
  name: string;
  isTyping?: boolean;
}

const API_URL = " http://212.182.25.252:8000";

export const AIModule = () => {
  const [message, setMessage] = useState("");
  const [chatHistory, setChatHistory] = useState<ChatMessage[]>([]);
  const [error, setError] = useState<string | null>(null);

  const simulateTyping = async (text: string): Promise<string> => {
    return new Promise((resolve) => {
      let displayedText = "";
      let currentIndex = 0;

      const typingInterval = setInterval(() => {
        if (currentIndex < text.length) {
          displayedText += text[currentIndex];
          currentIndex++;

          setChatHistory((prev) => {
            const newHistory = [...prev];
            newHistory[newHistory.length - 1] = {
              ...newHistory[newHistory.length - 1],
              message: displayedText,
              isTyping: true,
            };
            return newHistory;
          });
        } else {
          clearInterval(typingInterval);
          resolve(text);
        }
      }, 30);
    });
  };

  const mutation: UseMutationResult<string, Error, string> = useMutation({
    mutationFn: async (symptoms: string) => {
      setError(null);
      const response = await fetch(
        `${API_URL}/medical-advice?symptoms=${encodeURIComponent(symptoms)}`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      if (!response.ok) {
        throw new Error(
          `Błąd podczas komunikacji z serwerem: ${response.status}`
        );
      }

      return response.text();
    },
    onSuccess: async (data) => {
      setChatHistory((prev) => [
        ...prev,
        {
          message,
          role: "Ty",
          name: "Ty",
        },
      ]);

      setChatHistory((prev) => [
        ...prev,
        {
          message: "",
          role: "AI",
          name: "Dr. Mengele",
          isTyping: true,
        },
      ]);

      await simulateTyping(data);

      setChatHistory((prev) => {
        const newHistory = [...prev];
        newHistory[newHistory.length - 1] = {
          ...newHistory[newHistory.length - 1],
          isTyping: false,
        };
        return newHistory;
      });

      setMessage("");
    },
    onError: (error: Error) => {
      setError(error.message);
      console.error("Błąd podczas wysyłania zapytania:", error);
    },
  });

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();

    if (!message.trim()) {
      return;
    }

    mutation.mutate(message);
  };

  const filterAndReplace = (input: string): string => {
    return input.replace(/['"]/g, "").replace(/<s>/g, "");
  };

  return (
    <div className="min-h-screen w-full flex justify-center items-center p-4">
      <div className="absolute inset-0 -z-10 h-full w-full bg-white bg-[linear-gradient(to_right,#f0f0f0_1px,transparent_1px),linear-gradient(to_bottom,#f0f0f0_1px,transparent_1px)] bg-[size:6rem_4rem]">
        <div className="absolute bottom-0 left-0 right-0 top-0 bg-[radial-gradient(circle_800px_at_100%_200px,#d5c5ff,transparent)]"></div>
      </div>

      <div className="w-full max-w-6xl flex flex-col-reverse lg:flex-row gap-6 bg-[#F0EFFF] shadow-md rounded-md p-4 md:p-6 lg:p-10">
        <div className="w-full lg:w-1/2 bg-white rounded-lg p-4 md:p-6">
          <BackButton />
          <Alert
            title="Uwaga!"
            content="Pamiętaj, że sztuczna inteligencja tylko jest sugestią rekomendowanego
        leczenia na podstawie podanych danych"
          />

          {error ? <ErrorInfo title="Błąd!" content={error} /> : null}

          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label
                htmlFor="message"
                className="block text-gray-700 font-medium mb-2"
              >
                Treść wiadomości:
              </label>
              <textarea
                id="message"
                name="message"
                rows={3}
                className="w-full border text-black border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                value={message}
                onChange={(e) => setMessage(e.target.value)}
                disabled={mutation.status === "pending"}
              />
            </div>

            <Button
              variant="primary"
              isLoading={mutation.status === "pending"}
              className="w-full"
              disabled={mutation.status === "pending" || !message.trim()}
            >
              {mutation.status === "pending" ? "Wysyłanie..." : "Zapytaj AI"}
            </Button>
          </form>
        </div>

        <div className="w-full lg:w-1/2 bg-[#F1F1F1] rounded-lg p-4 md:p-6 lg:p-10 overflow-y-auto max-h-[600px]">
          <div className="space-y-4">
            {chatHistory.length === 0 ? (
              <p className="text-gray-500 text-center">
                Rozpocznij rozmowę, aby otrzymać poradę medyczną.
              </p>
            ) : (
              chatHistory.map((chat, index) => (
                <ChatBlob
                  key={index}
                  name={chat.name}
                  message={filterAndReplace(chat.message)}
                  role={chat.role}
                />
              ))
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default AIModule;
