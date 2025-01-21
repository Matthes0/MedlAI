import {
    List,
    Datagrid,
    TextField,
    DateField,
    Create,
    Edit,
    SimpleForm,
    DateInput,
    ReferenceInput,
    SelectInput,
    required,
    ReferenceField, TextInput,
} from "react-admin";

const absenceReasons = [
  { id: "VACATION", name: "Vacation" },
  { id: "L4", name: "L4" },
  { id: "CHILDCARE", name: "Childcare" },
  { id: "OTHER", name: "Other" },
];

export const AbsenceList = () => (
  <List>
    <Datagrid rowClick="edit">
      <ReferenceField source="doctor_id" reference="doctors">
        <TextField source="first_name"/>
          <span> </span>
        <TextField source="last_name"/>
      </ReferenceField>
      <DateField source="start_date" />
      <DateField source="end_date" />
      <TextField source="absence_reason" />
    </Datagrid>
  </List>
);


const AbsenceForm = () => (
    <SimpleForm>
        <ReferenceInput source="doctor_id" reference="doctors">
            <SelectInput
                optionText={(record) => `${record.first_name} ${record.last_name}`}
            />
        </ReferenceInput>
        <DateInput source="start_date" validate={required()} />
        <DateInput source="end_date" validate={required()} />
        <SelectInput
            source="absence_reason"
            choices={absenceReasons}
            validate={required()}
        />
    </SimpleForm>
);


export const AbsenceCreate = () => (
  <Create>
    <AbsenceForm />
  </Create>
);

export const AbsenceEdit = () => (
  <Edit>
    <AbsenceForm />
  </Edit>
);
