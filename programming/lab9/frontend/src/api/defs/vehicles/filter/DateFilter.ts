import {Filter} from "./Filter.ts";
import {VehicleColumn} from "../Vehicle.ts";

export class DateFilter extends Filter {
    lowerBound: Date | null
    upperBound: Date | null
    column: VehicleColumn

    constructor(column: VehicleColumn, lowerBound: Date | null, upperBound: Date | null) {
        super()
        this.column = column
        this.lowerBound = new Date(lowerBound)
        this.upperBound = new Date(upperBound)
    }

    toJSON() {
        const json =  {
            "type": "lab9.backend.adapter.in.web.dto.GetVehiclesFilterDTO.DateFilter",
            "field": this.column.toString(),
            "lowerBound": Math.floor(this.lowerBound.getTime() / 1000),
            "upperBound": Math.floor(this.upperBound.getTime() / 1000),
        }
        console.log(json)
        return json
    }
}