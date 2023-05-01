import {NavLink} from "react-router-dom";

export type HeaderItemProps = {
    name: string,
    path: string
}

const HeaderItem = (props: HeaderItemProps) => {
    return (
        <NavLink
            className="px-4 py-2 hover:bg-gray-700 text-sm font-medium text-center rounded-md"
            to={props.path}>
            {props.name}
        </NavLink>
    )
}
export default HeaderItem