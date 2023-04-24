import {action, makeObservable, observable} from "mobx";
import {Viewer} from "../shared/Viewer.ts";
import {ViewerService} from "../api/defs/ViewerService.tsx";
import AxiosViewerService from "../api/implementation/axios/AxiosViewerService.ts";

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

    setErrors(errors: Error) {
        this.errors = errors.message
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

    signIn(username: string, password: string): void {
        this.service.signIn(username, password).then(() => {
            this.service.getMe().then((me) => {
                this.setViewer(me)
                this.setIsSignedIn(true)
            }).catch((e) => {
                this.setErrors(e)
            })
        }).catch((error) => {
            this.setErrors(error)
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
}