<template>
    <div>
        <Card class="mx-0 my-3">
            <template #title>
                Categories
            </template>
            <template #content>
                <ul class="list-none">
                    <li v-for="category in categories" :key="category.id">
                        <Button class="p-button-link p-button-sm" :label="category.name"/>
                    </li>
                </ul>
            </template>
        </Card>
    </div>
</template>

<script>
import {getCategories} from '@/api/category'

export default {
    name: 'HomeSideBar',
    data() {
        return {
            categories: []
        }
    },
    created() {
        this.getCategories()
    },
    methods: {
        getCategories() {
            getCategories().then(res => {
                this.categories = res.data
            })
        }
    }
}
</script>