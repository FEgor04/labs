import { useMutation, useQueryClient } from '@tanstack/vue-query';
import {
    SessionData,
    axiosInstance,
    setAccessToken,
    setRefreshToken
} from './api';

type SignUpResponse = {
    id: number;
    email: string;
    session: SessionData;
};

type SignUpRequest = {
    email: string;
    password: string;
};

async function signUp(req: SignUpRequest): Promise<SignUpResponse> {
    const response = await axiosInstance.post('/v1/signup', req);
    axiosInstance.interceptors.request.use;
    return response.data;
}

export function useSignUpMutation(onSuccess: () => void) {
    const queryClient = useQueryClient();

    return useMutation((data: SignUpRequest) => signUp(data), {
        mutationKey: ['viewer'],
        onSuccess(data) {
            queryClient.invalidateQueries({ queryKey: ['viewer'] });
            setAccessToken(data.session.accessToken);
            setRefreshToken(data.session.refreshToken);
            onSuccess();
        }
    });
}
