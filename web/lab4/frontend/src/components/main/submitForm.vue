<template>
    <Card>
        <CardHeader>
            <CardTitle> Вдарим? </CardTitle>
        </CardHeader>
        <CardContent>
            <form id="form" @submit="sendForm" @reset="resetForm">
                <div>
                    <Label for="x">
                        X<span class="text-red-600">*</span>
                    </Label>
                    <div class="grid grid-cols-3 gap-1">
                        <div
                            v-for="x in xValues"
                            class="flex flex-row space-x-2"
                        >
                            <input
                                type="radio"
                                name="x"
                                v-model="store.x"
                                :value="x"
                                required
                            />
                            <Label>{{ x }}</Label>
                        </div>
                    </div>
                </div>
                <div>
                    <Label for="y">
                        Y<span class="text-red-600">*</span>
                    </Label>
                    <Input v-model="store.y" id="y" required />
                </div>
                <div>
                    <Label for="r">
                        R<span class="text-red-600">*</span>
                    </Label>
                    <div class="grid grid-cols-3 gap-1">
                        <div
                            v-for="r in rValues"
                            class="flex flex-row space-x-2"
                        >
                            <input
                                type="radio"
                                name="r"
                                v-model="store.r"
                                :value="r"
                                required
                            />
                            <Label>{{ r }}</Label>
                        </div>
                    </div>
                </div>
                <div v-if="error.error != undefined" class="text-red-600">
                    {{ error.error }}
                </div>
            </form>
        </CardContent>
        <CardFooter class="space-x-6 items-center">
            <Button type="submit" form="form"
                ><FlameIcon class="w-4 h-4 mr-2" /> Вдарить
            </Button>
            <Button variant="secondary" type="reset" form="form"
                ><RotateCw class="w-4 h-4 mr-2" /> Сбросить
            </Button>
        </CardFooter>
    </Card>
</template>

<script setup lang="ts">
import { FlameIcon, RotateCw } from 'lucide-vue-next';
import Button from '@/components/ui/button/Button.vue';
import Card from '@/components/ui/card/Card.vue';
import CardContent from '@/components/ui/card/CardContent.vue';
import CardFooter from '@/components/ui/card/CardFooter.vue';
import CardHeader from '@/components/ui/card/CardHeader.vue';
import CardTitle from '@/components/ui/card/CardTitle.vue';
import Input from '@/components/ui/input/Input.vue';
import Label from '@/components/ui/label/Label.vue';
import { useSendHitMutation } from '@/lib/api/useHitMutation';

import { ZodError, z } from 'zod';
import { store } from './state';
import { reactive } from 'vue';

const { mutate } = useSendHitMutation();

const scheme = z.object({
    x: z
        .number()
        .min(-4, { message: 'X должен быть больше или равен 4' })
        .max(4, { message: 'X должен быть меньше или равен 4' }),
    y: z
        .number()
        .min(-4, { message: 'Y должен быть больше или равен 4' })
        .max(4, { message: 'Y должен быть меньше или равен 4' }),
    r: z
        .number()
        .min(1, { message: 'R должен быть больше 0' })
        .max(4, { message: 'R должен быть меньше или равен 4' })
});

const xValues = [-4, -3, -2, -1, 0, 1, 2, 3, 4];
const rValues = [-4, -3, -2, -1, 0, 1, 2, 3, 4];

const error = reactive<{ error: any }>({ error: undefined });

function sendForm(e: Event) {
    error.error = undefined;
    e.preventDefault();
    try {
        const parsed = scheme.parse({
            x: store.x,
            y: parseFloat(String(store.y)),
            r: store.r
        });
        mutate(parsed);
    } catch (e) {
        if (e instanceof ZodError) {
            error.error = e.errors[0].message;
        }
    }
}

function resetForm() {
    error.error = undefined;
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
