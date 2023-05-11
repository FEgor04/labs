import {useTranslation} from "react-i18next";
import {Button, Form, Input} from "antd";
import {NavLink, useNavigate} from "react-router-dom";
import {observer} from "mobx-react";
import globalStore from "../../store";

type onFinishParams = {
    username: string,
    password: string
}

const SignInPage = observer(() => {
    const {viewerStore} = globalStore
    const {t} = useTranslation()

    const navigate = useNavigate()

    const onFinish = ({username, password}: onFinishParams) => {
        viewerStore.signIn(username, password, () => {
            navigate("/vehicles")
        })
    }

    return (
        <div>
            <h1>{t('signIn.header')}</h1>
            <Form
                name="basic"
                layout="vertical"
                style={{maxWidth: 600, margin: "auto"}}
                onFinish={onFinish}
            >

                <Form.Item
                    label={t('signIn.username')}
                    name="username"
                    rules={[{required: true, message: t('signIn.usernameMessage')!}]}
                >
                    <Input/>
                </Form.Item>

                <Form.Item
                    label={t('signIn.password')}
                    name="password"
                    rules={[{required: true, message: t('signIn.passwordMessage')!}]}
                >
                    <Input.Password/>
                </Form.Item>

                <Form.Item>
                    <Button type="primary" htmlType="submit">
                        {t('signIn.submit')}
                    </Button>
                </Form.Item>

                <p style={{textAlign: "center", color: "red"}}>
                    {viewerStore.errors}
                </p>

                <p style={{alignContent: "center", textAlign: "center"}}>
                    {t("signIn.dontHaveAnAccount")} <NavLink to="/signup">{t('signIn.signUp')}</NavLink>
                </p>

            </Form>
        </div>
    )
})
export default SignInPage