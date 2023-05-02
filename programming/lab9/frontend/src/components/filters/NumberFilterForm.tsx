import {FilterDropdownProps} from "antd/es/table/interface";
import {Button, Form, FormInstance, InputNumber, Space} from "antd";
import React from "react";
import {XFilter} from "../../api/defs/VehiclesService.tsx";

const NumberFilterForm = (props: FilterDropdownProps, setNumberFilter: (filter: XFilter) => void, clearFilter: () => void) => {
    const formRef = React.useRef<FormInstance>(null);
    const onFinish = (values: { lowerBound: number | null, upperBound: null }) => {
        if(!values.lowerBound && !values.upperBound ) {
            console.log("Clearing filters")
            clearFilter()
            return
        }
        console.log(values)
        setNumberFilter(new XFilter(values.lowerBound, values.upperBound))
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
                <Form.Item label={"Value should be greater than"} name="lowerBound">
                    <InputNumber/>
                </Form.Item>

                <Form.Item label={"Value should be lower than"} name="upperBound">
                    <InputNumber/>
                </Form.Item>
                <Space style={{display: "flex", justifyContent: "space-between"}} align="center">
                    <Button onClick={onReset}>Reset</Button>
                    <Button type="primary" htmlType="submit">OK</Button>
                </Space>
            </Form>
        </Space>
    )
}
export default NumberFilterForm