import {observer} from "mobx-react";
import {Button, Form, Input, InputNumber, Select, Space} from "antd";
import {useTranslation} from "react-i18next";
import globalStore from "../../store";
import {PossibleFuelTypes, PossibleVehicleTypes} from "../../api/defs/vehicles/Vehicle.ts";

const CreateNewForm = observer(() => {
    const {t} = useTranslation()
    const {vehicleStore} = globalStore

    const handleFinish = (values: any) => {
        vehicleStore.createVehicle({
            name: values.name,
            enginePower: values.enginePower,
            fuelType: values.fuelType,
            vehicleType: values.vehicleType,
            x: values.x,
            y: values.y
        })
    }

    return (
        <div>
            <h1>
                {t('createVehicle.title')}
            </h1>
            <Form
                name="basic"
                layout="vertical"
                style={{maxWidth: 600, margin: "auto"}}
                onFinish={handleFinish}
            >
                <Form.Item
                    name="name"
                    label={t("vehicle.name")}
                    required
                >
                    <Input/>
                </Form.Item>

                <Form.Item
                    name="x"
                    label={t("vehicle.x")}
                    required
                >
                    <InputNumber/>
                </Form.Item>
                <Form.Item
                    name="y"
                    label={t("vehicle.y")}
                >
                    <InputNumber/>
                </Form.Item>

                <Form.Item
                    name="enginePower"
                    label={t("vehicle.enginePower")}
                    rules={[{required: true}]}
                >
                    <InputNumber type="number"/>
                </Form.Item>

                <Form.Item
                    name="vehicleType"
                    label={t("vehicle.vehicleType.title")}
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

                <Space>
                    <Button type={"primary"} htmlType="submit">
                        {t('createVehicle.submit')}
                    </Button>
                </Space>
            </Form>
        </div>
    )
})

export default CreateNewForm