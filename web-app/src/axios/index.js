import axios from "axios"
import _ from "lodash";

//全局配置axios
axios.defaults.baseURL = process.env.VUE_APP_BASE_URL
axios.defaults.headers['Content-Type'] = 'application/json'

//防抖，很可能并发调用n个请求，防止重复的错误处理
let processNoTokenDebounce = _.debounce(processNoToken, 1000)
let process401Debounce = _.debounce(process401, 1000)
let process403Debounce = _.debounce(process403, 1000)

//拦截了错误，并做了中间处理之后，必须将错误返回
//让具体的业务自己决定如何处理异常
export default function initInterceptors(app) {
    //类似网络不可达类错误没必要提示用户
    axios.interceptors.request.use(async config => {
        if (config.url.startsWith('/api')) {
            return config;
        }
        if (config.url === '/login' || config.url === '/refreshToken') {
            return config;
        }
        //需要用户认证的接口，带上token访问
        let token = ''
        try {
            token = await app.$store.dispatch('/user/getTokenHeader')
        } catch (e) {
            if (process.env.NODE_ENV === 'development') {
                console.log('get token error', e)
            }
        }
        //如果拿不到token，导航到首页弹出登录框
        if (!token) {
            processNoTokenDebounce(app)
            //取消本次请求
            throw new axios.Cancel('cancel because no token is found');
        }
        return config
    })

    axios.interceptors.response.use(res => {
        return res
    }, error => {
        let res = error.response
        if (res.status === 401) {
            process401Debounce(app)
        } else if (res.status === 403) {
            process403Debounce()
        }
        return Promise.reject(error)
    })
}

function processNoToken(app) {
    app.$router.push('/')
}

function process401(app) {
    app.$router.push('/')
}

function process403() {
    this.$toast.add({severity:'error', summary: 'Forbidden', detail: 'Forbidden', life: 3000});
}