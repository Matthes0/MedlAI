import { Clock } from "lucide-react";
import { TimeSlotProps } from "../views/Assignment.tsx";

export const TimeSlot: React.FC<TimeSlotProps> = ({
                                                      time,
                                                      status,
                                                      roomNumber = "123",
                                                  }) => {
  const getStatusStyle = () => {
    switch (status) {
      case "available":
        return "bg-blue-500 hover:bg-blue-600 text-white";
      case "selected":
        return "bg-gray-300 text-white cursor-not-allowed";
      case "unavailable":
        return "bg-red-100 text-red-500 cursor-not-allowed";
      default:
        return "bg-white";
    }
  };

  return (
    <div className="mb-4 bg-white rounded-lg shadow-sm p-4 min-w-[400px]">
      <div className="flex items-center text-sm text-gray-500 mb-2">
        <span>Gabinet: {roomNumber}</span>
      </div>
      <div className="flex justify-between items-center text-gray-500">
        <div className="flex justify-center items-center">
          <Clock className="w-4 h-4 m-0" />
          <span className="text-gray-700 ml-2">{time}</span>
        </div>

        <button
          className={`px-4 py-2 rounded-md transition-colors ${getStatusStyle()}`}
          disabled={status === "unavailable"}
        >
          {status === "unavailable"
            ? "Niedostępna"
            : status === "selected"
            ? "Wybrano"
            : "Wybierz"}
        </button>
      </div>
    </div>
  );
};
