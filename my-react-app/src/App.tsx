import { Admin, Resource, AuthProvider } from "react-admin";
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


const authProvider = {
    async login({ username, password }) {
        if (username !== 'admin' || password !== 'admin') {
            throw new Error('Login failed');
        }
        localStorage.setItem('username', username);
    },
    async checkError(error) {
        const status = error.status;
        if (status === 401 || status === 403) {
            localStorage.removeItem('username');
            throw new Error('Session expired');
        }
        // other error codes (404, 500, etc): no need to log out
    },
    async checkAuth() {
        if (!localStorage.getItem('username')) {
            throw new Error('Not authenticated');
        }
    },
    async logout() {
        localStorage.removeItem('username');
    },
    async getIdentity() {
        const username = localStorage.getItem('username');
        return { id: username, fullName: username };
    },
};

const App = () => (
  <Admin dataProvider={dataProvider} authProvider={authProvider}>
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
