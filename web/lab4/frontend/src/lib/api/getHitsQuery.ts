import { useQuery } from '@tanstack/vue-query';
import { axiosInstance } from './api';
import { AxiosError } from 'axios';

export interface HistoryEntry {
    id: number;
    x: number;
    y: number;
    r: number;
    hit: boolean;
    serverTime: number;
    executionTime: number;
}

async function getHitsQuery(): Promise<HistoryEntry[]> {
    return (await axiosInstance.get('/v1/entries')).data;
}

export default function useGetHitsQuery() {
    return useQuery({
        queryFn: getHitsQuery,
        queryKey: ['entries'],
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
