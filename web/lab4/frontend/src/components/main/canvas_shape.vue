<template>
    {{ radius }}
    <v-shape
        :config="{
            sceneFunc: function (context: any, shape: any) {
                const MAX_RADIUS = 4;
                const RATIO = props.size / (2 * MAX_RADIUS + 1);
                context.beginPath();
                context.moveTo(size / 2, size / 2 - radius * RATIO);
                context.lineTo(
                    size / 2 - (radius / 2) * RATIO,
                    size / 2 - radius * RATIO
                );
                context.lineTo(size / 2 - (radius / 2) * RATIO, size / 2);
                context.lineTo(size / 2, size / 2);
                context.arc(
                    size / 2,
                    size / 2,
                    Math.abs((radius / 2) * RATIO),
                    Math.PI / 2,
                    Math.PI
                );
                context.lineTo(size / 2 + (radius / 2) * RATIO, size / 2);
                context.lineTo(size / 2, size / 2 - (radius / 2) * RATIO);
                context.closePath();

                // special Konva.js method
                context.fillStrokeShape(shape);
            },
            fill: '#16a34a',
            stroke: 'black',
            strokeWidth: 3
        }"
    />
</template>

<script setup lang="ts">
const props = defineProps<{
    size: number;
    radius: number;
}>();
</script>
