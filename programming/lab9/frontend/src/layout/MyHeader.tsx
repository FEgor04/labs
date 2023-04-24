import {Header} from "antd/es/layout/layout";
import HeaderLinks from "../components/header/HeaderLinks.tsx";
import {NavLink} from "react-router-dom";
import UserInformation from "../components/header/UserInformation.tsx";

const headerStyle: React.CSSProperties = {
    color: "white",
    verticalAlign: "middle"
};


const MyHeader = () => {
    return (
        <Header style={headerStyle}>
            <div style={{float: "left", marginRight: "8rem"}}>
                <NavLink to={"/"}
                         style={{
                             fontSize: "1.5rem",
                             color: "white",
                             fontFamily: "monospace",
                             letterSpacing: ".3rem",
                             fontWeight: "bolder"
                         }}>
                    LAB 9
                </NavLink>
            </div>
            <div style={{float: "right", verticalAlign: "middle", content: ""}}>
                <UserInformation/>
            </div>
            <HeaderLinks/>
        </Header>
    )
}
export default MyHeader