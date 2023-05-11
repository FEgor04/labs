import {NotificationCallback, NotificationService} from "../defs/notifications/NotificationService.tsx";
import RSocketWebSocketClient, {WebsocketClientTransport} from "rsocket-websocket-client";

import {
    RSocketConnector,
} from "rsocket-core";

export class RSocketNotificationService implements NotificationService {
    private connector = new RSocketConnector({
        transport: new WebsocketClientTransport({
            url: "ws://localhost:8080/rsocket",
        }),
    })

    private callbacks: NotificationCallback[] = []

    addCallback(callback: NotificationCallback): void {
        this.callbacks.push(callback)
    }

    constructor() {
        this.connector.connect().then(r => {
            r.requestStream(
                {
                    data: Buffer.from("Hello"),

                },
                3,
                {
                    onError: (e) => {
                        alert(e)
                    },
                    onNext: (payload, isComplete) => {
                        console.log(payload, isComplete)
                    },
                    onComplete: () => {
                    },
                    onExtension: () => {
                    }
                }
            )
        })
    }
}