import {Pagination} from "flowbite-react";

export type VehicleTableControlsProps = {
    pageSize: number,
    pageNumber: number,
    totalPages: number
    onPageChange: (page: number) => void
}

const VehicleTableControls = (props: VehicleTableControlsProps) => {
    return (
        <nav className="flex items-center justify-between p-4 dark:bg-gray-700 bg-gray-50 text-gray-700 dark:text-gray-400" aria-label="Table navigation">
            <span className="text-sm font-normal text-gray-500 dark:text-gray-400">Showing page <span
                className="font-semibold text-gray-900 dark:text-white">{props.pageNumber}</span> of <span
                className="font-semibold text-gray-900 dark:text-white">{props.totalPages}</span></span>

            <Pagination currentPage={props.pageNumber} onPageChange={props.onPageChange} totalPages={props.totalPages} />
        </nav>
    )
}
export default VehicleTableControls