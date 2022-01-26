<template>
    <!--整个应用程序的顶层UI架构-->
    <div class="app-wrapper">
        <header>
            <Menubar :model="items">
                <template #start>
                    <div class="header-gap"></div>
                </template>
            </Menubar>
        </header>
        <div class="grid-nogutter">
            <div class="md:col-8 md:col-offset-2 sm:col-12">
                <router-view/>
            </div>
        </div>
        <Footer class="footer"/>
    </div>
</template>

<script>
import Footer from '@/components/Footer'
export default {
    name: 'Home',
    components: {Footer},
    data() {
        return {
            items: [
                {
                    label: 'Home',
                    to: '/home'
                },
                {
                    label: 'About',
                    to: '/about'
                }
            ]
        }
    }
}
</script>

<style>
    body {
        margin: 0;
        padding: 0;
    }
</style>

<style scoped>

    .p-menubar {
        border: none;
    }
    /* 实现app-container高度自适应 */
    .app-wrapper {
        position: relative;
        height: 100vh;
    }

    .footer {
        position: absolute;
        bottom: 0;
        width: 100%;
    }

    /* 响应式，屏幕大的情况下把标题往右挤 */
    @media screen and (min-width: 1000px) {
        .header-gap {
            width: 20vw;
        }   
    }

</style>
