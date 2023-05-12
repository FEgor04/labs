import {apiInstance} from "./AxiosViewerService.ts";
import {
    CreateVehicleRequest,
    GetVehiclesRequest,
    GetVehiclesResponse, Vehicle,
    VehiclesService
} from "../../defs/VehiclesService.ts";

const columnNumberToName = (columnNumber: number) => {
    const columnNamesMap = new Map<number, string>
    columnNamesMap.set(0, "ID")
    columnNamesMap.set(1, "NAME")
    columnNamesMap.set(2, "X")
    columnNamesMap.set(3, "Y")
    columnNamesMap.set(4, "CREATION_DATE")
    columnNamesMap.set(5, "ENGINE_POWER")
    columnNamesMap.set(6, "FUEL_TYPE")
    columnNamesMap.set(7, "VEHICLE_TYPE")
    columnNamesMap.set(8, "CREATOR_ID")
    if(columnNamesMap.has(columnNumber)) {
        return columnNamesMap.get(columnNumber)
    }
    return "ID"
}

export default class AxiosVehicleService implements VehiclesService {
    async getVehicles(request: GetVehiclesRequest): Promise<GetVehiclesResponse> {
        const newParams = {
            pageSize: request.pageSize,
            pageNumber: request.pageNumber,
            sortingColumn: columnNumberToName(request.sortingColumn),
            ascending: request.isAscending,
            filter: JSON.stringify(request.filter),
        }
        const response = await apiInstance.get<GetVehiclesResponse>("/vehicles", {
            params: newParams,
            // paramsSerializer: params => {
            //     return qs.stringify(params)
            // }
        })
        return response.data
    }

    async createVehicle(request: CreateVehicleRequest): Promise<void> {
        const response = await apiInstance.post("/vehicles", request)
        return
    }

    async deleteVehicle(vehicleId: number): Promise<void> {
        const response = await apiInstance.delete(`/vehicles/${vehicleId}`)
        return
    }

    async updateVehicle(vehicleId: number, data: CreateVehicleRequest): Promise<void> {
        const response = await apiInstance.put(`/vehicles/${vehicleId}`, data)
        return
    }

    async getVehicle(vehicleId: number): Promise<Vehicle> {
        const response = await apiInstance.get(`/vehicles/${vehicleId}`)
        return response.data
    }

}