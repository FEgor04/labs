import {useEffect, useState} from "react";
import VehicleTable from "../components/vehicles/VehicleTable";
import {useNavigate} from "react-router-dom";
import * as lab9 from "lab9";
import ShowVehicleResponse = lab9.lab9.common.responses.ShowVehicleResponse;
import useGlobalStore from "../store/store";
import ShowVehiclesResponse = lab9.lab9.common.responses.ShowVehiclesResponse;

const VehiclesPage = () => {
    const [isLoading, setIsLoading] = useState(true)
    const [currentPage, setCurrentPage] = useState(1)
    const [pageSize, setPageSize] = useState(10)
    const [data, setData] = useState<ShowVehiclesResponse | null>(null);
    const {service} = useGlobalStore()
    const [selectedHeader, setSelectedHeader] = useState(0)
    const [isAscending, setIsAscending] = useState(true)

    useEffect(() => {
        service.showVehicles(currentPage - 1, pageSize, selectedHeader, isAscending).then((data) => {
            setData(data)
            console.log(data)
        }).catch((e) => {
            console.log(e)
        })
            .finally(() => {
            setIsLoading(false)
        })
    }, [currentPage, pageSize, selectedHeader, isAscending])

    const setSorting = (header: number) => {
        if(selectedHeader == header) {
            setIsAscending(!isAscending)
        }
        else {
            setSelectedHeader(header)
            setIsAscending(true)
        }
    }

    const navigate = useNavigate()
    if(isLoading) {
        return (
            <div className="w-full text-black dark:text-white text-4xl">
                <h1>
                    Loading..
                </h1>
            </div>
        )
    }
    return (
        <div className="w-full">
            <button
                className="bg-blue-600 py-2 px-2 rounded-md text-white"
                onClick={
                    () => {

                        navigate("/vehicles/new")
                    }}>
                Create new
            </button>
            <VehicleTable
                vehicles={data ? data.vehicles : []}
                pageSize={pageSize}
                pageNumber={currentPage}
                totalPages={data ? data.totalPages : 1}
                selectedHeader={selectedHeader}
                onSelectHeader={setSorting}
                isAscending={isAscending}
                onPageChange={(num) => {
                    setCurrentPage(num)
                }}
            />
        </div>
    )
}

export default VehiclesPage;