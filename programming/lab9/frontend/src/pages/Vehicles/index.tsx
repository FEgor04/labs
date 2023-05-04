import {observer} from "mobx-react";

import VehiclesTable from "./VehiclesTable.tsx";
import {Button} from "antd";
import {useNavigate} from "react-router-dom";

const VehiclesPage = observer(() => {
    const navigate = useNavigate()

    return (
        <div>
            <div style={{padding: "5rem"}}>
                <Button onClick={() => {
                    navigate("/vehicles/new")
                }}>
                    Create new
                </Button>
            </div>
            <VehiclesTable/>
        </div>
    )
})

export default VehiclesPage