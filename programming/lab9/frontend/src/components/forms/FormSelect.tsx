import {ChangeEventHandler, HTMLInputTypeAttribute} from "react";
import {Field} from "formik";

type FormSelectProps = {
    name: string
    fieldName: string
    value: string | ReadonlyArray<string> | number | undefined;
    possibleValues: string[]
    handleChange: ChangeEventHandler | undefined;
}

const FormSelect = ({name, fieldName, value, possibleValues, handleChange}: FormSelectProps) => {
    return (
        <div className="mb-4">
            <label className="block text-gray-700 dark:text-white text-sm font-bold mb-2" htmlFor={fieldName}>
                {name}
            </label>

            <select
                className="w-full shadow appearance-none border bg-white"
                name={fieldName}
                value={value}
                onChange={handleChange}
            >
                {possibleValues.map((val) => (
                    <option key={val}>{val}</option>
                ))}
            </select>
        </div>
    )
}
export default FormSelect