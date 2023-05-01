import * as lab9 from "lab9";
import ShowVehicleResponse = lab9.lab9.common.responses.ShowVehicleResponse;
import {Table} from "flowbite-react"

export type TableRowProps = {
    vehicle: ShowVehicleResponse
}

const TableRow = ({vehicle}: TableRowProps) => {
        return (
        <Table.Row className="dark:bg-gray-600 dark:hover:bg-gray-500 hover:bg-gray-50 dark:border-gray-700">
            <Table.Cell align="right">
                {vehicle.id}
            </Table.Cell>
            <Table.Cell>
                {vehicle.name}
            </Table.Cell>
            <Table.Cell align="right">
                {vehicle.coordinates.x}
            </Table.Cell>
            <Table.Cell align="right">
                {vehicle.coordinates.y}
            </Table.Cell>
            <Table.Cell>
                {vehicle.creationDate}
            </Table.Cell>
            <Table.Cell align="right">
                {vehicle.enginePower}
            </Table.Cell>
            <Table.Cell>
                {vehicle.fuelType}
            </Table.Cell>
            <Table.Cell>
                {vehicle.vehicleType}
            </Table.Cell>
            <Table.Cell align="right">
                {vehicle.creator.id}
            </Table.Cell>
        </Table.Row>
    )
}
export default TableRow