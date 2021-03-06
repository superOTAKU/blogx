import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        //大部分页面用了Layout，少数比如Login页面可能没用到
        {
            path: '/', 
            component: () => import('@/components/Layout.vue'),
            children: [
                {
                    path: '', 
                    component: () => import('@/components/Home.vue')
                },
                {
                    path: '/home', 
                    component: () => import('@/components/Home.vue')
                },
                {
                    path: '/about', 
                    component: () => import('@/components/About.vue')
                },
                {
                    path: '/post/:id', 
                    component: () => import('@/components/PostDetail.vue')
                }
            ]
        }
    ]
})

export default router