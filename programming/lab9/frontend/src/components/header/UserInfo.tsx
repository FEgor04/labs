import useGlobalStore from "../../store/store";
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";
import {AxiosUserService} from "../../services/implementation/AxiosUserService";
import {Dropdown} from "flowbite-react";

const UserInfo = () => {
    const {service, isLoggedIn, user, setUser} = useGlobalStore()
    useEffect(() => {
        if (user == null) {
            service.getMe().then((user) => {
                if (user != null) {
                    setUser(user)
                }
            })
        }
    }, [user, setUser])
    console.log(user)
    const navigate = useNavigate()
    if (!isLoggedIn) {
        return (
            <button className="px-4 py-2 rounded-md text-sm font-medium text-center text-white bg-gray-700"
                    onClick={() => {
                        navigate("/login")
                    }}
            >
                Log In
            </button>
        )
    }

    return <>
        <Dropdown label={user?.username} arrowIcon={false}>
            <Dropdown.Item onClick={() => {
                console.log(document.cookie)
                document.cookie = ""
                setUser(null)
            }}>
                Log Out
            </Dropdown.Item>
        </Dropdown>
    </>
}

export default UserInfo