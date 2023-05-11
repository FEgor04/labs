import {useTranslation} from "react-i18next";
import {useLocation, useNavigate} from "react-router-dom";
import {Menu, MenuProps} from "antd";

const HeaderLinks = () => {
    const {t} = useTranslation()

    const items: MenuProps['items'] = [
        {
            label: t('header.vehicles'),
            key: "vehicles",
        },
        {
            label: t('header.users'),
            key: "users"
        },
    ]

    const current = useLocation().pathname.substring(1)
    console.log(`Current is ${current}`)
    const navigate = useNavigate()
    const onClick: MenuProps['onClick'] = (e) => {
        navigate(`/${e.key}`)
    }

    return (
        <Menu mode="horizontal" items={items} onClick={onClick} selectedKeys={[current]} theme={"dark"}>
        </Menu>
    )
}
export default HeaderLinks