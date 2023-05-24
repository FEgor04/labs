import {Pagination, Spin} from "antd";
import globalStore from "../../store";
import {observer} from "mobx-react";
import {useEffect, useRef} from "react";
import {Circle, Layer, Line, Rect, Stage, Star, Text} from "react-konva";
import Konva from "konva";
import CenteredRectangular from "../../components/canvas/CenteredRectangular.tsx";
import VehicleCanvas from "../../components/canvas/VehicleCanvas.tsx";

const VisualizerPage = observer(() => {
    const vehiclesStore = globalStore.vehicleStore
    const viewerStore = globalStore.viewerStore

    const ref = useRef<HTMLCanvasElement>(null)
    useEffect(() => {
        if (viewerStore.isSignedIn) {
            vehiclesStore.updateData()
        }
    }, [vehiclesStore.pageSize, vehiclesStore.currentPage, vehiclesStore.sortColumnNumber, vehiclesStore.isSortAscending, viewerStore.isSignedIn, vehiclesStore.filter])

    useEffect(() => {
        const canvas = ref.current
        if (canvas == null) {
            return
        }
        const context = canvas.getContext('2d')
        vehiclesStore.vehicles.vehicles.forEach((veh) => {
            context.fillRect(veh.coordinates.x, veh.coordinates.y, 10, 10)
        })
    }, [vehiclesStore.vehicles])
    let content: JSX.Element;
    if (vehiclesStore.isLoading) {
        content = (<Spin/>)
    } else {
        const width = window.innerWidth * 0.8
        const height = window.innerHeight * 0.8
        const rangeX = 2 * Math.abs(vehiclesStore.vehicles.vehicles.reduce((prev, cur, _, __) => {
            if (Math.abs(prev.coordinates.x) < Math.abs(cur.coordinates.x)) {
                return cur
            }
            return prev
        }).coordinates.x) * 1.25
        const rangeY = 2 * Math.abs(vehiclesStore.vehicles.vehicles.reduce((prev, cur, _, __) => {
            if (Math.abs(prev.coordinates.y) < Math.abs(cur.coordinates.y)) {
                return cur
            }
            return prev
        }).coordinates.y) * 1.25
        console.log(width, height, rangeX, rangeY)

        content = (
            <Stage
                width={width}
                height={height}
            >
                <Layer>
                    {/*<CenteredRectangular*/}
                    {/*    x={width/2}*/}
                    {/*    y={height/2}*/}
                    {/*    fill="black"*/}
                    {/*    height={10}*/}
                    {/*    width={10}*/}
                    {/*/>*/}
                    <Line
                        x={0}
                        y={height / 2}
                        points={[0, 0, width, 0]}
                        tension={0.7}
                        stroke="black"
                    />
                    <Line
                        x={width / 2}
                        y={0}
                        points={[0, 0, 0, height]}
                        tension={0.7}
                        stroke="black"
                    />
                    {vehiclesStore.vehicles.vehicles.map((veh) => {
                        return (
                            <VehicleCanvas key={veh.id} vehicle={veh} canvasWidth={width} canvasHeight={height} rangeX={rangeX}
                                           rangeY={rangeY}/>
                        )
                    })}
                </Layer>
            </Stage>
        )
    }

    return (

        <div style={{justifyContent: "center", margin: "auto"}}>
            <div style={{border: "3px solid black", width: "80%", margin: "auto"}}>
                {content}
            </div>
            <Pagination current={vehiclesStore.currentPage} total={vehiclesStore.vehicles?.totalElements}
                        onChange={(page, pageSize) => {
                            vehiclesStore.setPageSize(pageSize)
                            vehiclesStore.setCurrentPage(page)
                        }}/>
        </div>
    )
})

export default VisualizerPage