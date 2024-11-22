import React from "react";

export const Stepper: React.FC<{ steps: string[]; step: number }> = ({
  steps,
  step,
}) => {
  return (
    <div className="flex justify-center md:justify-between mb-8 space-x-3 overflow-x-auto">
      {steps.map((text, index) => (
        <div key={index} className="flex items-center min-w-max">
          <div
            className={`w-8 h-8 rounded-full flex items-center justify-center ${
              step > index
                ? "bg-blue-500 text-white"
                : step === index + 1
                ? "bg-blue-500 text-white"
                : "bg-gray-200"
            }`}
          >
            {index + 1}
          </div>
          <span className="ml-2 text-sm text-black hidden md:inline">
            {text}
          </span>
        </div>
      ))}
    </div>
  );
};
