import React from "react";
import { useNavigate } from "react-router-dom";
import { ChevronLeft } from "lucide-react";

const BackButton = () => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(-1);
  };

  return (
    <div
      className="flex items-center text-gray-600 hover:cursor-pointer transition-colors hover:text-gray-800 mb-5 hover:border-none hover:outline-none focus:outline-none"
      onClick={handleClick}
    >
      <ChevronLeft size={20} className="mr-2" />
      <span>Powrót</span>
    </div>
  );
};

export default BackButton;
