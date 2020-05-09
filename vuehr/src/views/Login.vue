<template>
    <div>
        <el-form
                v-loading="loading"
                element-loading-text="正在登陆中"
                element-loading-spinner="el-icon-loading"
                element-loading-background="rgba(0, 0, 0, 0.8)"
                :rules="rules"
                :model="loginForm"
                class="loginContainer"
                ref="loginForm">
            <h3 class="loginTitle">系统登录</h3>
            <el-form-item prop="username">
                <el-input type="text" v-model="loginForm.username" auto-complete="off" placeholder="请输入用户名" @keydown.enter.native="submitLogin"></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input type="password" v-model="loginForm.password" auto-complete="off" placeholder="请输入密码" @keydown.enter.native="submitLogin"></el-input>
            </el-form-item>
            <el-form-item prop="code">
                <el-input type="text" v-model="loginForm.code" auto-complete="off" placeholder="点击图片更换验证码" @keydown.enter.native="submitLogin" style="width: 250px;"></el-input>
                <img :src="vcUrl" @click="updateVerifyCode" alt="">
            </el-form-item>
            <el-checkbox class="loginRemenber" v-model="checked"></el-checkbox>
            <el-button type="primary" style="width:100%" @click="submitLogin">登陆</el-button>
        </el-form>
    </div>
</template>

<script>

    export default {
        name: "Login",
        data() {
            return {
                loginForm: {
                    username: 'admin',
                    password: '123',
                    code:''
                },
                vcUrl:'/vhr-web/verifyCode?time='+new Date(),
                checked: true,
                rules: {
                    username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
                    password: [{required: true, message: '请输入密码', trigger: 'blur'}],
                    code: [{required: true, message: '请输入验证码', trigger: 'blur'}],
                },
                loading:false,
            }
        },
        methods: {
            updateVerifyCode(){
                this.vcUrl='/vhr-web/verifyCode?time='+new Date();
            },
            submitLogin() {
                this.$refs.loginForm.validate((valid) => {
                    if (valid) {
                        this.loading=true;
                        this.postKeyValueRequest('/vhr-web/doLogin',this.loginForm).then(resp=>{
                            this.loading=false;
                            if (resp){
                                //初始化hr
                                this.$store.commit('INIT_CURRENTHR',resp.obj);
                                window.sessionStorage.setItem("user",JSON.stringify(resp.obj));//resp.obj是后端RespBean里的obj，即Hr
                                let path=this.$route.query.redirect;//route而非router
                                // this.$router.replace('/home');
                               this.$router.replace((path=="/"||path==undefined)?'/home':path);
                            }else {
                                this.updateVerifyCode();
                            }
                        })
                        // alert('submit!');
                    } else {
                        this.$message.error('请输入所有字段');
                        return false;
                    }
                });
            }
        }
    }
</script>

<style>
    .loginContainer {
        border-radius: 15px;
        background-clip: padding-box;
        margin: 150px auto;
        width: 350px;
        padding: 15px 35px 15px 35px;
        background: white;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6c6;
    }

    .loginTitle {
        margin: 15px auto 20px auto;
        text-align: center;
        color: #505458;
    }

    .loginRemenber {
        text-align: left;
        margin: 0px 0px 15px 0px;
    }
    .el-form-item__content{
        display: flex;
        align-items: center;
    }
</style>