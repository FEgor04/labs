import VehicleTableHeader from "./table/VehicleTableHeader";
import VehicleTableControls from "./table/VehicleTableControls";
import TableRow from "./table/TableRow";
import {Table} from "flowbite-react"
import * as lab9 from "lab9";
import ShowVehicleResponse = lab9.lab9.common.responses.ShowVehicleResponse;

export type VehicleTableProps = {
    vehicles: ShowVehicleResponse[]
    pageSize: number,
    pageNumber: number,
    totalPages: number,
    onPageChange: (page: number) => void
    selectedHeader: number
    onSelectHeader: (header: number) => void
    isAscending: boolean
}

const VehicleTable = (props: VehicleTableProps) => {
    return (
        <div
            className="relative overflow-x-auto shadow-md rounded-lg sm:rounded-lg w-4/5 m-auto my-10 bg-white dark:bg-gray-600"
        >
            <Table>
                <VehicleTableHeader isAscending={props.isAscending} onSelectHeader={props.onSelectHeader}
                                    selectedHeader={props.selectedHeader}/>
                <Table.Body className="divide-y">
                    {props.vehicles.map((veh) => (
                        <TableRow vehicle={veh} key={veh.id}/>
                    ))}
                </Table.Body>
            </Table>
            <VehicleTableControls pageNumber={props.pageNumber} pageSize={props.pageSize} totalPages={props.totalPages}
                                  onPageChange={props.onPageChange}/>
        </div>
    )
}

export default VehicleTable

