<template>
    <div class="p-4 flex flex-col gap-8 max-w-screen-xl mx-auto">
        <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
            <Card class="flex items-center justify-center">
                <Suspense>
                    <template #default>
                        <Canvas />
                    </template>
                    <template #fallback> Loading... </template>
                </Suspense>
            </Card>
            <SubmitForm class="md:col-start-3" />
        </div>

        <Card>
            <Suspense>
                <template #default>
                    <Table />
                </template>
                <template #fallback> Loading... </template>
            </Suspense>
        </Card>
    </div>
</template>

<script setup lang="ts">
import Card from '@/components/ui/card/Card.vue';
import SubmitForm from '@/components/main/submitForm.vue';
import Table from '@/components/main/table.vue';
import Canvas from '@/components/main/canvas.vue';
</script>
