import { Landing } from "./views/Landing.tsx";
import { Routes, Route, BrowserRouter } from "react-router-dom";
import { AIModule } from "./views/AIModule.tsx";
import AppointmentBooking from "./views/Assignment.tsx";
import { CancelAssignment } from "./views/CancelAssignment.tsx";
import {ConfirmAssignment} from "./views/ConfirmAssignment.tsx";
const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Landing />} />
        <Route path="/ai" element={<AIModule />} />
        <Route path="/book" element={<AppointmentBooking />} />
        <Route path="/manage" element={<CancelAssignment />} />
        <Route path="/appointment" element={<ConfirmAssignment />} />
      </Routes>
    </BrowserRouter>
  );
};
export default App;
