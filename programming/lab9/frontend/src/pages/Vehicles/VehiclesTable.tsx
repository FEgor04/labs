import globalStore from "../../store";
import {useTranslation} from "react-i18next";
import {useEffect} from "react";
import {ColumnsType} from "antd/es/table";
import NumberFilterForm from "../../components/filters/NumberFilterForm.tsx";
import {Button, Space, Table, TableProps} from "antd";
import {observer} from "mobx-react";
import {useNavigate} from "react-router-dom";
import {getDateTimeFormatter, getNumberFormatter} from "../../shared/LocalizationUtils.ts";
import PatternFilterForm from "../../components/filters/PatternFilterForm.tsx";
import VehicleTypeFilterForm from "../../components/filters/VehicleTypeFilterForm.tsx";
import FuelTypeFilterForm from "../../components/filters/FuelTypeFilterForm.tsx";
import {Vehicle, VehicleColumn} from "../../api/defs/vehicles/Vehicle.ts";
import {PatternFilter} from "../../api/defs/vehicles/filter/PatternFilter.ts";
import DateFilterForm from "../../components/filters/DateFilterForm.tsx";

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
    const {t, i18n} = useTranslation()
    const currentLocale = new Intl.Locale(i18n.language)
    const numberFormatter = getNumberFormatter()
    const dateTimeFormatter = getDateTimeFormatter()
    const navigate = useNavigate()


    useEffect(() => {
        if (isSignedIn) {
            updateData()
        }
    }, [pageSize, currentPage, sortColumnNumber, isSortAscending, isSignedIn, filter])


    const columns: ColumnsType<Vehicle> = [
        {
            title: t('vehicle.id'),
            dataIndex: "id",
            key: "id",
            defaultSortOrder: "ascend",
            sorter: (a, b, sortOrder) => {
                setSorting(0, sortOrder)
                return 0
            },
        },
        {
            title: t('vehicle.name'),
            dataIndex: "name",
            key: "name",
            ellipsis: true,
            sorter: (a, b, sortOrder) => {
                setSorting(1, sortOrder)
                return 0
            },
            filterDropdown: props => PatternFilterForm({
                props,
                setFilter: addFilter,
                clearFilter: clearFilters,
                column: VehicleColumn.NAME
            }),
        },
        {
            title: t('vehicle.x'),
            dataIndex: "x",
            key: "x",
            align: "right",
            render: (_, record) => (
                <span>{numberFormatter.format(record.coordinates.x)}</span>
            ),
            sorter: (a, b, sortOrder) => {
                setSorting(2, sortOrder)
                return 0
            },
            filterDropdown: props => NumberFilterForm({
                props,
                setFilter: addFilter,
                clearFilter: clearFilters,
                column: VehicleColumn.Y
            }),
        },
        {
            title: t('vehicle.y'),
            dataIndex: "y",
            key: "y",
            align: "right",
            render: (_, record) => (
                <span>{numberFormatter.format(record.coordinates.y)}</span>
            ),
            sorter: (a, b, sortOrder) => {
                setSorting(3, sortOrder)
                return 0
            },
            filterDropdown: props => NumberFilterForm({
                props,
                setFilter: addFilter,
                clearFilter: clearFilters,
                column: VehicleColumn.Y
            }),
        },
        {
            title: t('vehicle.creationDate'),
            dataIndex: "creationDate",
            ellipsis: true,
            key: "creationDate",
            render: (_, record) => (
                <span>{dateTimeFormatter.format(new Date(record.creationDate))}</span>
            ),
            sorter: (a, b, sortOrder) => {
                setSorting(4, sortOrder)
                return 0
            },
            filterDropdown: props => DateFilterForm({
                props,
                setFilter: addFilter,
                clearFilter: clearFilters,
                column: VehicleColumn.CREATION_DATE
            }),
        },
        {
            title: t('vehicle.enginePower'),
            dataIndex: "enginePower",
            key: "enginePower",
            align: "right",
            render: (a, record) => (
                <span>{numberFormatter.format(record.enginePower)}</span>
            ),
            sorter: (a, b, sortOrder) => {
                setSorting(5, sortOrder)
                return 0
            },
            filterDropdown: props => NumberFilterForm({
                props,
                setFilter: addFilter,
                clearFilter: clearFilters,
                column: VehicleColumn.Y
            }),
        },
        {
            title: t('vehicle.fuelType.title'),
            ellipsis: true,
            dataIndex: "fuelType",
            key: "fuelType",
            render: (_, record) => (
                <span>{t(`vehicle.fuelType.${record.fuelType}`)}</span>
            ),
            sorter: (a, b, sortOrder) => {
                setSorting(6, sortOrder)
                return 0
            },
            filterDropdown: props => FuelTypeFilterForm({
                props,
                setFilter: addFilter,
                clearFilter: clearFilters,
                column: VehicleColumn.FUEL_TYPE
            }),
        },
        {
            title: t('vehicle.vehicleType.title'),
            ellipsis: true,
            dataIndex: "vehicleType",
            key: "vehicleType",
            render: (_, record) => (
                <span>{t(`vehicle.vehicleType.${record.vehicleType}`)}</span>
            ),
            sorter: (a, b, sortOrder) => {
                setSorting(7, sortOrder)
                return 0
            },
            filterDropdown: props => VehicleTypeFilterForm({
                props,
                setFilter: addFilter,
                clearFilter: clearFilters,
                column: VehicleColumn.VEHICLE_TYPE
            }),
        },

        {
            title: t('vehicle.creator'),
            ellipsis: true,
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
        <Table columns={columns}
               dataSource={vehicles?.vehicles}
               rowKey={"id"}
               loading={isLoading}
               bordered={true}
               onChange={onChange}
               scroll={{x: true}}
               pagination={{
                   total: vehicles?.totalElements,
                   position: ["bottomCenter"]
               }}
        >
        </Table>
    )
})
export default VehiclesTable