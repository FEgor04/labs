<template>
    <Card class="max-w-md m-4 md:mx-auto">
        <CardHeader>
            <CardTitle> Вход в систему </CardTitle>
        </CardHeader>
        <CardContent class="space-y-6">
            <form id="form" @submit="signIn">
                <div>
                    <Label for="email"
                        >Электронная почта
                        <span class="text-red-600">*</span></Label
                    >
                    <Input v-model="email" id="email" type="email" required />
                </div>
                <div>
                    <Label for="password"
                        >Пароль <span class="text-red-600">*</span></Label
                    >
                    <Input
                        v-model="password"
                        id="password"
                        type="password"
                        required
                    />
                </div>
                <div v-if="isError" class="text-red-600">
                    Неправильный логин или пароль
                </div>
            </form>
            <div class="space-y-4">
                <Button
                    class="bg-vk/20 border border-vk text-vk hover:bg-vk/10"
                    @click="redirectToVkOauth"
                >
                    <img
                        class="h-6 w-6 mr-2"
                        src="https://upload.wikimedia.org/wikipedia/commons/thumb/f/f3/VK_Compact_Logo_%282021-present%29.svg/768px-VK_Compact_Logo_%282021-present%29.svg.png"
                    />
                    Войти с помощью VK
                </Button>
                <Button
                    class="bg-primary/20 border border-primary text-primary hover:bg-primary/10"
                    @click="redirectToGoogleOauth"
                >
                    <img
                        class="h-6 w-6 mr-2"
                        src="https://www.svgrepo.com/show/303108/google-icon-logo.svg"
                    />
                    Войти с помощью Google
                </Button>
            </div>
        </CardContent>
        <CardFooter class="flex flex-row space-x-4 items-center">
            <Button type="submit" form="form" :disabled="isLoading"
                >Отправить</Button
            >
            <RouterLink to="/signup">
                <Button size="sm" variant="link">Зарегистрироваться</Button>
            </RouterLink>
        </CardFooter>
    </Card>
</template>

<script setup lang="ts">
import {
    Card,
    CardTitle,
    CardHeader,
    CardContent,
    CardFooter
} from '@/components/ui/card/index.ts';
import { Label } from '@/components/ui/label';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { RouterLink, useRouter } from 'vue-router';
import { useSignInMutation } from '@/lib/api/signInMutation';

const router = useRouter();
const { mutate, isLoading, isError } = useSignInMutation(() => {
    router.push('/');
});

let email = '';
let password = '';

function signIn(e: Event) {
    e.preventDefault();
    mutate({ email, password });
}

const redirectBase = 'https://localhost';
function redirectToVkOauth() {
    const vkClientId = '51782321';
    const vkRedirectUri = redirectBase + '/oauth/vk';
    const vkScope = 1 << 22;
    window.location.href = `https://oauth.vk.com/authorize?client_id=${vkClientId}&redirect_uri=${vkRedirectUri}&display=page&scope=${vkScope}&response_type=code`;
}

function redirectToGoogleOauth() {
    const googleClientId =
        '137778051916-osj3hdpside8gni55h3tocdk7glce14r.apps.googleusercontent.com';
    const googleRedirectUri = redirectBase + '/oauth/google';
    const googleScope = 'https://www.googleapis.com/auth/userinfo.email';
    window.location.href = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${googleClientId}&redirect_uri=${googleRedirectUri}&display=page&scope=${googleScope}&response_type=code`;
}
</script>

<style scoped>
form {
    @apply flex flex-col space-y-4;
}

form > div {
    @apply flex flex-col space-y-2;
}
</style>
