<template>
    <div v-if="isLoading">
        <h1 class="text-center font-bold text-2xl p-4">Загрузка..</h1>
    </div>
    <div v-else>
        <div v-if="data != null">
            <IsLoggedIn :refetch="refetch" />
        </div>
        <div v-else class="p-4 container mx-auto space-y-8">
            <NeedToLogIn />
        </div>
    </div>
</template>

<script setup lang="ts">
import { useGetMeQuery } from '@/lib/api/getMeQuery';
import IsLoggedIn from '@/components/index/isLoggedIn.vue';
import NeedToLogIn from '@/components/index/needToLogIn.vue';

const { data, isLoading, refetch } = useGetMeQuery();
// await suspense()
</script>
