import axios from "axios";

//全局配置axios
axios.defaults.baseURL = process.env.APPS_BASE_URL
axios.defaults.headers['Content-Type'] = 'application/json'


function initInterceptors(app) {
    //添加全局的拦截器
    axios.interceptors.request.use(config => {
        //处理各种情况，这里比较复杂
        return config
    }, error => {

    })
};