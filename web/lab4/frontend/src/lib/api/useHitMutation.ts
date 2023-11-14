import { useMutation, useQueryClient } from '@tanstack/vue-query';
import { axiosInstance } from './api';

type SendHitRequest = {
    x: number;
    y: number;
    r: number;
};

async function sendHit(req: SendHitRequest): Promise<void> {
    const response = await axiosInstance.post('/v1/entries', req);
    return response.data;
}

export function useSendHitMutation() {
    const queryClient = useQueryClient();

    return useMutation((data: SendHitRequest) => sendHit(data), {
        mutationKey: ['entries'],
        onSuccess() {
            queryClient.invalidateQueries({ queryKey: ['entries'] });
        }
    });
}
