import {
  DataProvider,
  fetchUtils,
  CreateParams,
  CreateResult,
  UpdateParams,
  UpdateResult,
  DeleteParams,
  DeleteResult,
  GetListParams,
  GetListResult,
  GetOneParams,
  GetOneResult,
  GetManyParams,
  GetManyResult,
  GetManyReferenceParams,
  GetManyReferenceResult,
  UpdateManyParams,
  UpdateManyResult,
  DeleteManyParams,
  DeleteManyResult,
  RaRecord,
} from "react-admin";
import { Absence, Appointment } from "./components/types";

interface HttpClientResponse {
  status: number;
  headers: Headers;
  body: string;
  json: any;
}

const apiUrl = "http://10.50.50.123:8080/admin";
const httpClient = fetchUtils.fetchJson;

export const dataProvider: DataProvider = {
  getList: async <RecordType extends RaRecord>(
    resource: string,
    params: GetListParams
  ): Promise<GetListResult<RecordType>> => {
    const url = `http://10.50.50.123:8080/admin/${resource}/get`;
    const { json } = await httpClient(url);
    return {
      data: json,
      total: json.length,
    };
  },

  getOne: async <RecordType extends RaRecord>(
    resource: string,
    params: GetOneParams
  ): Promise<GetOneResult<RecordType>> => {
    const url = `${apiUrl}/${resource}/${params.id}`;
    const { json } = await httpClient(url);
    return {
      data: json,
    };
  },

  create: async <RecordType extends RaRecord>(
    resource: string,
    params: CreateParams<RecordType>
  ): Promise<CreateResult<RecordType>> => {
    let url = `${apiUrl}/${resource}`;
    if (resource === "doctors") {
      url = `${apiUrl}/doctors/create`;
    }

    if (resource === "absences") {
      const absenceData = params.data as unknown as Absence;
      url = `${apiUrl}/absences/`;
    }
    console.log(params.data);
    const { json } = await httpClient(url, {
      method: "POST",
      body: JSON.stringify(params.data),
    });
    return {
      data: { ...params.data, id: json.id } as RecordType,
    };
  },

  update: async <RecordType extends RaRecord>(
    resource: string,
    params: UpdateParams<RecordType>
  ): Promise<UpdateResult<RecordType>> => {
    let url = `${apiUrl}/${resource}/${params.id}`;
    const method = "PUT";
    let body = JSON.stringify(params.data);

    if (resource === "appointments" && "status" in params.data) {
      const appointmentData = params.data as unknown as Appointment;
      url = `${apiUrl}/appointments/${params.id}/status`;
      body = JSON.stringify({ status: appointmentData.status });
    }

    const { json } = await httpClient(url, {
      method,
      body,
    });
    return {
      data: params.data as RecordType,
    };
  },

  delete: async <RecordType extends RaRecord>(
    resource: string,
    params: DeleteParams<RecordType>
  ): Promise<DeleteResult<RecordType>> => {
    const url = `${apiUrl}/${resource}/${params.id}`;
    await httpClient(url, {
      method: "DELETE",
    });
    return {
      data: params.previousData as RecordType,
    };
  },

  getMany: async <RecordType extends RaRecord>(
    resource: string,
    params: GetManyParams
  ): Promise<GetManyResult<RecordType>> => {
    const queries = params.ids.map((id) =>
      httpClient(`${apiUrl}/${resource}/${id}`).then(({ json }) => json)
    );
    const results = await Promise.all(queries);
    return {
      data: results,
    };
  },

  getManyReference: async <RecordType extends RaRecord>(
    resource: string,
    params: GetManyReferenceParams
  ): Promise<GetManyReferenceResult<RecordType>> => {
    const url = `${apiUrl}/${resource}?${params.target}=${params.id}`;
    const { json } = await httpClient(url);
    return {
      data: json,
      total: json.length,
    };
  },

  updateMany: async <RecordType extends RaRecord>(
    resource: string,
    params: UpdateManyParams<RecordType>
  ): Promise<UpdateManyResult> => {
    const queries = params.ids.map((id) =>
      httpClient(`${apiUrl}/${resource}/${id}`, {
        method: "PUT",
        body: JSON.stringify(params.data),
      })
    );
    await Promise.all(queries);
    return {
      data: params.ids,
    };
  },

  deleteMany: async <RecordType extends RaRecord>(
    resource: string,
    params: DeleteManyParams<RecordType>
  ): Promise<DeleteManyResult> => {
    const queries = params.ids.map((id) =>
      httpClient(`${apiUrl}/${resource}/${id}`, {
        method: "DELETE",
      })
    );
    await Promise.all(queries);
    return {
      data: params.ids,
    };
  },
};
