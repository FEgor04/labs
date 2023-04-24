import {ViewerService} from "../../defs/ViewerService.tsx";
import {Viewer} from "../../../shared/Viewer.ts";

export default class StubViewerService implements ViewerService {
    async getMe(): Promise<Viewer> {
        return {
            id: 1337,
            username: "Test"
        } as Viewer
    }

    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    async signIn(username: string, password: string): Promise<void> {
        console.log(username)
        console.log(password)
        // return Promise.reject("huy")
        return Promise.resolve()
    }

    async logOut(): Promise<void> {
        console.log("Logged out")
        return
    }

}