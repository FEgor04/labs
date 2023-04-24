import {Route, Routes, useLocation, useNavigate} from "react-router-dom";
import LoginPage from "../pages/LoginPage";
import useGlobalStore from "../store/store";
import React, {useEffect} from "react";
import VehiclesPage from "../pages/VehiclesPage";
import CreateVehiclePage from "../pages/CreateVehiclePage";

const MyRoutes = () => {
    return (
        <Routes>
            <Route path="/login" element={(<LoginPage/>)}/>
            <Route path="" element={(<h1>Hello</h1>)}/>
            <Route path="/vehicles" element={<VehiclesPage/>}/>
            <Route path="/vehicles/new" element={<CreateVehiclePage/>}/>
        </Routes>
    )
}
export default MyRoutes;