import {defineConfig} from 'vite'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [],
    server: {
        proxy: {
            '/action.php': 'http://localhost:8080',
        }
    }
})
