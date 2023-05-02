import {apiInstance} from "./AxiosViewerService.ts";
import {GetVehiclesRequest, GetVehiclesResponse, VehiclesService} from "../../defs/VehiclesService.tsx";

export default class AxiosVehicleService implements VehiclesService {
    async getVehicles(request: GetVehiclesRequest): Promise<GetVehiclesResponse> {
        console.log(JSON.stringify(request))
        const sorting = {
            column: request.sortingColumn,
            asc: request.isAscending,
        }
        const newParams = {
            pageSize: request.pageSize,
            pageNumber: request.pageNumber,
            sorting: JSON.stringify(sorting),
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

}