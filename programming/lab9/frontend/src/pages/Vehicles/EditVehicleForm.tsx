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
    useEffect(() => {
        updateVehicleStore.setId(id)
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
    if(updateVehicleStore.isLoading) {
        return (
            <h1>
                {t('loading')}
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
                           initialValue={updateVehicleStore.previousVehicle?.id}
                >
                    <Input disabled/>
                </Form.Item>

                <Form.Item
                    name="name"
                    label={t("name")}
                    required
                    initialValue={updateVehicleStore.previousVehicle?.name}
                >
                    <Input/>
                </Form.Item>

                <Form.Item
                    name="x"
                    label={t("vehicle.x")}
                    required
                    initialValue={updateVehicleStore.previousVehicle?.coordinates.x}
                    // rules={[{min: -527, required: true}]}
                >
                    <InputNumber/>
                </Form.Item>
                <Form.Item
                    name="y"
                    label={t("vehicle.y")}
                    initialValue={updateVehicleStore.previousVehicle?.coordinates.y}
                >
                    <InputNumber/>
                </Form.Item>

                <Form.Item
                    name="enginePower"
                    label={t("vehicle.enginePower")}
                    rules={[{required: true}]}
                    initialValue={updateVehicleStore.previousVehicle?.enginePower}
                >
                    <InputNumber type="number"/>
                </Form.Item>

                <Form.Item
                    name="vehicleType"
                    label={t("vehicle.vehicleType.title")}
                    initialValue={updateVehicleStore.previousVehicle?.vehicleType}
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
                    initialValue={updateVehicleStore.previousVehicle?.fuelType == null ? "" : updateVehicleStore.previousVehicle?.fuelType}
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