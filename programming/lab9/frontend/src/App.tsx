import './App.css'
import MyRoutes from "./router/MyRoutes.tsx";

import "./i18n/i18n.ts"
import {Layout} from "antd";
import MyHeader from "./layout/MyHeader.tsx";
import {Content} from "antd/es/layout/layout";

const App = () => {
    return (
        <Layout>
            <MyHeader/>
            <Content>
                <MyRoutes/>
            </Content>
        </Layout>
    )
}

export default App
