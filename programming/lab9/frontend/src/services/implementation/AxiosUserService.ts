import UserService from "../definitions/UserService";
import axios, {AxiosInstance} from "axios";
import * as lab9 from "lab9";
import ShowUserResponse = lab9.lab9.common.responses.ShowUserResponse;
import CoordinatesDTO = lab9.lab9.common.dto.CoordinatesDTO;
import VehicleType = lab9.lab9.common.vehicle.VehicleType;
import FuelType = lab9.lab9.common.vehicle.FuelType;
import ShowVehicleResponse = lab9.lab9.common.responses.ShowVehicleResponse;
import ShowVehiclesRequest = lab9.lab9.common.requests.ShowVehiclesRequest;
import ShowVehiclesResponse = lab9.lab9.common.responses.ShowVehiclesResponse;
import VehicleSorting = lab9.lab9.common.requests.VehicleSorting;
import VehicleColumn = lab9.lab9.common.dto.VehicleColumn;

export class AxiosUserService implements UserService {
    private axiosInstance: AxiosInstance = axios.create()
    private baseUrl = "/api"

    async getMe(): Promise<ShowUserResponse> {
        const response = await this.axiosInstance.get<ShowUserResponse>(`${this.baseUrl}/me`)
        return response.data
    }

    async tryToLogin(username: string, password: string): Promise<void> {
        let bodyFormData = new FormData()
        bodyFormData.append("username", username)
        bodyFormData.append("password", password)
        const response = await this.axiosInstance.postForm(
            `${this.baseUrl}/login`,
            bodyFormData,
        )
        return
    }

    async createVehicle(name: string, x: number, y: number, enginePower: number, vehicleType: string, fuelType: string): Promise<void> {
        const coordinates = new CoordinatesDTO(x, y)
        const vehType = VehicleType.valueOf(vehicleType)
        const fType = FuelType.valueOf(fuelType)
        const vehicle = {
            "name": name,
            "coordinates": {
                "x": coordinates.x,
                "y": coordinates.y
            },
            "enginePower": enginePower,
            "vehicleType": vehType.name,
            "fuelType": fType.name,
        }
        const response = await this.axiosInstance.post(`${this.baseUrl}/vehicles`, vehicle)
        return
    }

    async showVehicles(pageNumber: number, pageSize: number, sortHeader: number, isAscending: boolean): Promise<ShowVehiclesResponse> {
        const request = {
            pageSize: pageSize,
            pageNumber: pageNumber,
            sortingColumn: sortHeader,
            isAscending: isAscending
        }
        const response = await this.axiosInstance.get(`${this.baseUrl}/vehicles`, {
            params: request,
        })
        return response.data
    }


}