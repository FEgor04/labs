import {observer} from "mobx-react";
import globalStore from "../../store";
import {useEffect} from "react";
import {Button, Checkbox, Switch, Table} from "antd";
import {useTranslation} from "react-i18next";
import {User} from "../../api/defs/UsersService.ts";
import {ColumnsType, ColumnType} from "antd/es/table";
import PermissionsComponent from "./PermissionsComponent.tsx";

const UsersPage = observer(() => {
    const {t} = useTranslation()
    const usersStore = globalStore.usersStore
    const currentUser = globalStore.viewerStore.viewer
    useEffect(() => {
        usersStore.updateData()
    }, [usersStore.currentPage, usersStore.pageSize])

    const usersTableColumns: ColumnsType<User> = [
        {
            title: t('table.idColumn'),
            dataIndex: "id",
            key: "id",
        },
        {
            title: t('usersTable.usernameColumn'),
            dataIndex: "username",
            key: "username"
        },
        {
            title: t('usersTable.permissionsColumn'),
            dataIndex: "permissions",
            key: "id",
            align: "right",
            render: (value, record) => {
                return (
                    <PermissionsComponent
                        user={record}
                        currentUserId={currentUser.id}
                        onSave={(canDelete, canEdit) => {
                            usersStore.grantPermissions(record.id, canEdit, canDelete)
                    }}
                    />
                )
            }
        }
    ]

    return (
        <Table
            columns={usersTableColumns}
            loading={usersStore.isLoading}
            dataSource={usersStore.users}
            pagination={{
                current: usersStore.currentPage,
                pageSize: usersStore.pageSize,
                total: usersStore.totalElements,
                position: ["bottomCenter"],
            }}
        />
    )
})
export default UsersPage