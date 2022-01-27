<template>

<div class="grid grid-nogutter">
    <div class="col-12 md:col-9">
        <PostList/>
    </div>
    <div class="col-12 md:col-3">
        <HomeSideBar/>
    </div>
</div>
</template>

<script>
import PostList from '@/components/PostList'
import HomeSideBar from '@/components/HomeSideBar'
export default {
    name: 'Home',
    components: {PostList, HomeSideBar}
}
</script>
