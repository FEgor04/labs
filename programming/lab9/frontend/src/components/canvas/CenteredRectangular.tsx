import {Rect} from "react-konva";

export type VehicleInternalCanvasProps = {
    x: number
    y: number
    width: number
    height: number
    fill: string
    stroke: string
    onClick?: (() => void) | null | undefined
}

const CenteredRectangular = (props: VehicleInternalCanvasProps) => {
    return (
        <Rect
            height={props.height}
            width={props.width}
            x={props.x - props.width / 2}
            y={props.y - props.height / 2}
            fill={props.fill}
            stroke={props.stroke}
            onClick={props.onClick}
        />
    )
}


export default CenteredRectangular