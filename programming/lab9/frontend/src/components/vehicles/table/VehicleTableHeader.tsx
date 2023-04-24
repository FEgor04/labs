import {Table} from "flowbite-react"

export type VehicleTableHeaderProps = {
    selectedHeader: number
    onSelectHeader: (header: number) => void
    isAscending: boolean
}

type HeaderStyle = {
    name: string
    align?: "left" | "center" | "right" | "justify" | "char" | undefined;
}

const SortingIcon = (isSorted: boolean, isAscending: boolean) => {
    if (!isSorted) {
        return null
    }
    if (isAscending) {
        return (
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5}
                 stroke="currentColor" className="w-6 h-6">
                <path strokeLinecap="round" strokeLinejoin="round" d="M4.5 10.5L12 3m0 0l7.5 7.5M12 3v18"/>
            </svg>

        )
    } else {
        return (
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5}
                 stroke="currentColor" className="w-6 h-6">
                <path strokeLinecap="round" strokeLinejoin="round" d="M19.5 13.5L12 21m0 0l-7.5-7.5M12 21V3"/>
            </svg>
        )
    }
}

const VehicleTableHeader = (props: VehicleTableHeaderProps) => {

    const headers: HeaderStyle[] =
        ["ID", "Name", "X", "Y", "CREATION_DATE", "ENGINE POWER", "FUEL TYPE", "VEHICLE TYPE", "CREATOR_ID"].map((name) => {
            return {
                name: name,
                align: "left"
            } as HeaderStyle
        })
    headers[0].align = "right"
    headers[2].align = "right"
    headers[3].align = "right"
    headers[5].align = "right"
    headers[8].align = "right"

    return (
        <Table.Head>
            {headers.map((header, id) => (
                <Table.HeadCell
                    key={header.name}
                    align={header.align}
                    className="dark:text-white text-base text-black"
                    onClick={() => {
                        props.onSelectHeader(id)
                    }}
                >
                    <div className="inline-flex align-middle">
                        {header.align == "right" ? SortingIcon(id == props.selectedHeader, props.isAscending) : null}
                        {header.name}
                        {header.align == "left" ? SortingIcon(id == props.selectedHeader, props.isAscending) : null}
                    </div>
                </Table.HeadCell>
            ))}
        </Table.Head>
    )
}

export default VehicleTableHeader