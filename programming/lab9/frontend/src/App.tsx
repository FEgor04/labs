import './App.css'
import MyRoutes from "./router/MyRoutes.tsx";

import "./i18n/i18n.ts"
import {Layout, notification} from "antd";
import MyHeader from "./layout/MyHeader.tsx";
import {Content} from "antd/es/layout/layout";
import React, {useMemo} from "react";

const App = () => {
    const Context = React.createContext({name: 'Default'})
    const [api, contextHolder] = notification.useNotification()
    const contextValue = useMemo(() => ({name: "Lab9"}), [])
    return (
        <Context.Provider value={contextValue}>
            {contextHolder}
            <Layout>
                <MyHeader/>
                <Content>
                    <MyRoutes/>
                </Content>
            </Layout>
        </Context.Provider>
    )
}

export default App
