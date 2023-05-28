import {FilterDropdownProps} from "antd/es/table/interface";
import {Button, Form, FormInstance, InputNumber, Space} from "antd";
import React from "react";
import {VehicleColumn} from "../../api/defs/vehicles/Vehicle.ts";
import {Filter} from "../../api/defs/vehicles/filter/Filter.ts";
import {NumberFilter} from "../../api/defs/vehicles/filter/NumberFilter.ts";
import {useTranslation} from "react-i18next";

export type FilterProps = {
    props: FilterDropdownProps,
    setFilter: (filter: Filter) => void,
    clearFilter: () => void,
    column: VehicleColumn
}

const NumberFilterForm = ({props, setFilter, clearFilter, column}: FilterProps) => {
    const {t} = useTranslation()
    const formRef = React.useRef<FormInstance>(null);
    const onFinish = (values: { lowerBound: number | null, upperBound: number | null }) => {
        if (!values.lowerBound && !values.upperBound) {
            clearFilter()
            return
        }
        setFilter(new NumberFilter(
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
                <Form.Item label={t('vehiclesTable.numberFilter.greaterThan')} name="lowerBound">
                    <InputNumber/>
                </Form.Item>

                <Form.Item label={t('vehiclesTable.numberFilter.lowerThan')} name="upperBound">
                    <InputNumber/>
                </Form.Item>
                <Space style={{display: "flex", justifyContent: "space-between"}} align="center">
                    <Button onClick={onReset}>{t('vehiclesTable.filter.reset')}</Button>
                    <Button type="primary" htmlType="submit">{t('vehiclesTable.filter.submit')}</Button>
                </Space>
            </Form>
        </Space>
    )
}
export default NumberFilterForm