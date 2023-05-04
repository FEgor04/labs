import globalStore from "../../store";
import {useTranslation} from "react-i18next";
import {useEffect} from "react";
import {ColumnsType} from "antd/es/table";
import {Vehicle} from "../../api/defs/VehiclesService.tsx";
import NumberFilterForm from "../../components/filters/NumberFilterForm.tsx";
import {Button, Space, Table, TableProps} from "antd";
import {observer} from "mobx-react";

const VehiclesTable = observer(() => {
    const {isSignedIn} = globalStore.viewerStore
    const {
        vehicles,
        isLoading,
        currentPage,
        pageSize,
        setCurrentPage,
        setPageSize,
        deleteVehicle,
        updateData,
        sortColumnNumber,
        isSortAscending,
        setSorting,
        addFilter,
        filter,
        clearFilters
    } = globalStore.vehicleStore
    const {t} = useTranslation()


    useEffect(() => {
        if (isSignedIn) {
            updateData()
        }
    }, [pageSize, currentPage, sortColumnNumber, isSortAscending, isSignedIn, filter])

    if (!isSignedIn) {
        return (
            <h1 style={{textAlign: "center"}}>
                You need to sign in first
            </h1>
        )
    }

    const columns: ColumnsType<Vehicle> = [
        {
            title: t('tableIdColumn'),
            dataIndex: "id",
            key: "id",
            defaultSortOrder: "ascend",
            sorter: (a, b, sortOrder) => {
                setSorting(0, sortOrder)
                return 0
            },
        },
        {
            title: t('tableNameColumn'),
            dataIndex: "name",
            key: "name",
            sorter: (a, b, sortOrder) => {
                setSorting(1, sortOrder)
                return 0
            },
        },
        {
            title: t('tableXColumn'),
            dataIndex: "x",
            key: "x",
            render: (_, record) => (
                <span>{record.coordinates.x}</span>
            ),
            sorter: (a, b, sortOrder) => {
                setSorting(2, sortOrder)
                return 0
            },
            filterDropdown: props => NumberFilterForm(props, addFilter, clearFilters),
        },
        {
            title: t('tableYColumn'),
            dataIndex: "y",
            key: "y",
            render: (_, record) => (
                <span>{record.coordinates.y}</span>
            ),
            sorter: (a, b, sortOrder) => {
                setSorting(3, sortOrder)
                return 0
            },
            filters: [
                {
                    value: 1,
                    text: "asdd"
                }
            ]
        },
        {
            title: t('tableCreationDateColumn'),
            dataIndex: "creationDate",
            key: "creationDate",
            render: (_, record) => (
                <span>{new Date(record.creationDate).toLocaleDateString()}</span>
            ),
            sorter: (a, b, sortOrder) => {
                setSorting(4, sortOrder)
                return 0
            },
        },
        {
            title: t('tableEnginePowerColumn'),
            dataIndex: "enginePower",
            key: "enginePower",
            sorter: (a, b, sortOrder) => {
                setSorting(5, sortOrder)
                return 0
            },
        },
        {
            title: t('tableFuelTypeColumn'),
            dataIndex: "fuelType",
            key: "fuelType",
            sorter: (a, b, sortOrder) => {
                setSorting(6, sortOrder)
                return 0
            },
        },
        {
            title: t('tableVehicleTypeColumn'),
            dataIndex: "vehicleType",
            key: "vehicleType",
            sorter: (a, b, sortOrder) => {
                setSorting(7, sortOrder)
                return 0
            },
        },

        {
            title: t('tableCreatorColumn'),
            dataIndex: "creatorId",
            key: "creatorId",
            sorter: (a, b, sortOrder) => {
                setSorting(8, sortOrder)
                return 0
            },
        },
        {
            title: t('tableActionColumn'),
            key: "action",
            render: (_, record) => (
                <Space>
                    <Button danger={true} onClick={() => {
                        deleteVehicle(record.id)
                    }}
                    >
                        Delete
                    </Button>
                </Space>
            )
        }
    ]

    const onChange: TableProps<Vehicle>['onChange'] = (pagination) => {
        if (pagination.pageSize) {
            setPageSize(pagination.pageSize)
        }
        if (pagination.current) {
            setCurrentPage(pagination.current)
        }
    }

    return (
        <Table columns={columns} dataSource={vehicles?.vehicles} rowKey={"id"} loading={isLoading}
               bordered={true}
               tableLayout="fixed"
               onChange={onChange}
               pagination={{
                   total: vehicles?.totalElements,
                   position: ["bottomCenter"]
               }}
        >
        </Table>
    )
})
export default VehiclesTable