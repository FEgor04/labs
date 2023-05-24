import {observer} from "mobx-react";

import VehiclesTable from "./VehiclesTable.tsx";
import {Button, PaginationProps, TableProps} from "antd";
import {useNavigate} from "react-router-dom";
import globalStore from "../../store";
import {useTranslation} from "react-i18next";
import {Vehicle} from "../../api/defs/vehicles/Vehicle.ts";

const VehiclesPage = observer(() => {
    const {t} = useTranslation()
    const navigate = useNavigate()

    const {isSignedIn} = globalStore.viewerStore
    if (!isSignedIn) {
        return (
            <h1 style={{textAlign: "center"}}>
                {t('table.needToSignIn')}
            </h1>
        )
    }

    return (
        <div>
            <div style={{padding: "5rem"}}>
                <Button onClick={() => {
                    navigate("/vehicles/new")
                }}>
                    {t('vehiclesTable.createNew')}
                </Button>

                <Button onClick={() => {
                    navigate("/vehicles/visualizer")
                }}>
                    {t('vehiclesTable.visualizer')}
                </Button>
            </div>
            <VehiclesTable/>
        </div>
    )
})

export default VehiclesPage