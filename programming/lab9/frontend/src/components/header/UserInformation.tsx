import {observer} from "mobx-react";
import globalStore from "../../store";
import { Button, Dropdown, MenuProps} from "antd";
import {useNavigate} from "react-router-dom";
import {useTranslation} from "react-i18next";

const UserInformation = observer(() => {
    const {t} = useTranslation()
    const {viewer, isSignedIn} = globalStore.viewerStore
    const navigate = useNavigate()

    if (!isSignedIn || viewer == null) {
        return (
            <Button onClick={() => {
                navigate("/signin")
            }}
            >
                {t('header.logIn')}
            </Button>
        )
    }

    const items: MenuProps['items'] = [
        {
            label: t('header.logOut'),
            key: "logOut",
            onClick: () => {
                console.log("Logging out")
                globalStore.viewerStore.logOut()
                navigate("/")
            }
        },
    ]

    const menuProps = {
        items,
    }

    return (
        <Dropdown menu={menuProps}>
            <Button> {viewer.username}#{viewer.id} </Button>
        </Dropdown>
    )
})
export default UserInformation