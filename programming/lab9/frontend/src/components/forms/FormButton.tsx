import {MouseEventHandler} from "react";

type FormButtonProps = {
    text: string
    onClick?: MouseEventHandler | undefined;
    type?: 'submit' | 'reset' | 'button' | undefined;
}

const FormButton = (props: FormButtonProps) => {
    return (
        <button className="py-2 px-4 bg-blue-600 rounded text-white font-bold" onClick={props.onClick} type={props.type}>
            {props.text}
        </button>
    )
}

export default FormButton