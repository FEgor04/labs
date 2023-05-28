import {CreateVehicleRequest, VehiclesService} from "../api/defs/VehiclesService.ts";
import {makeAutoObservable} from "mobx";
import {Vehicle} from "../api/defs/vehicles/Vehicle.ts";
import VehicleStore from "./VehicleStore.ts";
import {message, notification} from "antd";
import {AxiosError} from "axios";
import i18n from "../i18n/i18n.ts";

export default class UpdateVehicleStore {
    private service: VehiclesService
    private vehiclesStore: VehicleStore
    currentId: number | null = null
    previousVehicle: Vehicle | null = null
    isLoading: boolean = true

    constructor(service: VehiclesService, store: VehicleStore) {
        this.vehiclesStore = store
        this.service = service
        makeAutoObservable(this)
    }

    setLoading(loading: boolean) {
        this.isLoading = loading
    }

    setPreviousVehicle(prev: Vehicle) {
        this.previousVehicle = prev
    }

    setId(id: number) {
        this.currentId = id
        this.setLoading(true)
        this.getVehicle(id).then(veh => {
            this.setPreviousVehicle(veh)
        }) .catch((e: AxiosError) => {
            if(e.response.status == 404) {
                this.setErrors("errors.updateVehicle.404", {id: id})
            }
            else {
                this.setErrors("errors.updateVehicle.unknown", e.toJSON)
            }
        }).finally(() => {
            this.setLoading(false)
        })
    }


    updateVehicle(vehicleId: number, request: CreateVehicleRequest) {
        this.service.updateVehicle(vehicleId, request).then(_ => {
        }).catch((e: AxiosError) => { // да мне вообще ***** на зависимости от внешней библиотеки
            if(e.response.status == 403) {
                this.setErrors('errors.updateVehicle.403', {id: this.previousVehicle.creatorId})
            }
            if(e.response.status == 404) {
                this.setErrors("errors.updateVehicle.404", {id: this.previousVehicle.id})
            }
            if(e.response.status >= 500) {
                this.setErrors("errors.5xx")
            }
        })
    }

    async getVehicle(vehicleId: number): Promise<Vehicle> {
        if (this.vehiclesStore.vehicles != null) {
            const cachedVehicle = this.vehiclesStore.vehicles.vehicles.find(value => value.id == vehicleId)
            if (cachedVehicle) {
                return cachedVehicle
            }
        }
        return this.service.getVehicle(vehicleId)
    }

    setErrors(errorKey: string, params?: any | undefined) {
        const t = i18n.t
        message.open({
            type: "error",
            // @ts-ignore
            content: t(errorKey, params),
        })
    }
}