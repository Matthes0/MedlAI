import { ChevronLeft } from "lucide-react";
import { Doctor } from "../views/Assignment.tsx";

export const DoctorCard: React.FC<{
  doctor: Doctor;
  isSelected: boolean;
  onSelect: () => void;
}> = ({ doctor, isSelected, onSelect }) => (
  <div
    className={`flex items-center min-w-[300px] p-4 rounded-lg mb-3 cursor-pointer ${
      isSelected ? "bg-blue-500 text-white" : "bg-blue-500/90 text-white"
    }`}
    onClick={onSelect}
  >
    <div className="w-12 h-12 rounded-full bg-gray-200 mr-4" />
    <div className="flex-grow">
      <h3 className="font-medium">{doctor.name}</h3>
      <p className="text-sm opacity-90">{doctor.title}</p>
    </div>
    {isSelected ? (
      <div className="w-6 h-6">✓</div>
    ) : (
      <ChevronLeft className="w-6 h-6 rotate-180" />
    )}
  </div>
);
