import * as lab9 from "../../../../common/build/productionLibrary";
import ShowUserResponse = lab9.lab9.common.responses.ShowUserResponse;
import ShowVehicleResponse = lab9.lab9.common.responses.ShowVehicleResponse;
import ShowVehiclesResponse = lab9.lab9.common.responses.ShowVehiclesResponse;

interface UserService {
    getMe(): Promise<ShowUserResponse>

    tryToLogin(username: String, password: String): Promise<void>

    createVehicle(name: string, x: number, y: number, enginePower: number, vehicleType: string, fuelType: string): Promise<void>

    showVehicles(pageNumber: number, pageSize: number, sortHeader: number, isAscending: boolean): Promise<ShowVehiclesResponse>
}

export default UserService