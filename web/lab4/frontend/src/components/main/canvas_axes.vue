<template>
    {{ radius }}
    <v-shape
        :config="{
            sceneFunc: function (context: any, shape: any) {
                context.beginPath();
                context.moveTo(0, size / 2);
                context.lineTo(size, size / 2);
                context.stroke();
                context.moveTo(size / 2, 0);
                context.lineTo(size / 2, size);
                context.stroke();
                context.fillStrokeShape(shape);
            },
            fill: '#16a34a',
            stroke: 'black',
            strokeWidth: 3
        }"
    />
</template>

<script setup lang="ts">
defineProps<{
    size: number;
    radius: number;
}>();
</script>
