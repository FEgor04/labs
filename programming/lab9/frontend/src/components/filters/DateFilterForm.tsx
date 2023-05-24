import {FilterDropdownProps} from "antd/es/table/interface";
import {Button, DatePicker, Form, FormInstance, InputNumber, Space} from "antd";
import React from "react";
import {VehicleColumn} from "../../api/defs/vehicles/Vehicle.ts";
import {Filter} from "../../api/defs/vehicles/filter/Filter.ts";
import {NumberFilter} from "../../api/defs/vehicles/filter/NumberFilter.ts";
import {FilterProps} from "./NumberFilterForm.tsx";
import {DateFilter} from "../../api/defs/vehicles/filter/DateFilter.ts";
import {useTranslation} from "react-i18next";

const DateFilterForm = ({props, setFilter, clearFilter, column}: FilterProps) => {
    const {t} = useTranslation()
    const formRef = React.useRef<FormInstance>(null);
    const onFinish = (values: { lowerBound: Date | null, upperBound: Date | null }) => {
        if (!values.lowerBound && !values.upperBound) {
            clearFilter()
            return
        }
        console.log(values)
        setFilter(new DateFilter(
            column,
            values.lowerBound,
            values.upperBound,
        ))
        props.confirm({
            closeDropdown: true
        })
    }

    const onReset = () => {
        formRef.current?.resetFields();
    }

    return (
        <Space align="center" style={{padding: ".5rem"}}>
            <Form onFinish={onFinish} ref={formRef}>
                <Form.Item label={t('vehiclesTable.dateFilter.greaterThan')} name="lowerBound">
                    <DatePicker />
                </Form.Item>

                <Form.Item label={t('vehiclesTable.dateFilter.lessThan')} name="upperBound">
                    <DatePicker />
                </Form.Item>
                <Space style={{display: "flex", justifyContent: "space-between"}} align="center">
                    <Button onClick={onReset}>{t('vehiclesTable.filter.reset')}</Button>
                    <Button type="primary" htmlType="submit">{t('vehiclesTable.filter.submit')}</Button>
                </Space>
            </Form>
        </Space>
    )
}
export default DateFilterForm