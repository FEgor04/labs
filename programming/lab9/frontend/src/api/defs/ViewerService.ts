import {Viewer} from "../../shared/Viewer.ts";

export interface ViewerService {
    signIn(username: string, password: string): Promise<void>
    signUp(username: string, password: string): Promise<void>
    getMe(): Promise<Viewer>
    logOut(): Promise<void>
}