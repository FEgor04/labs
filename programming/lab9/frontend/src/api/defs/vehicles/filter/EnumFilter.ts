import {FuelType, VehicleColumn, VehicleType} from "../Vehicle.ts";
import {Filter} from "./Filter.ts";

export class EnumFilter<T extends (VehicleType) | (FuelType | null)> extends Filter {
    column: VehicleColumn;
    values: T[]
    type: "VehicleType" | "FuelType"

    constructor(column: VehicleColumn, values: T[], type: "VehicleType" | "FuelType") {
        super();
        this.column = column
        this.values = values
        this.type = type
    }

    toJSON() {
        return {
            "field": this.column,
            "type": `lab9.backend.adapter.in.web.dto.GetVehiclesFilterDTO.${this.type}Filter`,
            "values": this.values
        }
    }
}