import {
    List,
    Datagrid,
    TextField,
    EmailField,
    Create,
    Edit,
    SimpleForm,
    TextInput,
    SelectInput,
    required,
} from "react-admin";

const specializations = [
    {id: "GENERAL", name: "General"},
    {id: "CARDIOLOGIST", name: "Cardiologist"},
    {id: "DERMATOLOGIST", name: "Dermatologist"},
    {id: "NEUROLOGIST", name: "Neurologist"},
    {id: "PEDIATRICIAN", name: "Pediatrics"},
    {id: "PSYCHIATRIST", name: "Psychiatry"},
    {id: "OPHTHALMOLOGIST", name: "Ophthalmologist"},
    {id: "GASTROENTEROLOGIST", name: "Gastroenterologist"},
    {id: "ENDOCRINOLOGIST", name: "Endocrinologist"}
];


export const DoctorList = () => (
    <List>
        <Datagrid rowClick="edit">
            <TextField source="id"/>
            <TextField source="first_name"/>
            <TextField source="last_name"/>
            <TextField source="email"/>
            <TextField source="phone"/>
            <TextField source="specialization"/>
        </Datagrid>
    </List>
);

const DoctorForm = () => (
    <SimpleForm>
        <TextInput source="first_name" validate={required()}/>
        <TextInput source="last_name" validate={required()}/>
        <TextInput source="email" type="email" validate={required()}/>
        <TextInput source="phone" validate={required()}/>
        <SelectInput
            source="specialization"
            choices={specializations}
            validate={required()}
        />
    </SimpleForm>
);

export const DoctorCreate = () => (
    <Create>
        <DoctorForm/>
    </Create>
);

export const DoctorEdit = () => (
    <Edit>
        <DoctorForm/>
    </Edit>
);
