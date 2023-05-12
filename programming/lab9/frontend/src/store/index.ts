import ViewerStore from "./ViewerStore.ts";
import VehicleStore from "./VehicleStore.ts";
import {makeAutoObservable} from "mobx";
import NotificationsStore from "./NotificationsStore.ts";
import {UsersStore} from "./UsersStore.ts";

class GlobalStore {
    viewerStore: ViewerStore = new ViewerStore()
    vehicleStore: VehicleStore = new VehicleStore()
    usersStore = new UsersStore()
    notificationsStore = new NotificationsStore((_) => {
        this.vehicleStore.updateData()
        this.usersStore.updateData()
    })

    constructor() {
        makeAutoObservable(this)
    }
}

const globalStore = new GlobalStore()
export default globalStore