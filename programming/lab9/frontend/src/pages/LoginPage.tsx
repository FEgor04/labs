import React from "react";
import {Formik} from "formik";
import useGlobalStore from "../store/store";
import {AxiosUserService} from "../services/implementation/AxiosUserService";
import UserService from "../services/definitions/UserService";
import {useNavigate} from "react-router-dom";

const LoginPage = () => {
    const store = useGlobalStore()
    const navigate = useNavigate()

    return (
        <div className="grid h-screen place-items-center">
            <div
                className="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-900 dark:border-gray-700">
                <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
                    <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
                        Sign In
                    </h1>
                    <Formik
                        initialValues={{username: '', password: ''}}
                        onSubmit={(values, {setSubmitting, setErrors}) => {
                            setSubmitting(true)
                            store.service.tryToLogin(values.username, values.password)
                                .then(() => {
                                    store.setIsLoggedIn(true)
                                    navigate("/")
                                })
                                .catch((e) => {
                                    setErrors(e.toString())
                                    setSubmitting(false)
                                })
                        }}
                    >
                        {({
                              values,
                              errors,
                              touched,
                              handleChange,
                              handleBlur,
                              handleSubmit,
                              isSubmitting,
                              /* and other goodies */
                          }) => (
                            <form className="space-y-4 md:space-y-6" onSubmit={handleSubmit}>
                                <div>
                                    <label htmlFor="email"
                                           className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
                                        Username
                                    </label>
                                    <input type="text" name="username" id="username"
                                           className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                                           onChange={handleChange}
                                           onBlur={handleBlur}
                                           value={values.username}
                                           required/>
                                    {errors.username && touched.username && errors.username}
                                </div>
                                <div>
                                    <label htmlFor="password"
                                           className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Password</label>
                                    <input type="password" name="password" id="password" placeholder="••••••••"
                                           className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                                           onChange={handleChange}
                                           onBlur={handleBlur}
                                           value={values.password}
                                           required/>
                                    {errors.password && touched.password && errors.password}
                                </div>
                                <div className="flex items-center justify-between">
                                    <div className="flex items-start">
                                        <div className="flex items-center h-5">
                                            <input id="remember" aria-describedby="remember" type="checkbox"
                                                   className="w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-primary-300 dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-primary-600 dark:ring-offset-gray-800"
                                            />
                                        </div>
                                        <div className="ml-3 text-sm">
                                            <label htmlFor="remember" className="text-gray-500 dark:text-gray-300">
                                                Remember me
                                            </label>
                                        </div>
                                    </div>
                                    <a href="#"
                                       className="text-sm font-medium text-gray-500 hover:underline dark:text-gray-500 disabled">
                                        Forgot password?
                                    </a>
                                </div>
                                <button type="submit"
                                        className="bg-blue-600 text-white w-full font-bold rounded-lg text-sm px-5 py-2.5"
                                        disabled={isSubmitting}
                                >
                                    Sign in
                                </button>
                                <p className="text-sm font-light text-gray-500 dark:text-gray-400">
                                    Don’t have an account yet? <a href="#"
                                                                  className="font-medium text-primary-600 hover:underline dark:text-primary-500">Sign
                                    up</a>
                                </p>
                            </form>
                        )}
                    </Formik>
                </div>
            </div>
        </div>
    )
}
export default LoginPage