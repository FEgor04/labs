export class Notification {
    type: NotificationType
}

export type NotificationType = "VehicleDeleted" | "VehicleUpdated" | "AccessChanged" | "NewVehicle"

export class VehicleDeletedNotification extends Notification {
    type: NotificationType = "VehicleDeleted"
    vehicleId: number
    deletedBy: number
}

export class VehicleUpdatedNotification extends Notification{
    type: NotificationType = "VehicleUpdated"
    vehicleId: number
    updatedBy: number
}

export class AccessChangedNotification {
    type: NotificationType = "AccessChanged"
    fromUser: number
    toUser: number
    canEdit: boolean
    canDelete: boolean
}

export class NewVehicleNotification {
    type: NotificationType = "NewVehicle"
    vehicleId: number
    authorId: number
}

