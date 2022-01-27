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

app.mount("#app")
