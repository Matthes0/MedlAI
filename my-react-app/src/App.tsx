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


const App = () => (
  <Admin dataProvider={dataProvider}>
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
