import {
  List,
  Datagrid,
  TextField,
  DateField,
  ReferenceField,
  Edit,
  SimpleForm,
  TextInput,
  DateTimeInput,
  ReferenceInput,
  SelectInput,
  // Button,
  // useUpdate,
  // useNotify,
  EmailField,
} from "react-admin";

// const QuickStatusButton = ({ record }) => {
//   const [update] = useUpdate();
//   const notify = useNotify();

//   const handleClick = () => {
//     update(
//       "appointments",
//       { id: record.id, data: { status: "COMPLETED" } },
//       {
//         onSuccess: () => notify("Appointment marked as completed"),
//         onError: (error) =>
//           notify("Error: " + error.message, { type: "error" }),
//       }
//     );
//   };

//   return (
//     <Button
//       label="Mark Completed"
//       onClick={handleClick}
//       disabled={record.status === "COMPLETED"}
//     />
//   );
// };

export const AppointmentList = () => (
  <List>
    <Datagrid>
      <DateField source="start_date" showTime />
      <TextField source="patient_first_name" />
      <TextField source="patient_last_name" />
      <EmailField source="patient_email" />
      <TextField source="patient_phone" />
      <TextField source="patient_address" />q
      <TextField source="patient_pesel" />
      <ReferenceField source="doctor_id" reference="doctors">
        <TextField source="first_name"/>
        <span> </span>
        <TextField source="last_name"/>
      </ReferenceField>
      <TextField source="status" />
      {/* <QuickStatusButton /> */}
    </Datagrid>
  </List>
);

export const AppointmentEdit = () => (
    <Edit>
      <SimpleForm>
        <DateTimeInput source="start_date" />
        <TextInput source="patient_first_name" />
        <TextInput source="patient_last_name" />
        <TextInput source="patient_email" type="email" />
        <TextInput source="patient_phone" />
        <TextInput source="patient_address" />
        <TextInput source="patient_pesel" />
        <ReferenceInput source="doctor_id" reference="doctors">
          <SelectInput
              optionText={(record) => `${record.first_name} ${record.last_name}`}
          />
        </ReferenceInput>
        <SelectInput
            source="status"
            choices={[
              { id: "SCHEDULED", name: "Scheduled" },
              { id: "COMPLETED", name: "Completed" },
              { id: "CANCELLED", name: "Cancelled" },
              { id: "TO_BE_CONFIRMED", name: "To be Confirmed" },
            ]}
        />
      </SimpleForm>
    </Edit>
);
