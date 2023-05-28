import {Route, Routes,} from "react-router-dom";
import SignInPage from "../pages/SignIn";
import VehiclesPage from "../pages/Vehicles";
import SignUpPage from "../pages/SignUp";
import CreateNewForm from "../pages/Vehicles/CreateNewForm.tsx";
import EditVehicleForm from "../pages/Vehicles/EditVehicleForm.tsx";
import UsersPage from "../pages/Users";
import VisualizerPage from "../pages/Vehicles/Visualizer.tsx";
import {Watermark} from "antd";

const MyRoutes = () => {
    // noinspection AllyJsxHardcodedStringInspection
    return (
            <Routes>
                <Route path="/signin" element={(<SignInPage/>)}/>
                <Route path="/signup" element={(<SignUpPage/>)}/>
                <Route path="/vehicles/visualizer" element={(<VisualizerPage/>)}/>
                <Route path="/vehicles/new" element={(<CreateNewForm/>)}/>
                <Route path="/vehicles/:id" element={<EditVehicleForm/>}/>
                <Route path="/vehicles" element={(<VehiclesPage/>)}/>
                <Route path="/users" element={(<UsersPage/>)}/>
                <Route path="/" element={(<div>Hello</div>)}/>
            </Routes>
    )
}

export default MyRoutes;
