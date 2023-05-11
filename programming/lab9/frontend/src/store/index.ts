import ViewerStore from "./ViewerStore.ts";
import VehicleStore from "./VehicleStore.ts";
import {makeAutoObservable} from "mobx";
import NotificationsStore from "./NotificationsStore.ts";

class GlobalStore {
    viewerStore: ViewerStore = new ViewerStore()
    vehicleStore: VehicleStore = new VehicleStore()
    notificationsStore = new NotificationsStore((_) => {
        this.vehicleStore.updateData()
    })

    constructor() {
        makeAutoObservable(this)
    }
}

const globalStore = new GlobalStore()
export default globalStore