import globalStore from "../../store";
import {useTranslation} from "react-i18next";
import {useEffect} from "react";
import {ColumnsType} from "antd/es/table";
import {Vehicle} from "../../api/defs/VehiclesService.ts";
import NumberFilterForm from "../../components/filters/NumberFilterForm.tsx";
import {Button, Space, Table, TableProps} from "antd";
import {observer} from "mobx-react";
import {useNavigate} from "react-router-dom";

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
    const navigate = useNavigate()


    useEffect(() => {
        if (isSignedIn) {
            updateData()
        }
    }, [pageSize, currentPage, sortColumnNumber, isSortAscending, isSignedIn, filter])

    if (!isSignedIn) {
        return (
            <h1 style={{textAlign: "center"}}>
                {t('table.needToSignIn')}
            </h1>
        )
    }

    const columns: ColumnsType<Vehicle> = [
        {
            title: t('table.idColumn'),
            dataIndex: "id",
            key: "id",
            defaultSortOrder: "ascend",
            sorter: (a, b, sortOrder) => {
                setSorting(0, sortOrder)
                return 0
            },
        },
        {
            title: t('table.nameColumn'),
            dataIndex: "name",
            key: "name",
            sorter: (a, b, sortOrder) => {
                setSorting(1, sortOrder)
                return 0
            },
        },
        {
            title: t('table.xColumn'),
            dataIndex: "x",
            key: "x",
            align: "right",
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
            title: t('table.yColumn'),
            dataIndex: "y",
            key: "y",
            align: "right",
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
            title: t('table.creationDateColumn'),
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
            title: t('table.enginePowerColumn'),
            dataIndex: "enginePower",
            key: "enginePower",
            align: "right",
            sorter: (a, b, sortOrder) => {
                setSorting(5, sortOrder)
                return 0
            },
        },
        {
            title: t('table.fuelTypeColumn'),
            dataIndex: "fuelType",
            key: "fuelType",
            sorter: (a, b, sortOrder) => {
                setSorting(6, sortOrder)
                return 0
            },
        },
        {
            title: t('table.vehicleTypeColumn'),
            dataIndex: "vehicleType",
            key: "vehicleType",
            sorter: (a, b, sortOrder) => {
                setSorting(7, sortOrder)
                return 0
            },
        },

        {
            title: t('table.creatorColumn'),
            dataIndex: "creatorId",
            key: "creatorId",
            sorter: (a, b, sortOrder) => {
                setSorting(8, sortOrder)
                return 0
            },
        },
        {
            title: t('table.actionColumn'),
            key: "action",
            align: "right",
            render: (_, record) => (
                <Space>
                    <Button disabled={!record.canDelete} danger={true} onClick={() => {
                        deleteVehicle(record.id)
                    }}
                    >
                        {t('table.actions.delete')}
                    </Button>

                    <Button disabled={!record.canEdit} type="primary" onClick={() => {
                        navigate(`/vehicles/${record.id}`)
                    }}>
                        {t('table.actions.update')}
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