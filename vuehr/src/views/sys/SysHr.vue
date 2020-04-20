<template>
    <div>
        <div style="margin-top: 10px;display: flex;justify-content: center;">
            <el-input
                    v-model="keywords"
                    @input="doSearch"
                    placeholder="通过用户名搜索用户..."
                    prefix-icon="el-icon-search"
                    style="width: 400px;margin-right: 10px">
            </el-input>
            <el-button icon="el-icon-search" @click="doSearch" type="primary">搜索</el-button>

        </div>
        <div class="hr-container">
            <el-card
                    v-loading="loading"
                    element-loading-text="正在加载中..."
                    element-loading-spinner="el-icon-loading"
                    element-loading-background="rgba(0, 0, 0, 0.8)"
                    class="hr-card"
                    v-for="(hr,index) in hrs"
                    :key="index">
                <div slot="header" class="clearfix">
                    <span>{{hr.name}}</span>
                    <el-button style="float: right; padding: 3px 0;color: red" type="text" icon="el-icon-delete" @click="deleteHr(hr)"></el-button>
                </div>
                <div>
                    <div class="img-container">
                        <img :src="hr.userface" :alt="hr.name" :title="hr.name" class="userface-img">
                    </div>
                    <div class="userinfo-container">
                        <div>用户名：{{hr.name}}</div>
                        <div>手机号码：{{hr.phone}}</div>
                        <div>电话号码：{{hr.telephone}}</div>
                        <div>地址：{{hr.address}}</div>
                        <div>用户状态：
                            <el-switch
                                v-model="hr.enabled"
                                @change="enableChange(hr)"
                                active-color="#13ce66"
                                inactive-color="#ff4949"
                                active-text="启用"
                                inactive-text="禁止">
                            </el-switch>
                        </div>
                        <div>用户角色：
                            <el-tag type="success" style="margin-right: 4px" v-for="(role,index) in hr.roles" :key="index">{{role.nameZh}}</el-tag>
                            <el-popover
                                    placement="right"
                                    title="角色列表"
                                    @show="showPop(hr)"
                                    @hide="hidePop(hr)"
                                    width="200"
                                    trigger="click">

                                <el-select v-model="selectedRoles" multiple placeholder="请选择">
                                    <el-option
                                            v-for="(r,indexk) in allRoles"
                                            :key="indexk"
                                            :label="r.nameZh"
                                            :value="r.id">
                                    </el-option>
                                </el-select>
                                <el-button slot="reference" icon="el-icon-more" type="text"></el-button>
                            </el-popover>
                        </div>
                        <div>备注：{{hr.remark}}</div>
                    </div>
                </div>
            </el-card>
        </div>
    </div>
</template>

<script>
    export default {
        name: "SysHr",
        data(){
            return{
                keywords:'',
                hrs:[],
                allRoles:[],
                selectedRoles:[],
                loading:false,
            }
        },
        methods:{
            initHrs(){
                this.loading=true;
                this.getRequest("/system/hr/?keywords="+this.keywords).then(resp=>{
                    this.loading=false;
                    if (resp){
                        this.hrs=resp;
                    }
                });
            },
            initAllRoles(){this.getRequest("/system/hr/roles").then(resp=>{
                    if (resp){
                        this.allRoles=resp;
                    }
                });
            },
            showPop(hr){
                this.initAllRoles();
                let roles=hr.roles;
                this.selectedRoles=[];
                roles.forEach(r=>{
                    this.selectedRoles.push(r.id);
                });
            },
            hidePop(hr){
                //比较数据是否变化
                let roles=[];
                Object.assign(roles,hr.roles);
                if (roles.length==this.selectedRoles.length){
                    for (let i=0;i<roles.length;i++){
                        let role=roles[i];
                        for (let j=0;j<this.selectedRoles.length;j++){
                            let sr=this.selectedRoles[j];
                            if (role.id==sr){
                                roles.splice(i,1);
                                i--;
                                break;
                            }
                        }
                    }
                    if (roles.length==0)return;
                }

                let url='/system/hr/role?hrid='+hr.id;
                this.selectedRoles.forEach(rid=>{
                    url+='&rids='+rid;
                });
                this.putRequest(url).then(resp=>{
                    if (resp){
                        this.initHrs();
                    }
                });
            },
            doSearch(){
                this.initHrs();
            },
            deleteHr(hr){
                this.$confirm('此操作将永远删除【'+hr.name+'】, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/hr/"+hr.id).then(resp=>{
                        if (resp){
                            this.initHrs();
                        }
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消操作'
                    });
                });
            },
            enableChange(hr){this.putRequest("/system/hr/",hr).then(resp=>{
                    if (resp){
                        this.initHrs();
                    }
                });
            },
        },
        mounted() {
            this.initHrs();
        },
    }
</script>

<style>
    .hr-container{
        margin-top: 10px;
        display: flex;
        justify-content: space-around;
        flex-wrap: wrap;
    }
    .hr-card{
        width: 350px;
        margin-bottom: 20px;
    }
    .userface-img{
        width: 72px;
        height: 72px;
        border-radius: 72px;
    }
    .img-container{
        width: 100%;
        display: flex;
        justify-content: center;
    }
    .userinfo-container{
        margin-top: 20px;
    }
    .userinfo-container div{
        font-size: 12px;
        color: #4aafff;
    }
</style>