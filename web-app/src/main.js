import { createApp } from 'vue'
import App from './App.vue'
const app = createApp(App)

//全局样式重置
import '@/assets/global.css'

//PrimeVue UI框架
import primevue from '@/primevue'
primevue(app)

//使用vue-router插件
import router from '@/router'
app.use(router)

//使用vuex管理状态
import store from '@/store'
app.use(store)

import initAxios from '@/axios'
//vue对象外部访问公共属性的方式，例如访问$store
initAxios(app.config.globalProperties)

app.mount("#app")
