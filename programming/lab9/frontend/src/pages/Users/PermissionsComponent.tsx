import {User} from "../../api/defs/UsersService.ts";
import {Button, Checkbox} from "antd";
import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";

export type PermissionsComponentProps = {
    user: User
    onSave: (canDelete: boolean, canEdit: boolean) => void
    currentUserId: number
}

const PermissionsComponent = (props: PermissionsComponentProps) => {
    const {t} = useTranslation()
    const isCurrentUser = props.currentUserId == props.user.id
    const [canHeEdit, setCanHeEdit] = useState<boolean>(props.user.canHeEdit)
    const [canHeDelete, setCanHeDelete] = useState<boolean>(props.user.canHeDelete)
    const [hasDelta, setHasDelta] = useState<boolean>(false)
    useEffect(() => {
        setHasDelta(
            canHeEdit != props.user.canHeEdit || canHeDelete != props.user.canHeDelete
        )
    }, [canHeDelete, canHeEdit])
    return (
        <>

            <Checkbox disabled={isCurrentUser} checked={canHeEdit} onChange={e => {setCanHeEdit(e.target.checked)}}>
                {t('usersTable.permissions.canEdit')}
            </Checkbox>

            <Checkbox disabled={isCurrentUser} checked={canHeDelete} onChange={e => {setCanHeDelete(e.target.checked)}}>
                {t('usersTable.permissions.canDelete')}
            </Checkbox>

            <Button disabled={isCurrentUser || !hasDelta} onClick={(_) => {
                props.onSave(canHeDelete, canHeEdit)
            }}>
                {t('usersTable.permissions.save')}
            </Button>
        </>
    )
}
export default PermissionsComponent