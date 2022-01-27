<template>
    <div>
        <ul class="list-none">
            <li v-for="post in postData.list" :key="post.id">
                <Card class="my-3 mx-0 md:mx-3">
                    <template #title>
                        {{post.title}}
                    </template>
                    <template #subtitle>
                        {{post.createTime}}
                    </template>
                    <template #content>
                        {{post.overview}}
                    </template>
                    <template #footer>
                        <Button label="Read More" class="p-button-text p-button-sm p-button-plain" @click="() => $router.push('/post/1')"/>
                    </template>
                </Card>
            </li>
        </ul>
        <Paginator :rows="10" :totalRecords="100" :first="1" />
    </div>
</template>

<script>
export default {
    name: 'PostList',
    created() {
        this.postData = this.mockPosts();
    },
    data() {
        return {
            postData: null,
        }
    },
    methods: {
        mockPosts() {
            let list = []
            for (let i = 0; i < 10; i++) {
                list.push({
                    id: i,
                    title: 'Post-' + i,
                    createTime: '2020-10-10',
                    overview: 'Some overview description-' + i       
                })
            }
            return {
                list: list,
                page: 1,
                total: 100,
                rows: 10
            }
        }
    }
}
</script>