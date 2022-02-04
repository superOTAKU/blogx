import {login, refreshToken} from '@/api/admin/user'
import jwt from 'jsonwebtoken'

class AccessTokenWrapper {
    constructor(accessToken) {
        this.accessToken = accessToken;
        this.decodedToken = jwt.decode(accessToken.token)
        this.decodedRefreshToken = jwt.decode(accessToken.refreshToken)
    }

    getToken() {
        return this.accessToken.token
    }

    getRefreshToken() {
        return this.accessToken.refreshToken
    }

    getDocodedToken() {
        return this.decodedToken
    }

    getDecodedRefreshToken() {
        return this.decodedRefreshToken
    }

    isTokenExpired() {
        return this.isJwtExpired(this.decodedToken)
    }

    isRefreshTokenExpired() {
        return this.isJwtExpired(this.decodedRefreshToken)
    }

    shouldRefresh() {
        return this.isTokenExpired() || this.decodedToken.exp - Date.now() <= 5 * 60 * 1000
    }

    //js通过#控制可见性
    isJwtExpired(payload) {
        return Date.now() / 1000 > payload.exp
    }

}

//用户登录信息
const store = {
    //使用命名空间
    namespaced: true,
    //state维护数据结构
    state() {
        return {
            //登录token，初始化默认读localStorage
            accessToken: localStorage.getItem('accessToken') ? 
                new AccessTokenWrapper(JSON.parse(localStorage.getItem('accessToken')))
                : null
        }
    },
    //便捷访问方法
    getters: {
        isLogined(state) {
            return (state.accessToken && !state.accessToken.isTokenExpired()) ? true : false
        }
    },
    //mutation提供同步接口修改state
    mutations: {
        setAccessToken(state, token) {
            state.accessToken = new AccessTokenWrapper(token)
            //存储到localStorage相当于序列号反序列化
            localStorage.setItem('accessToken', JSON.stringify(token))
            if (process.env.NODE_ENV === 'development') {
                console.log('save accessToken to localStorage', state.accessToken)
            }
        },
        clearAccessToken(state) {
            localStorage.removeItem('accessToken')
            state.accessToken = null
        }
    },
    //action执行操作，可能是异步操作，action内部通过mutation修改state
    actions: {
        login({commit}, loginForm) {
            return login(loginForm).then(res => {
                commit('setAccessToken', res.data)
            })
        },
        //封装内部自动通过refreshToken刷新token的逻辑，返回promise
        async getTokenHeader({state, commit}) {
            if (!state.accessToken) {
                return Promise.resolve('')
            }
            if (!state.accessToken.isTokenExpired()) {
                //离过期前5分钟自动刷新token
                if (state.accessToken.shouldRefresh()) {
                    return doRefresh()
                }
                return Promise.resolve(state.accessToken.getToken())
            } else {
                //已经过期了，但还能refresh，则尝试refresh
                if (!state.accessToken.isRefreshTokenExpired()) {
                    return doRefresh()
                } else {
                    commit('clearAccessToken')
                    return Promise.resolve('')
                }
            }

            async function doRefresh() {
                try {
                    let newAccessToken = await refreshToken({
                        refreshToken: state.accessToken.getRefreshToken() 
                    })
                    commit('setAccessToken', newAccessToken)
                    return Promise.resolve(state.accessToken.getToken())
                } catch(e) {
                    commit('clearAccessToken')
                    return Promise.resolve('')
                }
            }
        }
    }
}

export default store