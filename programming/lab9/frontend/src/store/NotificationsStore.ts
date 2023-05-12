import {makeAutoObservable} from "mobx";
import {NotificationCallback, NotificationService} from "../api/defs/notifications/NotificationService.tsx";
import {Notification} from "../api/defs/notifications/NotificationType.tsx";
import {SSENotificationService} from "../api/implementation/SSENotificationService.ts";
import {OpenNotification} from "../utils/OpenNotification.ts";
import {WebSocketNotificationService} from "../api/implementation/WebSocketNotificationService.ts";

export default class NotificationsStore {
    private notificationService: NotificationService

    notifications: Notification[] = []

    constructor(notificationCallback: NotificationCallback) {
        this.notificationService = new WebSocketNotificationService()
        makeAutoObservable(this)
        this.notificationService.addCallback((notif) => {
            this.notifications.push(notif)
        })
        this.notificationService.addCallback((notif) => {
            OpenNotification(notif)
        })
        this.notificationService.addCallback(notificationCallback)
    }

    reconnect() {
        this.notificationService.reconnect()
    }
    addCallback(callback: NotificationCallback) {
        this.notificationService.addCallback(callback)
    }

}