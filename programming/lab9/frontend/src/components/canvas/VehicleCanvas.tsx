import {Vehicle} from "../../api/defs/vehicles/Vehicle.ts";
import {Circle, Image, Ring, Wedge} from "react-konva";
import useImage from "use-image"
import Konva from "konva";
import CenteredRectangular, {VehicleInternalCanvasProps} from "./CenteredRectangular.tsx";
import {useNavigate} from "react-router-dom";
import globalStore from "../../store";
import {notification} from "antd";
import {useTranslation} from "react-i18next";

export type VehicleCanvasProps = {
    vehicle: Vehicle
    canvasWidth: number
    canvasHeight: number
    rangeX: number
    rangeY: number
}
const VehicleCanvas = ({
                           vehicle: veh,
                           canvasWidth: width,
                           canvasHeight: height,
                           rangeX,
                           rangeY,
                       }: VehicleCanvasProps) => {
    const {t} = useTranslation()
    const navigate = useNavigate()
    const transformedX = veh.coordinates.x / rangeX * width + width / 2
    const transformedY = -veh.coordinates.y / rangeY * height + height / 2
    const colorGenerator = (id: number): string => {
        // генерируем числа от 17 до 255 (в hex это будет два символа)
        // для устойчивости генерации:
        // берем id по модулю 239 (получаются числа из [0..238])
        // прибавляем к ним 17 (получаются числа из [17..255],
        // но тогда числа будут ~~скучные~~ и похожи друг на друга
        // для этого умножим на какое-нибудь число, но оно должно быть простым,
        // чтобы не дай бог что-нибудь не сократилось
        const r = (id * 107 % 238 + 17).toString(16)
        const g = (id * 163 % 238 + 17).toString(16)
        const b = (id * 89 % 238 + 17).toString(16)
        const color = `#${r}${g}${b}`
        console.log(`mapped color #${id} -> ${color}`)
        return color
    }
    const size = 50

    const props: VehicleInternalCanvasProps = {
        width: size,
        height: size,
        x: transformedX,
        y: transformedY,
        fill: colorGenerator(veh.creatorId),
        onClick: () => {
            if (veh.canEdit) {
                navigate(`/vehicles/${veh.id}`)
            } else {
                notification.open({
                    message: t('visualizer.canNotEdit.message'),
                    description: t('visualizer.canNotEdit.description', {creatorId: veh.creatorId, vehId: veh.id}),
                    type: "warning",
                })
            }
        },
        stroke: "black"
    }
    let element: JSX.Element
    if (veh.vehicleType == "BICYCLE") {
        element = (
            <Circle
                {...props}
            />
        )
    }
    else if(veh.vehicleType == "BOAT") {
        element = (
            <CenteredRectangular {...props}/>
        )
    }
    else if(veh.vehicleType == "PLANE") {
        element = (
            <Ring innerRadius={10} outerRadius={size} {...props}/>
        )
    }
    else if(veh.vehicleType == "SUBMARINE") {
        element = (
            <Wedge
                angle={veh.enginePower % 180 + 45}
                radius={size}
                {...props}
            />
        )
    }

    return element
}
export default VehicleCanvas