import { createStore } from 'vuex'
import userStore from './user.js'

const store = createStore({
    modules: {
        user: userStore
    }
})

export default store