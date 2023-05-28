import {Filter} from "./Filter.ts";
import {VehicleColumn} from "../Vehicle.ts";

export class NumberFilter extends Filter {
    lowerBound: number | null
    upperBound: number | null
    column: VehicleColumn

    constructor(column: VehicleColumn, lowerBound: number | null, upperBound: number | null) {
        super()
        this.column = column
        this.lowerBound = lowerBound
        this.upperBound = upperBound
    }

    toJSON() {
        return {
            "type": "lab9.backend.adapter.in.web.dto.GetVehiclesFilterDTO.NumberFilter",
            "field": this.column.toString(),
            "lowerBound": this.lowerBound,
            "upperBound": this.upperBound
        }
    }
}