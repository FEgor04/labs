import {Vehicle} from "./vehicles/Vehicle.ts";
import {Filter} from "./vehicles/filter/Filter.ts";

export interface VehiclesService {
    getVehicles(request: GetVehiclesRequest): Promise<GetVehiclesResponse>

    createVehicle(request: CreateVehicleRequest): Promise<void>

    deleteVehicle(vehicleId: number): Promise<void>

    updateVehicle(vehicleId: number, data: CreateVehicleRequest): Promise<void>

    getVehicle(vehicleId: number): Promise<Vehicle>
}

export type GetVehiclesRequest = {
    pageNumber: number
    pageSize: number
    sortingColumn: number
    isAscending: boolean,
    filter?: Filter | null
}

export type CreateVehicleRequest = {
    name: string
    x: number
    y: number | null
    enginePower: number
    vehicleType: string
    fuelType: string
}

export type GetVehiclesResponse = {
    totalElements: number
    totalPages: number
    vehicles: Array<Vehicle>
}

