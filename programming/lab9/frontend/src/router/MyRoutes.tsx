
import {Route, Routes, } from "react-router-dom";
import SignInPage from "../pages/SignIn";
import VehiclesPage from "../pages/Vehicles";

const MyRoutes = () => {
    return (
        <Routes>
            <Route path="/signin" element={(<SignInPage/>)} />
            <Route path="/vehicles" element={(<VehiclesPage />)}/>
            <Route path="/" element={(<div>Hello</div>)} />
        </Routes>
    )
}

export default MyRoutes;
