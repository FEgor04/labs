import {CreateVehicleRequest, Vehicle, VehiclesService} from "../api/defs/VehiclesService.tsx";
import {makeAutoObservable} from "mobx";

export default class UpdateVehicleStore {
    private service: VehiclesService

    constructor(service: VehiclesService) {
        this.service = service
        makeAutoObservable(this)
    }

    errors: string | null = null

    updateVehicle(vehicleId: number, request: CreateVehicleRequest) {
        this.service.updateVehicle(vehicleId, request).then(_ => {
        }).catch(e => {
            this.setErrors(e.toString())
        })
    }

    async getVehicle(vehicleId: number): Promise<Vehicle> {
        return this.service.getVehicle(vehicleId)
    }

    setErrors(error: string) {
        this.errors = error
    }
}