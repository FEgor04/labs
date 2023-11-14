import { createApp } from 'vue';
import './style.css';
import App from './App.vue';
import { createRouter, createWebHistory } from 'vue-router/auto';
import { VueQueryPlugin } from '@tanstack/vue-query';
import VueKonva from 'vue-konva';

const router = createRouter({
    history: createWebHistory()
});

const app = createApp(App);
app.use(router);
app.use(VueQueryPlugin);
app.use(VueKonva);
app.mount('#app');
