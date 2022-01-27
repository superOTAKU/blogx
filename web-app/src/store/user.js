import {login} from '@/api/admin/user'

//用户登录信息
const store = {
    //使用命名空间
    namespaced: true,
    //state维护数据结构
    state() {
        return {
            //登录token，初始化默认读localStorage
            token: localStorage.getItem('token')
        }
    },
    //mutation提供同步接口修改state
    mutations: {
        setToken(state, token) {
            state.token = token
            localStorage.setItem('token', token)
        }
    },
    //action执行操作，可能是异步操作，action内部通过mutation修改state
    actions: {
        login({commit}, loginForm) {
            return login(loginForm).then(res => {
                commit('setToken', res.data)
            })
        }
    }
}

export default store