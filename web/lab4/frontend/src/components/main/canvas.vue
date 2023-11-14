<template>
    <span class="hidden">
        {{ store.r }}
    </span>
    <div
        class="w-full aspect-square"
        id="canvasWrapper"
        ref="el"
        @click="handleClick"
    >
        <v-stage :config="{ width, height }" v-if="width != 0">
            <v-layer>
                <CanvasShape :radius="store.r" :size="width" />
            </v-layer>
            <v-layer>
                <CanvasAxes :radius="store.r" :size="width" />
                <!-- <v-text :x="width/2 + 5" :y="width/2 + 5" text="0" />  -->
            </v-layer>
            <v-layer>
                <Point
                    v-for="point in filterRadius(data ?? [])"
                    :entry="point"
                    :canvas-size="width"
                />
            </v-layer>
        </v-stage>
    </div>
</template>

<script lang="ts" setup>
import CanvasShape from './canvas_shape.vue';
import CanvasAxes from './canvas_axes.vue';
import useGetHitsQuery, { HistoryEntry } from '@/lib/api/getHitsQuery';
import Point from './point.vue';
import { useElementSize } from '@vueuse/core';
import { store } from './state';
import { ref } from 'vue';
import { AxiosError } from 'axios';
import { useRouter } from 'vue-router';
import { useSendHitMutation } from '@/lib/api/useHitMutation';
const el = ref(null);
const router = useRouter();
const { width, height } = useElementSize(el);
const { data, suspense } = useGetHitsQuery();
const { mutate } = useSendHitMutation();

function filterRadius(value: HistoryEntry[]) {
    return value.filter((entry) => {
        return entry.r == store.r;
    });
}

function handleClick(e: MouseEvent) {
    if (store.r > 0) {
        const MAX_RADIUS = 4;
        const RATIO = width.value / (2 * MAX_RADIUS + 1);
        let x: number = (e.offsetX - width.value / 2) / RATIO;
        let y: number = (width.value / 2 - e.offsetY) / RATIO;
        x = parseFloat(Math.min(Math.max(x, -4), 4).toFixed(2));
        y = parseFloat(Math.min(Math.max(y, -4), 4).toFixed(2));
        mutate({ x, y, r: store.r });
    }
}

try {
    await suspense();
} catch (error) {
    console.log('Error while getting hits');
    console.log(error);
    if (error instanceof AxiosError) {
        if (error.response?.status == 401) {
            router.replace('/');
        }
    }
}
</script>

<style scoped></style>
