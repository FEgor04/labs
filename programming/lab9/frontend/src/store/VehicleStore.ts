import AxiosVehicleService from "../api/implementation/axios/AxiosVehicleService.ts";
import {action, makeAutoObservable, observable} from "mobx";
import {CreateVehicleRequest, Filter, GetVehiclesResponse, VehiclesService} from "../api/defs/VehiclesService.ts";
import UpdateVehicleStore from "./UpdateVehicleStore.ts";
import {notification} from "antd";
import globalStore from "./index.ts";

export default class VehicleStore {
    private vehiclesService: VehiclesService = new AxiosVehicleService()
    currentPage = 1
    pageSize = 10
    vehicles: GetVehiclesResponse | null = null
    errors: string | null = null
    isLoading = true
    sortColumnNumber = 0
    isSortAscending = true
    filter: Filter | null = null
    updateVehicleStore: UpdateVehicleStore = new UpdateVehicleStore(this.vehiclesService)



    constructor() {
        makeAutoObservable(this, {
            setPageSize: action.bound,
            setCurrentPage: action.bound,
            updateData: action.bound,
            setSortingColumn: action.bound,
            setIsSortingAscending: action.bound,
            setSorting: action.bound,
            currentPage: observable,
            addFilter: action.bound,
            clearFilters: action.bound,
            deleteVehicle: action.bound,
            filter: observable,
        })
    }

    addFilter(filter: Filter) {
        this.filter = filter
    }

    createVehicle(request: CreateVehicleRequest) {
        this.vehiclesService.createVehicle(request).then(() => {
        }).catch((e) => {
            console.log(`Could not create vehicle: ${e}`)
        })
    }

    clearFilters() {
        this.filter = null
    }

    setSorting(column: number, direction: 'descend' | 'ascend' | null | undefined) {
        if(!direction) {
            this.setSortingColumn(0)
            this.setIsSortingAscending(true)
            this.updateData()
        } else {
            this.setSortingColumn(column)
            this.setIsSortingAscending(direction == "ascend")
        }
    }

    setCurrentPage(page: number) {
        this.currentPage = page
    }

    setPageSize(size: number) {
        this.pageSize = size
    }

    setVehicles(vehicles: GetVehiclesResponse) {
         this.vehicles = vehicles
    }

    setErrors(errors: string) {
        this.errors = errors
    }

    setIsLoading(isLoading: boolean) {
        this.isLoading = isLoading
    }

    setSortingColumn(column: number) {
        this.sortColumnNumber = column
    }

    setIsSortingAscending(isAscending: boolean) {
        this.isSortAscending = isAscending
    }

    updateData() {
        this.setIsLoading(true)
        this.vehiclesService.getVehicles({
            pageNumber: this.currentPage - 1,
            pageSize: this.pageSize,
            isAscending: this.isSortAscending,
            sortingColumn: this.sortColumnNumber,
            filter: this.filter
        }).then((response) => {
            this.setVehicles(response)
        }).catch((e) => {
            this.setErrors(e)
        }).finally(() => {
            this.setIsLoading(false)
        })
    }

    deleteVehicle(vehicleId: number) {
        this.vehiclesService.deleteVehicle(vehicleId).then(r => {
        }).catch(e => {
            notification.open({
                message: "Ошибка!",
                type: "error"
            })
        })
    }
}