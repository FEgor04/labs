<template>
    <tr :class="entry.hit ? `text-green-600` : `text-rose-600`">
        <td>{{ entry.x }}</td>
        <td>{{ entry.y }}</td>
        <td>{{ entry.r }}</td>
        <td>{{ entry.hit ? 'Да!' : 'Нет' }}</td>
    </tr>
</template>

<script setup lang="ts">
import { HistoryEntry } from '@/lib/api/getHitsQuery';

defineProps<{ entry: HistoryEntry }>();
</script>

<style scoped>
td,
th {
    @apply p-1 border border-b-0;
}
</style>
