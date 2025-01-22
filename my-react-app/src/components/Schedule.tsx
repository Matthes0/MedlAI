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
            <ReferenceField source="doctor_id" reference="doctors">
                <TextField source="first_name"/>
                <span> </span>
                <TextField source="last_name"/>
            </ReferenceField>
            <TextField source="day_of_week" label="Day of Week" />
            <TextField source="start_time" label="Start Time" />
            <TextField source="end_time" label="End Time" />
            <DateField source="valid_to" label="Valid Until" />
        </Datagrid>
    </List>
);

export const ScheduleForm = () => (
    <SimpleForm>
        <ReferenceInput source="doctor_id" reference="doctors" label="Doctor">
            <SelectInput optionText={(record) => `${record.first_name} ${record.last_name}`} />
        </ReferenceInput>
        <SelectInput
            source="day_of_week"
            choices={weekDays}
            label="Day of Week"
            validate={required()}
        />
        <TimeInput source="start_time" label="Start Time" validate={required()} parse={(value)=> {
            return value
        }} />
        <TimeInput source="end_time" label="End Time" validate={required()} parse={(value)=> {
            return value
        }} />
        <DateInput source="valid_to" label="Valid Until" validate={required()} />
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
