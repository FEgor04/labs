import { useMutation, useQueryClient } from '@tanstack/vue-query';
import {
    SessionData,
    axiosInstance,
    setAccessToken,
    setRefreshToken
} from './api';

type SignInResponse = {
    id: number;
    email: string;
    session: SessionData;
};

type SignInRequest = {
    email: string;
    password: string;
};

async function signIn(req: SignInRequest): Promise<SignInResponse> {
    const response = await axiosInstance.post('/v1/signin', req);
    return response.data;
}

export function useSignInMutation(onSuccess: () => void) {
    const queryClient = useQueryClient();

    return useMutation((data: SignInRequest) => signIn(data), {
        mutationKey: ['viewer'],
        onSuccess(data) {
            queryClient.invalidateQueries({ queryKey: ['viewer'] });
            setAccessToken(data.session.accessToken);
            setRefreshToken(data.session.refreshToken);
            onSuccess();
        }
    });
}
