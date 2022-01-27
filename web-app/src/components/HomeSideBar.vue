<template>
    <div>
        <Card class="mx-0 my-3">
            <template #title>
                Categories
            </template>
            <template #content>
                <ul class="list-none">
                    <li v-for="category in categories" :key="category">
                        <Button class="p-button-link p-button-sm" :label="category"/>
                    </li>
                </ul>
            </template>
        </Card>
    </div>
</template>

<script>
export default {
    name: 'HomeSideBar',
    data() {
        return {
            categories: []
        }
    },
    created() {
        for(let i = 0; i < 10; i++) {
            this.categories.push('Category-' + i)
        }
    }
}
</script>