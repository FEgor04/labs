import {
    NotificationCallback,
    NotificationService,
    parseNotifications
} from "../defs/notifications/NotificationService.tsx";
import * as SockJS from "sockjs-client";
import {Client, IMessage} from "@stomp/stompjs";

export class WebSocketNotificationService implements NotificationService {
    private callbacks: NotificationCallback[] = []
    private client: Client

    addCallback(callback: NotificationCallback): void {
        this.callbacks.push(callback)
    }

    constructor() {
        this.setup()
    }

    handleMessage(msg: IMessage) {
        console.log(`Message from websocket: ${msg.body}`)
        const notification = parseNotifications(msg.body)
        this.callbacks.forEach((cb) => {
            cb(notification)
        })
    }

    private getBrokerURL(): string {
        const port = window.location.port ? `:${window.location.port}` : ""
        const isSecure = window.location.protocol == "https:"
        const hostname = window.location.hostname
        return `${isSecure ? "wss" : "ws"}://${hostname}${port}/websocket`
    }

    private setup() {
        this.client = new Client({
            brokerURL: this.getBrokerURL(),
            onConnect: () => {
                this.client.subscribe("/topic/notifications", message => {
                    this.handleMessage(message)
                })
            }
        })
        this.client.activate()
    }

    reconnect(): void {
        this.client.forceDisconnect()
        this.setup()
    }

}