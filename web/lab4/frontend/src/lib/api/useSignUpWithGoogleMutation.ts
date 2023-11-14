import { useMutation, useQueryClient } from '@tanstack/vue-query';
import {
    SessionData,
    axiosInstance,
    setAccessToken,
    setRefreshToken
} from './api';

type SignUpWithGoogleResposne = {
    id: number;
    userName: string;
    session: SessionData;
};

type SignUpWithGoogleRequest = {
    code: string;
};

async function signIn(
    req: SignUpWithGoogleRequest
): Promise<SignUpWithGoogleResposne> {
    const response = await axiosInstance.post('/v1/signin/google', req);
    return response.data;
}

export function useSignUpWithGoogleMutation(onSucces: () => void) {
    const queryClient = useQueryClient();

    return useMutation((data: SignUpWithGoogleRequest) => signIn(data), {
        mutationKey: ['viewer'],
        onSuccess(data) {
            queryClient.invalidateQueries({ queryKey: ['viewer'] });
            setAccessToken(data.session.accessToken);
            setRefreshToken(data.session.refreshToken);
            onSucces();
        }
    });
}
