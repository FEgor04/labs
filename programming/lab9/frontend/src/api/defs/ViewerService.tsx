import {Viewer} from "../../shared/Viewer.ts";

export interface ViewerService {
    signIn(username: string, password: string): Promise<void>
    signU(username: string, password: string): Promise<void>
    getMe(): Promise<Viewer>
    logOut(): Promise<void>
}