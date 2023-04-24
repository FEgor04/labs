import ViewerStore from "./ViewerStore.ts";
import VehicleStore from "./VehicleStore.ts";
import {makeAutoObservable} from "mobx";

class GlobalStore {
    viewerStore: ViewerStore = new ViewerStore()
    vehicleStore: VehicleStore = new VehicleStore()

    constructor() {
        makeAutoObservable(this)
    }
}

const globalStore = new GlobalStore()
export default globalStore