import {ViewerService} from "../../defs/ViewerService.tsx";
import {Viewer} from "../../../shared/Viewer.ts";
import axios from "axios";

export const apiInstance = axios.create({
    baseURL: "/api"
})

export default class AxiosViewerService implements ViewerService {
    async getMe(): Promise<Viewer> {
        const response = await apiInstance.get<Viewer>("/me")
        console.log(`Viewer is ${JSON.stringify(response.data)}`)
        return response.data
    }

    async logOut(): Promise<void> {
        const response = await apiInstance.post("/logout",)
        console.log(response)
    }

    signIn(username: string, password: string): Promise<void> {
        const formData = new FormData()
        formData.append("username", username)
        formData.append("password", password)
        return apiInstance.postForm("/login", formData)
    }

}