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

    async signIn(username: string, password: string): Promise<void> {
        const formData = new FormData()
        formData.append("username", username)
        formData.append("password", password)
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