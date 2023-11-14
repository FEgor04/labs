<template>
    <table class="w-full border-b">
        <thead>
            <tr>
                <th>X</th>
                <th>Y</th>
                <th>R</th>
                <th>Попал?</th>
            </tr>
        </thead>
        <TableRow v-for="entry in data" :entry="entry" :key="entry.id" />
    </table>
</template>

<script setup lang="ts">
import TableRow from './tableRow.vue';
import useGetHitsQuery from '../../lib/api/getHitsQuery';
const { data, suspense } = useGetHitsQuery();
await suspense();
</script>

<style scoped>
td,
th {
    @apply p-1 border border-b-0;
}
</style>
