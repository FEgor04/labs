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
    const {isSignedIn, viewer: currentUser} = globalStore.viewerStore
    useEffect(() => {
        if (isSignedIn) {
            usersStore.updateData()
        }
    }, [usersStore.currentPage, usersStore.pageSize])

    if(!isSignedIn) {
        return (
            <h1>
                {t('table.needToSignIn')}
            </h1>
        )
    }

    const usersTableColumns: ColumnsType<User> = [
        {
            title: t('user.id'),
            dataIndex: "id",
            key: "id",
        },
        {
            title: t('user.username'),
            dataIndex: "username",
            key: "username"
        },
        {
            title: t('usersTable.permissions.title'),
            dataIndex: "permissions",
            key: "permissions",
            align: "right",
            render: (value, record) => {
                return (
                    <PermissionsComponent
                        key={record.id}
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
            scroll={{x: true}}
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