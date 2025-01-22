import { Landing } from "./views/Landing.tsx";
import { Routes, Route, BrowserRouter } from "react-router-dom";
import { AIModule } from "./views/AIModule.tsx";
import AppointmentBooking from "./views/Assignment.tsx";
import { Admin } from "./views/Admin.tsx";
import { ManageAssignment } from "./views/ManageAssignment.tsx";
const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Landing />} />
        <Route path="/ai" element={<AIModule />} />
        <Route path="/book" element={<AppointmentBooking />} />
        <Route path="/manage" element={<ManageAssignment />} />
        <Route path="/admin" element={<Admin />} />
      </Routes>
    </BrowserRouter>
  );
};
export default App;
