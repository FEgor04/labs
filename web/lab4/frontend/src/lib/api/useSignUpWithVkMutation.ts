import { useMutation, useQueryClient } from '@tanstack/vue-query';
import {
    SessionData,
    axiosInstance,
    setAccessToken,
    setRefreshToken
} from './api';

type SignUpWithVkResposne = {
    id: number;
    userName: string;
    session: SessionData;
};

type SignUpWithVkRequest = {
    code: string;
};

async function signIn(req: SignUpWithVkRequest): Promise<SignUpWithVkResposne> {
    const response = await axiosInstance.post('/v1/signin/vk', req);
    return response.data;
}

export function useSignUpWithVkMutation(onSucces: () => void) {
    const queryClient = useQueryClient();

    return useMutation((data: SignUpWithVkRequest) => signIn(data), {
        mutationKey: ['viewer'],
        onSuccess(data) {
            queryClient.invalidateQueries({ queryKey: ['viewer'] });
            setAccessToken(data.session.accessToken);
            setRefreshToken(data.session.refreshToken);
            onSucces();
        }
    });
}
