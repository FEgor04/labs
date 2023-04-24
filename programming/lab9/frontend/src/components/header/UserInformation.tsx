import {observer} from "mobx-react";
import globalStore from "../../store";
import {Button, Dropdown, MenuProps} from "antd";
import {useNavigate} from "react-router-dom";

const UserInformation = observer(() => {
    const {viewer, isSignedIn} = globalStore.viewerStore
    const navigate = useNavigate()

    if (!isSignedIn || viewer == null) {
        return (
            <Button onClick={() => {
                navigate("/signin")
            }}
            >
                Sign In
            </Button>
        )
    }

    const items: MenuProps['items'] = [
        {
            label: "Log Out",
            key: "logOut",
            onClick: () => {
                console.log("Logging out")
                globalStore.viewerStore.logOut()
                navigate("/")
            }
        }
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