import {
    AccessChangedNotification,
    NewVehicleNotification,
    VehicleDeletedNotification,
    VehicleUpdatedNotification,
    Notification,
} from "./NotificationType.tsx";

export interface NotificationService {
    addCallback(callback: NotificationCallback): void
    reconnect(): void
}

export type NotificationCallback = (notification: Notification) => void


export const parseNotifications = (rawJson: string): Notification => {
    const json = JSON.parse(rawJson)
    if(json["type"].endsWith("VehicleCreated")) {
        return {
            vehicleId: json["vehicleId"],
            authorId: json["authorId"],
            type: "NewVehicle",
        } as NewVehicleNotification
    }
    if(json["type"].endsWith("AccessChanged")) {
        return {
            fromUser: json["fromUser"],
            toUser: json["toUser"],
            canEdit: json["canEdit"],
            canDelete: json["canDelete"],
            type: "AccessChanged",
        } as AccessChangedNotification
    }
    if(json["type"].endsWith("VehicleDeleted")) {
        return {
            vehicleId: json["vehicleId"],
            deletedBy: json["deletedBy"],
            type: "VehicleDeleted",
        } as VehicleDeletedNotification
    }
    if(json["type"].endsWith("VehicleUpdated")) {
        return {
            vehicleId: json["vehicleId"],
            updatedBy: json["updatedBy"],
            type: "VehicleUpdated",
        } as VehicleUpdatedNotification
    }
    throw Error("Deserialization error")
}