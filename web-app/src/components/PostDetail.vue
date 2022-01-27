<template>
    <div>
        <h1>{{post.title}}</h1>
        <h4>Categories:</h4>
        <div>
            <Chip v-for="category in post.categories" :label="category" :key="category" class="mr-2"/>
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
                content: null,
            }
        }
    },
    created() {
        this.post = {
            id: 1,
            categories: ['Java', 'JavaScript'],
            title: 'Java and JavaScript',
            createTime: '2020-10-10',
            content: `Nowadays, Programing becomes more and more popular`
        }
    }
}
</script>