
export interface VehiclesService {
    getVehicles(request: GetVehiclesRequest) : Promise<GetVehiclesResponse>
}

export type GetVehiclesRequest = {
    pageNumber: number
    pageSize: number
    sortingColumn: number
    isAscending: boolean,
    filter?: Filter | null
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
    fuelType: VehicleFuelType | null,
    creatorId: number,
    canEdit: boolean,
    canDelete: boolean,
}

export type VehicleFuelType = "MANPOWER" | "GASOLINE" | "ELECTRICITY" | "PLASMA" | "ANTIMATTER"
export type VehicleType = "PLANE" | "BICYCLE" | "BOAT" | "SUBMARINE"

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

}
