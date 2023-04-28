import {apiInstance} from "./AxiosViewerService.ts";
import {GetVehiclesRequest, GetVehiclesResponse, VehiclesService} from "../../defs/VehiclesService.tsx";
import * as qs from "qs"

export default class AxiosVehicleService implements VehiclesService {
    async getVehicles(request: GetVehiclesRequest): Promise<GetVehiclesResponse> {
        console.log(JSON.stringify(request))
        const response = await apiInstance.get<GetVehiclesResponse>("/vehicles", {
            params: request,
            paramsSerializer: params => {
                return qs.stringify(params)
            }

        })
        return response.data
    }

}