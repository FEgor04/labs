<template>
    <Card class="max-w-md m-4 md:mx-auto">
        <CardHeader>
            <CardTitle> Регистрация в системе </CardTitle>
        </CardHeader>
        <CardContent class="space-y-6">
            <form id="form" @submit="signUp">
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
            </form>
        </CardContent>
        <CardFooter class="flex flex-row space-x-4 items-center">
            <Button type="submit" form="form" :disabled="isLoading"
                >Отправить</Button
            >
            <RouterLink to="/signin">
                <Button size="sm" variant="link">Войти</Button>
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
import { useSignUpMutation } from '@/lib/api/signUpMutation';

const router = useRouter();
const { mutate, isLoading, isSuccess } = useSignUpMutation(() => {
    router.push('/');
});

let email = '';
let password = '';

function signUp(e: Event) {
    e.preventDefault();
    mutate({ email: email, password });
    if (isSuccess) {
        router.push('/');
    }
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
