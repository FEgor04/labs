import {create} from "zustand";
import {devtools, persist} from "zustand/middleware";
import UserService from "../services/definitions/UserService";
import {AxiosUserService} from "../services/implementation/AxiosUserService";
import * as lab9 from "../../../common/build/productionLibrary";
import ShowUserResponse = lab9.lab9.common.responses.ShowUserResponse;
import {Axios} from "axios";

interface GlobalState {
    isLoggedIn: boolean
    setIsLoggedIn: (isLoggedIn: boolean) => void
    user: ShowUserResponse | null,
    setUser: (user: ShowUserResponse | null) => void,
    service: UserService
}

const useGlobalStore = create<GlobalState>()(
    devtools(
        (set) => ({
            service: new AxiosUserService(),
            isLoggedIn: false,
            setIsLoggedIn: (isLoggedIn) => set(state => ({
                isLoggedIn: isLoggedIn
            })),
            user: null,
            setUser: (user) => set(state => ({
                user: user
            })),
        }),
        {
            name: 'global-state',
        }
    )
)
export default useGlobalStore