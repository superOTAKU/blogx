<template>
    <div>
        <ul class="list-none">
            <li v-for="post in postData.records" :key="post.id">
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
                        <Button label="Read More" class="p-button-text p-button-sm p-button-plain" @click="routeToPostDetail(post.id)"/>
                    </template>
                </Card>
            </li>
        </ul>
        <Paginator :rows="pageConfig.rows" :totalRecords="postData.total" :first="pageConfig.page" />
    </div>
</template>

<script>

import {getPostList} from '@/api/post'

export default {
    name: 'PostList',
    created() {
        this.getPosts()
    },
    data() {
        return {
            postData: {
                records: [],
                total: 0
            },
            pageConfig: {
                page: 1,
                rows: 10
            }
        }
    },
    methods: {
        getPosts() {
            getPostList(this.pageConfig.page, this.pageConfig.rows).then(res => {
                this.postData = res.data
            })
        },
        routeToPostDetail(id) {
            this.$router.push({
                path: '/post/' + id,
                params: {
                    id: id
                }
            })
        }
    }
}
</script>