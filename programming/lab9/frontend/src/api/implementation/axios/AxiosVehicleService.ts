import {apiInstance} from "./AxiosViewerService.ts";
import {
    CreateVehicleRequest,
    GetVehiclesRequest,
    GetVehiclesResponse,
    VehiclesService
} from "../../defs/VehiclesService.tsx";

const columnNumberToName = (columnNumber: number) => {
    const columnNamesMap = new Map<number, string>
    columnNamesMap.set(0, "ID")
    columnNamesMap.set(1, "NAME")
    columnNamesMap.set(2, "X")
    columnNamesMap.set(3, "Y")
    columnNamesMap.set(4, "CREATION_DATE")
    columnNamesMap.set(5, "ENGINE_POWER")
    columnNamesMap.set(6, "FUEL_TYPE")
    columnNamesMap.set(7, "VEHICLE_TYPE")
    columnNamesMap.set(8, "CREATOR_ID")
    if(columnNamesMap.has(columnNumber)) {
        console.log(columnNumber, columnNamesMap.get(columnNumber))
        return columnNamesMap.get(columnNumber)
    }
    return "ID"
}

export default class AxiosVehicleService implements VehiclesService {
    async getVehicles(request: GetVehiclesRequest): Promise<GetVehiclesResponse> {
        console.log(JSON.stringify(request))
        console.log(columnNumberToName(request.sortingColumn))
        const newParams = {
            pageSize: request.pageSize,
            pageNumber: request.pageNumber,
            sortingColumn: columnNumberToName(request.sortingColumn),
            ascending: request.isAscending,
            filter: JSON.stringify(request.filter),
        }
        console.log(`New params is: ${JSON.stringify(newParams)}`)
        const response = await apiInstance.get<GetVehiclesResponse>("/vehicles", {
            params: newParams,
            // paramsSerializer: params => {
            //     return qs.stringify(params)
            // }
        })
        return response.data
    }

    async createVehicle(request: CreateVehicleRequest): Promise<void> {
        const response = await apiInstance.post("/vehicles", request)
        console.log(response)
        return
    }

}