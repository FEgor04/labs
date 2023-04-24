import {FocusEventHandler, FormEvent, PropsWithChildren, ReactNode} from "react";


export type FormWrapperProps<P = unknown> = P & {
    handleSubmit: (event: FormEvent<HTMLFormElement>) => void ,
    handleBlur: FocusEventHandler | undefined;
    children?: ReactNode | undefined
};

const FormWrapper = (props: FormWrapperProps) => {
    return (
        <form
            className="bg-white dark:bg-gray-600
                    shadow-md rounded w-2/5  px-8 pt-6 pb-8 mb-4"
            onSubmit={props.handleSubmit}
            onBlur={props.handleBlur}
        >
            {props.children}
        </form>
    )

}

export default FormWrapper