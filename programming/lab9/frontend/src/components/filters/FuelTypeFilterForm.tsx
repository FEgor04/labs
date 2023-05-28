import {FilterProps} from "./NumberFilterForm.tsx";
import {Button, Checkbox, Form, FormInstance, Input, InputNumber, Space} from "antd";
import React from "react";
import {useTranslation} from "react-i18next";
import {FuelType, VehicleType} from "../../api/defs/vehicles/Vehicle.ts";
import {PatternFilter} from "../../api/defs/vehicles/filter/PatternFilter.ts";
import {EnumFilter} from "../../api/defs/vehicles/filter/EnumFilter.ts";

const FuelTypeFilterForm = ({props, setFilter, clearFilter, column}: FilterProps) => {
    const {t} = useTranslation()
    const formRef = React.useRef<FormInstance>(null);
    const onFinish = (values: any) => {
        console.log(values)
        if (!values.values) {
            clearFilter()
            return
        }
        setFilter(new EnumFilter<FuelType>(
            column,
            values.values.map((it) => {
                return FuelType[`${it}`]
            }),
            "FuelType"
        ))
        props.confirm({
            closeDropdown: true
        })
    }

    const onReset = () => {
        formRef.current?.resetFields();
    }


    const options = Object.keys(FuelType).filter((item) => {
        return isNaN(Number(item));
    }).map((it, ind) => {
        return {
            label: t(`vehicle.fuelType.${it}`),
            value: it
        }
    });

    return (
        <Space align="center" style={{padding: ".5rem"}}>
            <Form onFinish={onFinish} ref={formRef}>
                <Form.Item label={t('vehiclesTable.enumFilter.shouldBeOneOf')} name="values">
                    <Checkbox.Group options={options}/>
                </Form.Item>
                <Space style={{display: "flex", justifyContent: "space-between"}} align="center">
                    <Button onClick={onReset}>{t('vehiclesTable.filter.reset')}</Button>
                    <Button type="primary" htmlType="submit">{t('vehiclesTable.filter.submit')}</Button>
                </Space>
            </Form>
        </Space>
    )
}
export default FuelTypeFilterForm