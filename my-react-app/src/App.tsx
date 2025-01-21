import { Admin, Resource } from "react-admin";
import simpleRestProvider from "ra-data-simple-rest";

import { AppointmentList, AppointmentEdit } from "./components/Appointments";
import { DoctorList, DoctorCreate, DoctorEdit } from "./components/Doctors";
import { AbsenceCreate, AbsenceEdit, AbsenceList } from "./components/Absences";
import {
  ScheduleList,
  ScheduleCreate,
  ScheduleEdit,
} from "./components/Schedule";
import { dataProvider } from "./dataProvider";
// const authProvider = {
//     async login({ username, password }) {
//         try {
//             const response = await fetch("http://localhost:8080/api/auth/login", {
//                 method: "POST",
//                 headers: {
//                     "Content-Type": "application/x-www-form-urlencoded",
//                 },
//                 body: new URLSearchParams({
//                     username,
//                     password
//                 })
//             });
//
//             if (!response.ok) {
//                 throw new Error('Login failed');
//             }
//
//             const data = await response.json();
//
//             // Assuming the API returns a token
//             if (data.token) {
//                 localStorage.setItem('token', data.token);
//                 localStorage.setItem('username', username);
//             }
//         } catch (error) {
//             throw new Error('Login failed: ' + error.message);
//         }
//     },
//
//     async checkError(error) {
//         const status = error.status;
//         if (status === 401 || status === 403) {
//             localStorage.removeItem('token');
//             localStorage.removeItem('username');
//             throw new Error('Session expired');
//         }
//         // other error codes (404, 500, etc): no need to log out
//     },
//
//     async checkAuth() {
//         const token = localStorage.getItem('token');
//         const username = localStorage.getItem('username');
//
//         if (!token || !username) {
//             throw new Error('Not authenticated');
//         }
//
//         // Optional: Verify token validity with the backend
//         try {
//             const response = await fetch("http://localhost:8080/verify-token", {
//                 method: "POST",
//                 headers: {
//                     "Authorization": `Bearer ${token}`
//                 }
//             });
//
//             if (!response.ok) {
//                 throw new Error('Invalid token');
//             }
//         } catch (error) {
//             throw new Error('Authentication check failed');
//         }
//     },
//
//     async logout() {
//         // Optional: Call logout endpoint if needed
//         try {
//             const token = localStorage.getItem('token');
//             await fetch("http://localhost:8080/logout", {
//                 method: "POST",
//                 headers: {
//                     "Authorization": `Bearer ${token}`
//                 }
//             });
//         } catch (error) {
//             console.error('Logout error:', error);
//         } finally {
//             localStorage.removeItem('token');
//             localStorage.removeItem('username');
//         }
//     },
//
//     async getIdentity() {
//         const username = localStorage.getItem('username');
//         const token = localStorage.getItem('token');
//
//         try {
//             const response = await fetch("http://localhost:8080/user-profile", {
//                 headers: {
//                     "Authorization": `Bearer ${token}`
//                 }
//             });
//
//             if (response.ok) {
//                 const userData = await response.json();
//                 return {
//                     id: userData.id || username,
//                     fullName: userData.fullName || username,
//                     // Add any other user data returned by the API
//                 };
//             }
//         } catch (error) {
//             console.error('Error fetching user identity:', error);
//         }
//
//         // Fallback to basic identity if API call fails
//         return { id: username, fullName: username };
//     },
// };


const App = () => (
  <Admin dataProvider={dataProvider}
         // authProvider={authProvider}
  >
    <Resource
      name="doctors"
      list={DoctorList}
      create={DoctorCreate}
      edit={DoctorEdit}
    />
    <Resource
      name="appointment"
      list={AppointmentList}
      edit={AppointmentEdit}
    />
    <Resource
      name="absences"
      list={AbsenceList}
      create={AbsenceCreate}
      edit={AbsenceEdit}
    />
    <Resource
      name="schedules"
      list={ScheduleList}
      create={ScheduleCreate}
      edit={ScheduleEdit}
    />
  </Admin>
);

export default App;
