import {useEffect, useState} from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import Layout from "./layout/Layout";
import {BrowserRouter, createBrowserRouter, Router, RouterProvider, useNavigate} from "react-router-dom";
import MyRoutes from "./router/MyRoutes";
import LoginPage from "./pages/LoginPage";
import useGlobalStore from "./store/store";
import UserService from "./services/definitions/UserService";
import {AxiosUserService} from "./services/implementation/AxiosUserService";

function App() {
    const [loading, setLoading] = useState(true)
    const {setIsLoggedIn, setUser, service} = useGlobalStore()
    useEffect(() => {
        const initApp = async () => {
            console.log("Initializing")
            try {
                const data = await service.getMe()
                setIsLoggedIn(true)
                setUser(data)
            } catch(e) {
                localStorage.removeItem('session')
                setIsLoggedIn(false)
                setUser(null)
            }
        }
        initApp().then(() => setLoading(false))
    }, [setIsLoggedIn, setUser])

    if(loading) {
        return (
            <h1 className="text-black dark:text-white text-2xl">Молодой человек, у нас обед</h1>
        )
    }

    return (
        <BrowserRouter>
            <Layout>
                <MyRoutes/>
            </Layout>
        </BrowserRouter>
    )
}

export default App
