import {Filter} from "./Filter.ts";
import {VehicleColumn} from "../Vehicle.ts";

export class PatternFilter extends Filter {
    pattern: string
    column: VehicleColumn

    constructor(column: VehicleColumn, pattern: string) {
        super();
        this.column = column
        this.pattern = pattern
    }

    toJSON() {
        return {
            "field": this.column,
            "pattern": this.pattern,
            "type": "lab9.backend.adapter.in.web.dto.GetVehiclesFilterDTO.PatternFilter"
        }
    }

}