<template>
    <div v-if="!isCodeOk">Ошибочка! Кода-то нету!</div>
    <div v-else>
        <h1 class="text-center mt-8 text-2xl">Загрузка...</h1>
    </div>
</template>

<script setup lang="ts">
import { useSignUpWithVkMutation } from '../../lib/api/useSignUpWithVkMutation';
import { useRouter } from 'vue-router/auto';
const router = useRouter();

let urlParams = new URLSearchParams(window.location.search);
let isCodeOk = urlParams.has('code'); // true
let vkCode = urlParams.get('code'); // "MyParam"

if (vkCode != null) {
    const { mutate } = useSignUpWithVkMutation(() => {
        router.push('/main');
    });
    mutate({ code: vkCode });
}
</script>
