import {
  List,
  Datagrid,
  TextField,
  DateField,
  Create,
  Edit,
  SimpleForm,
  DateInput,
  TimeInput,
  ReferenceInput,
  SelectInput,
  required,
  ReferenceField,
} from "react-admin";

const weekDays = [
  { id: "MONDAY", name: "Monday" },
  { id: "TUESDAY", name: "Tuesday" },
  { id: "WEDNESDAY", name: "Wednesday" },
  { id: "THURSDAY", name: "Thursday" },
  { id: "FRIDAY", name: "Friday" },
  { id: "SATURDAY", name: "Saturday" },
  { id: "SUNDAY", name: "Sunday" },
];

export const ScheduleList = () => (
  <List>
    <Datagrid rowClick="edit">
      <ReferenceField source="doctorId" reference="doctors">
        <TextField source="lastName" />
      </ReferenceField>
      <TextField source="dayOfWeek" />
      {/* <TimeField source="startTime" />
      <TimeField source="endTime" /> */}
      <DateField source="validUntil" />
    </Datagrid>
  </List>
);

const ScheduleForm = () => (
  <SimpleForm>
    <ReferenceInput source="doctorId" reference="doctors">
      <SelectInput optionText="lastName" />
    </ReferenceInput>
    <SelectInput source="dayOfWeek" choices={weekDays} validate={required()} />
    <TimeInput source="startTime" validate={required()} />
    <TimeInput source="endTime" validate={required()} />
    <DateInput source="validUntil" validate={required()} />
  </SimpleForm>
);

export const ScheduleCreate = () => (
  <Create>
    <ScheduleForm />
  </Create>
);

export const ScheduleEdit = () => (
  <Edit>
    <ScheduleForm />
  </Edit>
);
