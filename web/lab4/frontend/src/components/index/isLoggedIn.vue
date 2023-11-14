<template>
    <div class="p-4 space-y-4 items-center flex flex-col">
        <h1 class="text-2xl text-center font-bold">Вы вошли</h1>
        <div class="flex flex-row space-x-6">
            <RouterLink to="/main">
                <Button>
                    <CrosshairIcon class="w-4 h-4 mr-2" />
                    Стрельнуть
                </Button>
            </RouterLink>
            <Button variant="secondary" @click="logOut">
                <LogOutIcon class="w-4 h-4 mr-2" />
                Выйти
            </Button>
        </div>
    </div>
</template>

<script setup lang="ts">
const props = defineProps(['refetch']);
import { useQueryClient } from '@tanstack/vue-query';
import Button from '../ui/button/Button.vue';
import { CrosshairIcon } from 'lucide-vue-next';
import { LogOutIcon } from 'lucide-vue-next';
import { useRouter } from 'vue-router';

const queryClient = useQueryClient();
const router = useRouter();

function logOut() {
    console.log('Logging out');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('accessToken');
    queryClient.clear();
    queryClient.invalidateQueries({
        queryKey: ['viewer']
    });
    router.push('/');
    props.refetch();
}
</script>
