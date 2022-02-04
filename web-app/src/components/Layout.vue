<template>
    <!--整个应用程序的顶层UI架构-->
    <div class="app-wrapper">
        <header class="app-header">
            <Menubar :model="items">
                <template #start>
                    <div class="flex flex-row mr-2">
                        <div class="flex header-gap"></div>
                        <Image class="flex" :src="logo" :imageStyle="{width: '40px', height: '40px'}"/>
                    </div>
                </template>
                <template #end>
                    <Button v-if="!isLogin" label="Login" class="p-button-sm p-button-text" @click="showLoginForm"/>
                    <Button v-if="isLogin" label="Logout" class="p-button-sm p-button-text" @click="logout"/>
                </template>
            </Menubar>
        </header>
        <div class="grid grid-nogutter app-container">
            <div class="col-12 md:col-8 md:col-offset-2">
                <router-view/>
            </div>
        </div>
        <Footer class="app-footer"/>
        <Dialog v-model:visible="loginDialog.visible" header="Login" :modal="true" :style="{width: '400px'}">
            <div class="grid grid-nogutter">
                <div class="col-12 field mt-4">
                    <div class="p-float-label">
                        <InputText type="text" id="username" v-model="loginForm.username" style="width:100%;"/>
                        <label for="username">Username</label>
                    </div>
                </div>
                <div class="col-12 field mt-4">
                    <div class="p-float-label">
                        <Password id="password" class="password" v-model="loginForm.password" style="width:100%;"/>
                        <label for="password">Password</label>
                    </div>
                </div>
            </div>
            <template #footer>
                <Button label="Submit" @click="submitLoginForm"/>
                <Button label="Cancel" @click="hideLoginForm"/>
            </template>
        </Dialog>
        <Toast/>
    </div>
</template>

<script>
import Footer from '@/components/Footer'
import Logo from '@/assets/logo.png'
import {mapGetters} from 'vuex'
export default {
    name: 'Layout',
    props: {
        //加载完页面是否默认弹登录框
        show: {
            type: Boolean,
            default: false
        }
    },
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
            ],
            logo: Logo,
            loginDialog: {
                visible: false
            },
            loginForm: {
                username: '',
                password: ''
            }
        }
    },
    computed: {
        ...mapGetters({
            isLogin: 'user/isLogined'
        })
    },
    mounted() {
        if (this.show) {
            this.showLoginForm();
        }
    },
    methods: {
        showLoginForm() {
            this.loginDialog.visible = true
        },
        hideLoginForm() {
            this.loginDialog.visible = false,
            this.loginForm = {
                username: '',
                password: ''
            }
        },
        submitLoginForm() {
            this.$store.dispatch('user/login', this.loginForm).then(() => {
                this.hideLoginForm();
                this.$toast.add({severity:'success', summary: 'Login Success', detail: 'Login Success', life: 1500});
            }).catch(e => {
                //弹窗提示登录失败
                this.hideLoginForm();
                this.$toast.add({severity:'error', summary: 'Login Fail', detail: 'You fail login because ' + e, life: 1500});
            })
        },
        logout() {
            this.$store.commit('user/clearAccessToken')
            this.$toast.add({
                severity: 'success',
                summary: 'Logout Success',
                detail: 'Logout Success',
                life: 1500
            })
        }
    }
}
</script>

<style scoped>

    .p-menubar {
        border: none;
    }
    
    .app-wrapper {
        height: 100vh;
    }

    .app-header {
        height: 52px;
    }

    .app-container {
        min-height: calc(100vh - 112px - 2em);
        margin: 1em;
    }

    .app-footer {
        height: 60px;
    }

    /* 响应式，屏幕大的情况下把标题往右挤 */
    @media screen and (min-width: 1000px) {
        .header-gap {
            width: 20vw;
        }   
    }

    .password >>> input {
        width: 100% !important;
    }

</style>
