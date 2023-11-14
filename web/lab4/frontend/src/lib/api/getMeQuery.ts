import { useQuery } from '@tanstack/vue-query';
import { axiosInstance } from './api';
import { AxiosError } from 'axios';

export type GetMeResponse = {
    id: number;
    userName: string;
};

async function getMe(): Promise<GetMeResponse | null> {
    const response = await axiosInstance.get<GetMeResponse>('/v1/me');
    if (response.status == 200) {
        return response.data;
    }
    return null;
}

export function useGetMeQuery() {
    return useQuery({
        queryKey: ['viewer'],
        queryFn: getMe,
        retry(failureCount, error) {
            if (error == 'Bad refresh token') {
                console.log('Bad refresh token. will not try again');
                return false;
            }
            if (error == 'JWT Token not set. Proceed to authorization page.') {
                console.log('No JWT token. Will not try again');
                return false;
            }
            if (error instanceof AxiosError) {
                if ((error.response?.status ?? 0) == 401) {
                    return false;
                }
            }

            return failureCount < 5;
        }
    });
}
