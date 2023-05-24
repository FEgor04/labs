import {useTranslation} from "react-i18next";
import {Button, Form, Input} from "antd";
import {NavLink, useNavigate} from "react-router-dom";
import {observer} from "mobx-react";
import globalStore from "../../store";

type onFinishParams = {
    username: string,
    password: string
}

const SignUpPage = observer(() => {
    const {viewerStore} = globalStore
    const {t} = useTranslation()

    const navigate = useNavigate()

    const onFinish = ({username, password}: onFinishParams) => {
        viewerStore.signUp(username, password, () => {
            navigate("/vehicles")
        })
    }

    return (
        <div>
            <h1>{t('signUp.header')}</h1>
            <Form
                name="basic"
                layout="vertical"
                style={{maxWidth: 600, margin: "auto"}}
                onFinish={onFinish}
            >

                <Form.Item
                    label={t('user.username')}
                    name="username"
                    rules={[{required: true, message: t('signUp.usernameMessage')!}]}
                >
                    <Input/>
                </Form.Item>

                <Form.Item
                    label={t('user.password')}
                    name="password"
                    rules={[{required: true, message: t('signUp.passwordMessage')!}]}
                >
                    <Input.Password/>
                </Form.Item>

                <Form.Item>
                    <Button type="primary" htmlType="submit">
                        {t('signUp.submit')}
                    </Button>
                </Form.Item>

                <p style={{textAlign: "center", color: "red"}}>
                    {t(viewerStore.errors)}
                </p>

                <p style={{alignContent: "center", textAlign: "center"}}>
                    {t("signUp.alreadyHaveAnAccount")} <NavLink to="/signin">{t('signUp.signIn')}</NavLink>
                </p>

            </Form>
        </div>
    )
})
export default SignUpPage