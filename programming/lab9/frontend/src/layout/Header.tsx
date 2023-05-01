import {Link, NavLink, useLocation, useNavigate} from "react-router-dom";
import HeaderItem, {HeaderItemProps} from "../components/header/HeaderItem";
import React from "react";

import {Fragment} from 'react'
import {Disclosure, Menu, Transition} from '@headlessui/react'
import {Bars3Icon, BellIcon, XMarkIcon} from '@heroicons/react/24/outline'
import useGlobalStore from "../store/store";
import UserInfo from "../components/header/UserInfo";
import UserNotifications from "../components/header/Notifications";
import {Navbar} from "flowbite-react";
import {NavbarBrand} from "flowbite-react/lib/esm/components/Navbar/NavbarBrand";

// @ts-ignore
function classNames(...classes) {
    return classes.filter(Boolean).join(' ')
}

const Header = () => {
    const location = useLocation()
    const navigation = [
        {name: 'Vehicles', href: '/vehicles', current: location.pathname.startsWith("/vehicles")},
        {name: 'Users', href: '/users', current: location.pathname.startsWith("/users")},
    ]
    const store = useGlobalStore()

    return (
        <Navbar
            fluid={true}
            rounded={true}
        >
            <Navbar.Brand as={Link} to={"/"}>
                <span className="self-center whitespace-nowrap text-xl font-bold dark:text-white">
                    LAB 9
                </span>
            </Navbar.Brand>
            <div className="flex md:order-2">
                <UserInfo/>
            </div>
            <Navbar.Toggle/>
            <Navbar.Collapse>
                {navigation.map((link) => (
                    <Navbar.Link
                        to={link.href}
                        key={link.name}
                        active={link.current}
                        as={Link}
                    >
                        {link.name}
                    </Navbar.Link>
                ))}
            </Navbar.Collapse>
        </Navbar>
    )
}

export default Header