import {ChangeEventHandler, HTMLInputTypeAttribute} from "react";
import {Label, TextInput} from "flowbite-react";

type FormInputProps = {
    name: string
    value: string | ReadonlyArray<string> | number | undefined;
    type: HTMLInputTypeAttribute | undefined
    handleChange: ChangeEventHandler | undefined;
    htmlName: string,
    required?: boolean | undefined;
    errors?: string | undefined
    touched?: boolean | undefined
}

const FormInput = ({name, value, type, handleChange, required, errors, htmlName, touched}: FormInputProps) => {
    return (
        <div className="mb-4">
            <Label htmlFor="htmlName" className="block text-gray-700 dark:text-white text-sm font-bold mb-2" value={name}>
            </Label>

            <TextInput
                value={value}
                type={type}
                name={htmlName}
                onChange={handleChange}
                className="w-full shadow appearance-none border"
                required={required}
                helperText={errors && touched ? (<>{errors}</>) : null}
                color={errors && touched ? "failure" : undefined}

            />
        </div>
    )
}

export default FormInput