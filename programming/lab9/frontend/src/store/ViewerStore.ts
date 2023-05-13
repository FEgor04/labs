import {action, makeObservable, observable} from "mobx";
import {Viewer} from "../shared/Viewer.ts";
import {ViewerService} from "../api/defs/ViewerService.ts";
import AxiosViewerService from "../api/implementation/axios/AxiosViewerService.ts";
import globalStore from "./index.ts";
import {AxiosError} from "axios";

export default class ViewerStore {
    viewer?: Viewer | null
    service: ViewerService = new AxiosViewerService()
    isSignedIn = false

    errors: string | undefined = undefined

    constructor() {
        this.service.getMe()
            .then(viewer => {
                this.setIsSignedIn(true)
                this.setViewer(viewer)
            })
            .catch((e) => {
                this.setIsSignedIn(false)
                this.setViewerToNull()
                console.log(`Could not get me at INIT: ${e}`)
            })
        makeObservable(this, {
            viewer: observable,
            signIn: action,
            logOut: action,
            isSignedIn: observable,
            errors: observable,
            setErrors: action,
            setIsSignedIn: action,
            setViewerToNull: action,
            setViewer: action,
        })
    }

    setErrors(errors: string) {
        this.errors = errors
    }

    setIsSignedIn(isSignedIn: boolean) {
        this.isSignedIn = isSignedIn
    }

    setViewer(viewer: Viewer) {
        this.viewer = viewer
    }

    setViewerToNull() {
        this.viewer = null
    }

    signIn(username: string, password: string, onSuccess: () => void): void {
        this.service.signIn(username, password).then(() => {
            this.service.getMe().then((me) => {
                this.setViewer(me)
                this.setIsSignedIn(true)
                globalStore.notificationsStore.reconnect()
                onSuccess()
            }).catch((e) => {
                console.log(e)
                this.setErrors(e)
            })
        }).catch((e: AxiosError) => {
            if (e.message.includes("401")) {
                this.setErrors("errors.signIn.401")
            } else if (e.message.includes("500") || e.message.includes("502")) {
                this.setErrors("errors.signIn.5xx")
            } else if (e.message.includes("400")) {
                this.setErrors("errors.signIn.400")
            } else {
                this.setErrors(e.message)
            }
        })
    }

    logOut(): void {
        this.service.logOut().then(() => {
            this.setViewerToNull()
            this.setIsSignedIn(false)
        }).catch((e) => {
            console.log(`Error on logging out: ${e}`)
            this.errors = e
        })
    }

    signUp(username: string, password: string, onSuccess: () => void): void {
        this.service.signUp(username, password).then(() => {
            this.service.signIn(username, password).then(() => {
                this.service.getMe().then((me) => {
                    this.setViewer(me)
                    onSuccess()
                }).catch((e: Error) => {
                    this.setErrors(e.message)
                })
            }).catch((e: Error) => {
                this.setErrors(e.message)
            })
        }).catch((e: Error) => {
            if (e.message.includes("409")) {
                this.setErrors("errors.signUp.409")
            } else if (e.message.includes("500") || e.message.includes("502")) {
                this.setErrors("errors.signUp.5xx")
            } else if (e.message.includes("400")) {
                this.setErrors("errors.signUp.400")
            } else {
                this.setErrors(e.message)
            }
        })
    }
}