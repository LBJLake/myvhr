<template>
    <div>
        <el-card class="box-card" style="width: 400px;">
            <div slot="header" class="clearfix">
                <span>{{hr.name}}</span>
            </div>
            <div>
                <div style="display: flex;justify-content: center">
                    <el-upload
                            :show-file-list="false"
                            :on-success="onSuccess"
                            :data="hr"
                            action="/hr/userface">
                        <img :src="hr.userface" alt="" title="点击修改用户头像" style="width: 100px;height: 100px;border-radius: 50px;">
                    </el-upload>
                </div>
                <div>电话号码：<el-tag>{{hr.telephone}}</el-tag></div>
                <div>手机号码：<el-tag>{{hr.phone}}</el-tag></div>
                <div>居住地址：<el-tag>{{hr.address}}</el-tag></div>
                <div>用户标签：<el-tag v-for="(role,index) in hr.roles" :key="index" type="success" style="margin-right: 5px">{{role.nameZh}}</el-tag></div>
                <div style="display: flex;justify-content: space-around;margin-top: 10px">
                    <el-button type="primary" @click="showUpdateView">修改信息</el-button>
                    <el-button type="danger" @click="showUpdatePasswordView">修改密码</el-button>
                </div>
            </div>
        </el-card>
        <el-dialog
                title="修改密码"
                :visible.sync="passwordDialogVisible"
                width="30%">
            <div>
                <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                    <el-form-item label="请输入旧密码" prop="oldpass">
                        <el-input type="password" v-model="ruleForm.oldpass" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="请输入新密码" prop="pass">
                        <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="新确认密码" prop="checkPass">
                        <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
                        <el-button @click="resetForm('ruleForm')">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>

        </el-dialog>

        <el-dialog
                title="修改用户信息"
                :visible.sync="dialogVisible"
                width="30%">
            <div>
                <table>
                    <tr>
                        <td><el-tag>昵称：</el-tag></td>
                        <td><el-input v-model="updateHr.name"></el-input></td>
                    </tr>
                    <tr>
                        <td><el-tag>电话号码：</el-tag></td>
                        <td><el-input v-model="updateHr.telephone"></el-input></td>
                    </tr>
                    <tr>
                        <td><el-tag>手机号码：</el-tag></td>
                        <td><el-input v-model="updateHr.phone"></el-input></td>
                    </tr>
                    <tr>
                        <td><el-tag>用户地址：</el-tag></td>
                        <td><el-input v-model="updateHr.address"></el-input></td>
                    </tr>
                </table>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="updateHrInfo">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import {putRequest} from "../utils/api";

    export default {
        name: "HrInfo",
        data(){
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if (this.ruleForm.checkPass !== '') {
                        this.$refs.ruleForm.validateField('checkPass');
                    }
                    callback();
                }
            };
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.ruleForm.pass) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return{
                hr:null,
                updateHr:null,
                dialogVisible:false,
                passwordDialogVisible:false,

                ruleForm: {
                    oldpass:'',
                    pass: '',
                    checkPass: '',
                },
                rules: {
                    oldPass: [
                        { validator: validatePass, trigger: 'blur' }
                    ],
                    pass: [
                        { validator: validatePass, trigger: 'blur' }
                    ],
                    checkPass: [
                        { validator: validatePass2, trigger: 'blur' }
                    ],
                }

            }
        },
        methods:{
            onSuccess(){
                this.initHr();
            },
            initHr(){
                this.getRequest("/hr/info").then(resp=>{
                    if (resp){
                        this.hr=resp;
                        this.updateHr=Object.assign({},this.hr);
                        window.sessionStorage.setItem("user",JSON.stringify(resp));//resp.obj是后端RespBean里的obj，即Hr
                        this.$store.commit('INIT_CURRENTHR',resp);

                    }
                });
            },
            showUpdateView(){
                this.updateHr=Object.assign({},this.hr);
                this.dialogVisible=true;
            },
            showUpdatePasswordView(){
                this.passwordDialogVisible=true;
            },
            updateHrInfo(){
                this.putRequest("/hr/info",this.updateHr).then(resp=>{
                    if (resp){
                        this.initHr();
                        this.dialogVisible=false;
                    }
                });
            },


            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.ruleForm.hrid=this.hr.id;
                        putRequest("/hr/pass",this.ruleForm).then(resp=>{
                            if (resp){
                                this.getRequest("/logout");
                                window.sessionStorage.removeItem("user");
                                this.$store.commit('initRoutes',[]);
                                this.$router.replace("/");
                            }
                        });
                    } else {
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },

        },
        mounted() {
            this.initHr();
        }
    }
</script>

<style scoped>

</style>