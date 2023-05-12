import {observer} from "mobx-react";
import {Button, Form, Input, InputNumber, Select} from "antd";
import {useTranslation} from "react-i18next";
import {PossibleFuelTypes, PossibleVehicleTypes} from "../../api/defs/VehiclesService.ts";
import globalStore from "../../store";

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
                Create new
            </h1>
            <Form
                name="basic"
                layout="vertical"
                style={{maxWidth: 600, margin: "auto"}}
                onFinish={handleFinish}
            >
                <Form.Item
                    name="name"
                    label={t("name")}
                    required
                >
                    <Input/>
                </Form.Item>

                <Form.Item
                    name="x"
                    label={t("x")}
                    required
                    // rules={[{min: -527, required: true}]}
                >
                    <InputNumber/>
                </Form.Item>
                <Form.Item
                    name="y"
                    label={t("y")}
                >
                    <InputNumber/>
                </Form.Item>

                <Form.Item
                    name="enginePower"
                    label={t("enginePower")}
                    rules={[{required: true}]}
                >
                    <InputNumber type="number"/>
                </Form.Item>

                <Form.Item
                    name="vehicleType"
                    label={t("vehicleType")}
                    required
                >
                    <Select>
                        {PossibleVehicleTypes.map((name) => (
                            <Select.Option key={name.toString()} value={name}>{name}</Select.Option>
                        ))}
                    </Select>
                </Form.Item>


                <Form.Item
                    name="fuelType"
                    label={t("fuelType")}
                >

                    <Select>
                        <>
                            <Select.Option value={""}>{}</Select.Option>
                            {PossibleFuelTypes.map((name) => (
                                <Select.Option key={name} value={name}>{name}</Select.Option>
                            ))}
                        </>
                    </Select>
                </Form.Item>

                <Button type={"primary"} htmlType="submit">
                    Create
                </Button>
            </Form>
        </div>
    )
})

export default CreateNewForm