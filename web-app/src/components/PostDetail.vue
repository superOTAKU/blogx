<template>
    <div>
        <h1>{{post.title}}</h1>
        <h4>Categories:</h4>
        <div>
            <Chip v-for="category in post.categories" :label="category.name" :key="category.id" class="mr-2"/>
        </div>
        <Divider/>
        <!--解析markdown语法-->
        <div>
            <Markdown :source="post.content"/>
        </div>
    </div>
</template>

<script>
import Markdown from 'vue3-markdown-it'
import 'highlight.js/styles/monokai.css'

import {getPostDetail} from '@/api/post'

export default {
    name: 'PostDetail',
    components: {Markdown},
    data() {
        return {
            post: {
                id: null,
                categories: [],
                title: null,
                createTime: null,
                content: '',
            }
        }
    },
    created() {
        this.getPostDetail()
    },
    methods: {
        getPostDetail() {
            getPostDetail(this.$route.params.id).then(res => {
                this.post = res.data
            })
        }
    }
}
</script>