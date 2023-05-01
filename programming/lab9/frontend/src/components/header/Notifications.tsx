const UserNotifications = () => {
    return (

        <button type="button"
                className="relative inline-flex items-center p-3 py-2 text-sm font-medium text-center text-white    focus:ring-4 focus:outline-none focus:ring-blue-300 ">
            <svg className="w-6 h-6" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20"
                 xmlns="http://www.w3.org/2000/svg">
                <path d="M2.003 5.884L10 9.882l7.997-3.998A2 2 0 0016 4H4a2 2 0 00-1.997 1.884z"></path>
                <path d="M18 8.118l-8 4-8-4V14a2 2 0 002 2h12a2 2 0 002-2V8.118z"></path>
            </svg>
            <span className="sr-only">Notifications</span>
            <div
                className="absolute inline-flex items-center justify-center w-6 h-6 text-xs font-bold text-white bg-red-500 border-2 border-white rounded-full -top-1 -right-1 dark:border-gray-900">20
            </div>
        </button>

    )
}

export default UserNotifications