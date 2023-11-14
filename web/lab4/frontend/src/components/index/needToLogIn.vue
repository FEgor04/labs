<template>
    <h1 class="text-left text-2xl font-bold md:text-center">
        Сначала нужно войти в систему или зарегистрироваться
    </h1>
    <div class="flex flex-row justify-start md:justify-center space-x-4">
        <RouterLink to="/signin">
            <Button> Войти </Button>
        </RouterLink>
        <RouterLink to="/signup">
            <Button variant="secondary"> Зарегистрироваться </Button>
        </RouterLink>
    </div>
</template>

<script setup lang="ts">
import Button from '../ui/button/Button.vue';
</script>
