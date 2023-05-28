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

export enum VehicleColumn {
    ID = "ID",
    NAME = "NAME",
    X = "X",
    Y = "Y",
    CREATION_DATE = "CREATION_DATE",
    ENGINE_POWER = "ENGINE_POWER",
    VEHICLE_TYPE = "VEHICLE_TYPE",
    FUEL_TYPE = "FUEL_TYPE",
    CREATOR_ID = "CREATOR_ID"
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