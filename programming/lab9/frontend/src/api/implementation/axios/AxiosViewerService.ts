import {ViewerService} from "../../defs/ViewerService.ts";
import {Viewer} from "../../../shared/Viewer.ts";
import axios from "axios";

export const apiInstance = axios.create({
    baseURL: "/api"
})

export default class AxiosViewerService implements ViewerService {
    async getMe(): Promise<Viewer> {
        const response = await apiInstance.get<Viewer>("/me")
        return response.data
    }

    async logOut(): Promise<void> {
        const response = await apiInstance.post("/logout",)
    }

    async signIn(username: string, password: string): Promise<void> {
        const formData = new FormData()
        formData.append("username", username)
        formData.append("password", password)
        // apiInstance.interceptors.request.use((config) => {
        //     config.auth = {
        //         username: username,
        //         password: password
        //     }
        //     return config
        // })
        const response = await apiInstance.postForm("/login", formData)
        return response.data
    }

    async signUp(username: string, password: string): Promise<void> {
        const response = await apiInstance.post("/signup", {
            username,
            password
        })
        return response.data
    }


}