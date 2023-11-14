import path from 'path';
import vue from '@vitejs/plugin-vue';
import { defineConfig } from 'vite';
import VueRouter from 'unplugin-vue-router/vite';

export default defineConfig({
    base: 'https://localhost/',
    plugins: [VueRouter({}), vue()],
    resolve: {
        alias: {
            '@': path.resolve(__dirname, './src')
        }
    },
    server: {
        proxy: {
            '/api': 'http://localhost:8080/backend-1.0-SNAPSHOT/'
        }
    }
});
