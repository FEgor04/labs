import {defineConfig} from 'vite'
import react from '@vitejs/plugin-react'
import commonjs from 'vite-plugin-commonjs'
import {esbuildCommonjs, viteCommonjs} from "@originjs/vite-plugin-commonjs";


// https://vitejs.dev/config/

/** @type {import('vite').UserConfig} */
export default defineConfig({
    plugins: [
        react(),
        viteCommonjs(),
    ],
    server: {
        proxy: {
            '/api': {
                target: 'http://localhost:8080'
            }
        }
    },
    optimizeDeps: {
        include: ["node_modules/lab9"]
    }
})
