import axios, { AxiosError } from 'axios';
export const axiosInstance = axios.create({
    baseURL: '/api'
});

function getAccessToken() {
    return localStorage.getItem('accessToken');
}
function getRefreshToken() {
    return localStorage.getItem('refreshToken');
}

export function setAccessToken(token: string) {
    console.log('setting access token to', token);
    axiosInstance.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    localStorage.setItem('accessToken', token);
}

export function setRefreshToken(token: string) {
    localStorage.setItem('refreshToken', token);
}

axiosInstance.interceptors.request.use(
    (config) => {
        config.headers['Content-Type'] = `application/json`;
        const token = getAccessToken();
        config.headers['Authorization'] = `Bearer ${token}`;
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

let currentlyRefreshing = false;

axiosInstance.interceptors.response.use(
    (res) => {
        return res;
    },
    async (err: AxiosError) => {
        const originalConfig = err.config!;
        if (err.response) {
            // Access Token was expired
            if (err.response.status === 401 && !currentlyRefreshing) {
                currentlyRefreshing = true;
                console.log('Access Token is expired. Trying to refresh it.');

                try {
                    const previousRefreshToken = getRefreshToken();
                    if (!previousRefreshToken) {
                        return Promise.reject(err.response.data);
                    }
                    const rs = await refreshAccessTokenFn(previousRefreshToken);
                    const { accessToken, refreshToken } = rs;
                    originalConfig.headers[
                        'Authorization'
                    ] = `Bearer ${accessToken}`;
                    setAccessToken(accessToken);
                    setRefreshToken(refreshToken);

                    console.log('Succesfully refreshed access token!');
                    return axiosInstance(originalConfig).then((res) => {
                        currentlyRefreshing = false;
                        return res;
                    });
                } catch (_error) {
                    localStorage.removeItem('accessToken');
                    localStorage.removeItem('refreshToken');
                    currentlyRefreshing = false;
                    if (_error instanceof AxiosError) {
                        return Promise.reject(_error.response?.data);
                    }
                    return Promise.reject(_error);
                }
            }

            if (err.response.status === 403 && err.response.data) {
                return Promise.reject(err.response.data);
            }
        }

        return Promise.reject(err);
    }
);

export type SessionData = {
    refreshToken: string;
    accessToken: string;
};

/**
 * Обновляет access token. Возвращает новые токены
 */
async function refreshAccessTokenFn(
    refreshToken: string
): Promise<SessionData> {
    console.log('refreshing token with refresh token = ', refreshToken);
    const data = (
        await axiosInstance.post('/v1/refresh', {
            refreshToken
        })
    ).data;
    console.log('Data from refresh endpoint', data);
    return data.session;
}
