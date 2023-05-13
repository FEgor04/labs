import {observer} from "mobx-react";
import {useTranslation} from "react-i18next";
import globalStore from "../../store";
import {Button, Form, Input, InputNumber, Select} from "antd";
import {useParams} from "react-router";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {PossibleFuelTypes, PossibleVehicleTypes, Vehicle} from "../../api/defs/vehicles/Vehicle.ts";

const EditVehicleForm = observer(() => {
    const {t} = useTranslation()
    const navigate = useNavigate()
    const idStr = useParams()["id"]
    const id = parseInt(idStr!)
    const {updateVehicleStore} = globalStore.vehicleStore
    const [prevVehicle, setPrevVehicle] = useState<Vehicle>()
    const [isLoading, setLoading] = useState<boolean>()
    useEffect(() => {
        console.log(`Loading vehicle ${id}`)
        setLoading(true)
        updateVehicleStore.getVehicle(id).then(veh => {
            setPrevVehicle(veh)
            console.log(prevVehicle)
        }).catch(e => {
            console.log(e)
        }).finally(() => {
            setLoading(false)
        })
    }, [id])

    const handleFinish = (values: any) => {
        updateVehicleStore.updateVehicle(id, {
            name: values.name,
            enginePower: values.enginePower,
            fuelType: values.fuelType,
            vehicleType: values.vehicleType,
            x: values.x,
            y: values.y
        })
    }
    if(isLoading) {
        return (
            <h1>
                Загрузка
            </h1>
        )
    }

    return (
        <div>
            <h1>
                {t('editVehicle.title')}
            </h1>
            <Form
                name="basic"
                layout="vertical"
                style={{maxWidth: 600, margin: "auto"}}
                onFinish={handleFinish}
            >
                <Form.Item name="id"
                           label={t('vehicle.id')}
                           initialValue={prevVehicle?.id}
                >
                    <Input disabled/>
                </Form.Item>

                <Form.Item
                    name="name"
                    label={t("name")}
                    required
                    initialValue={prevVehicle?.name}
                >
                    <Input/>
                </Form.Item>

                <Form.Item
                    name="x"
                    label={t("vehicle.x")}
                    required
                    initialValue={prevVehicle?.coordinates.x}
                    // rules={[{min: -527, required: true}]}
                >
                    <InputNumber/>
                </Form.Item>
                <Form.Item
                    name="y"
                    label={t("vehicle.y")}
                    initialValue={prevVehicle?.coordinates.y}
                >
                    <InputNumber/>
                </Form.Item>

                <Form.Item
                    name="enginePower"
                    label={t("vehicle.enginePower")}
                    rules={[{required: true}]}
                    initialValue={prevVehicle?.enginePower}
                >
                    <InputNumber type="number"/>
                </Form.Item>

                <Form.Item
                    name="vehicleType"
                    label={t("vehicle.vehicleType.title")}
                    initialValue={prevVehicle?.vehicleType}
                    required
                >
                    <Select>
                        {PossibleVehicleTypes.map((name) => (
                            <Select.Option key={name} value={name}>{t(`vehicle.vehicleType.${name}`)}</Select.Option>
                        ))}
                    </Select>
                </Form.Item>


                <Form.Item
                    name="fuelType"
                    label={t("vehicle.fuelType.title")}
                    initialValue={prevVehicle?.fuelType == null ? "" : prevVehicle?.fuelType}
                >

                    <Select>
                        <>
                            <Select.Option key={"null"} value={""}>{t(`vehicle.fuelType.null`)}</Select.Option>
                            {PossibleFuelTypes.map((name) => (
                                <Select.Option key={name} value={name}>{t(`vehicle.fuelType.${name}`)}</Select.Option>
                            ))}
                        </>
                    </Select>
                </Form.Item>

                <Button type={"primary"} htmlType="submit">
                    {t('editVehicle.submit')}
                </Button>
            </Form>
        </div>
    )
})

export default EditVehicleForm