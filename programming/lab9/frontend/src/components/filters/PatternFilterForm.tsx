import {FilterProps} from "./NumberFilterForm.tsx";
import {Button, Form, FormInstance, Input, InputNumber, Space} from "antd";
import React from "react";
import {useTranslation} from "react-i18next";
import {PatternFilter} from "../../api/defs/vehicles/filter/PatternFilter.ts";

const PatternFilterForm = ({props, setFilter, clearFilter, column}: FilterProps) => {
    const {t} = useTranslation()
    const formRef = React.useRef<FormInstance>(null);
    const onFinish = (values: { pattern: string | null }) => {
        if (!values.pattern) {
            clearFilter()
            return
        }
        setFilter(new PatternFilter(
            column,
            values.pattern
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
                <Form.Item label={t('vehiclesTable.patternFilter.shouldMatch')} name="pattern">
                    <Input/>
                </Form.Item>
                <Space style={{display: "flex", justifyContent: "space-between"}} align="center">
                    <Button onClick={onReset}>{t('vehiclesTable.filter.reset')}</Button>
                    <Button type="primary" htmlType="submit">{t('vehiclesTable.filter.submit')}</Button>
                </Space>
            </Form>
        </Space>
    )
}
export default PatternFilterForm