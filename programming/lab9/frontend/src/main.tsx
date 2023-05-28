import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import {DevSupport} from "@react-buddy/ide-toolbox";
import {ComponentPreviews, useInitial} from "./dev";
import {Provider} from "mobx-react";
import stores from "./store";
import {BrowserRouter} from "react-router-dom";

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
    <React.StrictMode>
        <DevSupport ComponentPreviews={ComponentPreviews}
                    useInitialHook={useInitial}
        >
            <Provider {...stores}>
                <BrowserRouter>
                    <App/>
                </BrowserRouter>
            </Provider>
        </DevSupport>
    </React.StrictMode>,
)
