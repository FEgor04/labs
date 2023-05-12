import {GetUsersResponse, UsersService} from "../../defs/UsersService.ts";
import {apiInstance} from "./AxiosViewerService.ts";

export class AxiosUsersService implements UsersService {
    async getUsers(pageNumber: number, pageSize: number): Promise<GetUsersResponse> {
        const response = await apiInstance.get<GetUsersResponse>("/users", {
            params: {
                pageNumber,
                pageSize,
            }
        })
        return response.data
    }

    async grantPermissions(userId: number, canEdit: boolean, canDelete: boolean): Promise<void> {
        const response = await apiInstance.put(`/users/grant/${userId}`, {}, {
            params: {
                canEdit: canEdit,
                canDelete: canDelete,
            }
        })
        return
    }

}