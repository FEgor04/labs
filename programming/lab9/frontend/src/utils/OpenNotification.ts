import {notification} from "antd";
import {
    AccessChangedNotification,
    NewVehicleNotification,
    Notification, VehicleDeletedNotification, VehicleUpdatedNotification
} from "../api/defs/notifications/NotificationType.tsx";
import i18n from '../i18n/i18n.ts'

export const OpenNotification = (notif: Notification): void => {
    if (notif.type == "AccessChanged") {
        openAccessChangedNotification(notif as AccessChangedNotification)
    } else if (notif.type == "NewVehicle") {
        openNewVehicleNotification(notif as NewVehicleNotification)
    } else if (notif.type == "VehicleDeleted") {
        openVehicleDeletedNotification(notif as VehicleDeletedNotification)
    } else if (notif.type == "VehicleUpdated") {
        openVehicleUpdatedNotification(notif as VehicleUpdatedNotification)
    }
}

const openAccessChangedNotification =
    (notif: AccessChangedNotification): void => {
        const t = i18n.t
        notification.open({
            type: "info",
            message: t(`notification.accessChanged.message`),
            description: t(`notification.accessChanged.description`, {
                fromUser: notif.fromUser,
                toUser: notif.toUser,
                canEdit: notif.canEdit,
                canDelete: notif.canDelete
            }),
        })
    }

const openNewVehicleNotification =
    (notif: NewVehicleNotification): void => {
        const t = i18n.t
        notification.open({
            type: "info",
            message: t(`notification.newVehicle.message`),
            description: t(`notification.newVehicle.description`, {
                authorId: notif.authorId,
                vehicleId: notif.vehicleId
            }),
        })
    }
const openVehicleUpdatedNotification =
    (notif: VehicleUpdatedNotification): void => {
        const t = i18n.t
        notification.open({
            type: "info",
            message: t(`notification.vehicleUpdated.message`),
            description: t(`notification.vehicleUpdated.description`, {
                updatedBy: notif.updatedBy,
                vehicleId: notif.vehicleId
            }),
        })
    }
const openVehicleDeletedNotification =
    (notif: VehicleDeletedNotification): void => {
        const t = i18n.t
        notification.open({
            type: "info",
            message: t(`notification.vehicleDeleted.message`),
            description: t(`notification.vehicleDeleted.description`, {
                deletedBy: notif.deletedBy,
                vehicleId: notif.vehicleId
            }),
        })
    }