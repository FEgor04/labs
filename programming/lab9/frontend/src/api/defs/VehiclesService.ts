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

export type Vehicle = {
    id: number
    name: string
    coordinates: Coordinates
    creationDate: Date,
    enginePower: number,
    vehicleType: VehicleType
    fuelType: FuelType | null
    creatorId: number,
    canEdit: boolean,
    canDelete: boolean,
}

export enum VehicleType {
    PLANE = "PLANE",
    BICYCLE = "BICYCLE",
    BOAT = "BOAT",
    SUBMARINE = "SUBMARINE",
}

export const PossibleVehicleTypes: VehicleType[] = [
    VehicleType.PLANE, VehicleType.BICYCLE, VehicleType.BOAT, VehicleType.SUBMARINE
]

export enum FuelType {
    MANPOWER = "MANPOWER",
    GASOLINE = "GASOLINE",
    ELECTRICITY = "ELECTRICITY",
    PLASMA = "PLASMA",
    ANTIMATTER = "ANTIMATTER"
}

export const PossibleFuelTypes: FuelType[] = [
    FuelType.ANTIMATTER, FuelType.GASOLINE, FuelType.ELECTRICITY, FuelType.PLASMA, FuelType.MANPOWER
]

export type Coordinates = {
    x: number
    y: number | null
}

export abstract class Filter {
    abstract column: string
}

abstract class NumberFilter extends Filter {
    abstract lowerBound: number | null
    abstract upperBound: number | null
}

export class XFilter extends NumberFilter {
    column = "x"
    lowerBound: number | null;
    upperBound: number | null;

    constructor(lowerBound: number | null, upperBound: number | null) {
        super();
        this.lowerBound = lowerBound
        this.upperBound = upperBound
    }

    toJSON() {
        return {
            type: "lab9.common.requests.VehicleFilter.XFilter",
            filterColumn: "COORDINATES_X",
            lowerBound: this.lowerBound,
            upperBound: this.upperBound,
        }
    }

}
