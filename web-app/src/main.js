import { createApp } from 'vue'
import App from './App.vue'
const app = createApp(App)

//PrimeVue UI框架
import PrimeVue from 'primevue/config'
//样式资源文件
import 'primevue/resources/themes/lara-light-blue/theme.css'
import 'primevue/resources/primevue.min.css'
import 'primeicons/primeicons.css'
import 'primeflex/primeflex.css'
//按需引入组件
import Button from 'primevue/button'
import Menubar from 'primevue/menubar'
import Divider from 'primevue/divider'

//挂载PrimeVue插件
app.use(PrimeVue)
//挂载组件到全局命名空间
app.component('Button', Button)
app.component('Menubar', Menubar)
app.component('Divider', Divider)

//使用vue-router插件
import router from '@/router'
app.use(router)

app.mount("#app")
