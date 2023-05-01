import Header from "./Header";
import {PropsWithChildren} from "react";
import Footer from "./Footer";

const Layout = (props: PropsWithChildren) => {
    return (
        <>
            <Header/>
            {props.children}
            <Footer/>
        </>
    )
}

export default Layout