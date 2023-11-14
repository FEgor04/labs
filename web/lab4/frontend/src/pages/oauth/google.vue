<template>
    <div v-if="!isCodeOk">Ошибочка! Кода-то нету!</div>
    <div v-else>
        <h1 class="text-center mt-8 text-2xl">Загрузка...</h1>
    </div>
</template>

<script setup lang="ts">
import { useSignUpWithGoogleMutation } from '../../lib/api/useSignUpWithGoogleMutation.ts';
import { useRouter } from 'vue-router/auto';
const router = useRouter();

let urlParams = new URLSearchParams(window.location.search);
let isCodeOk = urlParams.has('code'); // true
let googleCode = urlParams.get('code'); // "MyParam"

if (googleCode != null) {
    const { mutate } = useSignUpWithGoogleMutation(() => {
        router.push('/main');
    });
    mutate({ code: googleCode });
}
</script>
