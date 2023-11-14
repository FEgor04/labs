<template>
    <v-circle
        :config="{
            x,
            y,
            radius: 4,
            fill: props.entry.hit ? 'green' : 'red',
            stroke: 'black',
            strokeWidth: 1
        }"
    >
    </v-circle>
</template>

<script setup lang="ts">
const props = defineProps<{ entry: HistoryEntry; canvasSize: number }>();
const MAX_RADIUS = 4;
const RATIO = props.canvasSize / (2 * MAX_RADIUS + 1);
import { HistoryEntry } from '../../lib/api/getHitsQuery';
const x = props.entry.x * RATIO + props.canvasSize / 2;
const y = -props.entry.y * RATIO + props.canvasSize / 2;
</script>
