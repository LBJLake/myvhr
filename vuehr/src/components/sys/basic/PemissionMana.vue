<template>
    <div>
        <div class="permissionManaTool">
            <el-input size="small" placeholder="请输入角色英文名" v-model="role.name">
                <template slot="prepend">ROLE_</template>
            </el-input>
            <el-input size="small" placeholder="请输入角色中文名" v-model="role.nameZh" @keydown.enter.native="doAddRole"></el-input>
            <el-button type="primary" size="small" icon="el-icon-plus" @click="doAddRole">添加角色</el-button>
        </div>
        <div class="permissManaMain">
            <el-collapse
                    v-loading="loading"
                    element-loading-text="正在加载中..."
                    element-loading-spinner="el-icon-loading"
                    element-loading-background="rgba(0, 0, 0, 0.8)"
                    v-model="activeName"
                    accordion
                    @change="change">
                <el-collapse-item :title="r.nameZh" :name="r.id" v-for="(r,index) in roles" :key="index">
                    <el-card class="box-card">
                        <div slot="header" class="clearfix">
                            <span>可访问的资源</span>
                            <el-button style="float: right; padding: 3px 0;color: red;" icon="el-icon-delete" type="text" @click="doDeleteRole(r)"></el-button>
                        </div>
                        <div>
                            <el-tree
                                    show-checkbox node-key="id"
                                    ref="tree"
                                    :key="index"
                                    :default-checked-keys="selectedMenus"
                                    :data="allMenus"
                                    :props="defaultProps">

                            </el-tree>
                            <div style="display: flex;justify-content: flex-end">
                                <el-button size="mini" @click="cancelUpdate">取消修改</el-button>
                                <el-button size="mini" type="primary" @click="doUpdate(r.id,index)">确认修改</el-button>
                            </div>
                        </div>
                    </el-card>
                </el-collapse-item>
            </el-collapse>
        </div>
    </div>
</template>

<script>
    export default {
        name: "PemissionMana",
        data(){
            return{
                role:{
                    name:'',
                    nameZh:'',
                },
                selectedMenus:[],
                roles:[],
                allMenus:[],
                activeName:-1,
                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
                loading:false,

            }
        },
        mounted() {
            this.initRoles();
        },
        methods:{
            initRoles(){
                this.loading=true;
                this.getRequest("/vhr-web/system/basic/permiss/").then(resp=>{
                    this.loading=false;
                    if (resp){
                        this.roles=resp;
                    }
                });
            },
            change(rid){
              if (rid){
                  this.initAllMenus();
                  this.initSelectedMenus(rid);
              }
            },
            initAllMenus(){
                this.getRequest("/vhr-web/system/basic/permiss/menus").then(resp=>{
                    if (resp){
                        this.allMenus=resp;
                    }
                });
            },
            initSelectedMenus(rid){
                this.getRequest("/vhr-web/system/basic/permiss/mids/"+rid).then(resp=>{
                    if (resp){
                        this.selectedMenus=resp;
                    }
                });
            },
            doUpdate(rid,index){
                let tree=this.$refs.tree[index];
                let selectedKeys=tree.getCheckedKeys(true);
                let url='/vhr-web/system/basic/permiss/?rid='+rid;
                selectedKeys.forEach(key=>{
                    url+='&mids='+key;
                });
                this.putRequest(url).then(resp=>{
                    if (resp){
                        this.activeName=-1;
                    }
                });

            },
            cancelUpdate(){
                this.activeName=-1;
            },
            doAddRole(){
                if (this.role.name&&this.role.nameZh&&this.role.name.trim().length>0&&this.role.nameZh.trim().length>0){
                    this.postRequest("/vhr-web/system/basic/permiss/",this.role).then(resp=>{
                        if (resp){
                            this.role={
                                name:'',
                                nameZh:''
                            };
                            this.initRoles();
                        }
                    });
                }else {
                    this.$message.error('角色名称不能为空！');
                }
            },
            doDeleteRole(role){
                this.$confirm('此操作将永远删除【'+role.nameZh+'】角色, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/vhr-web/system/basic/permiss/"+role.id).then(resp=>{
                        if (resp){
                            this.initRoles();
                        }
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消操作'
                    });
                });
            },
        }
    }
</script>

<style>
    .permissionManaTool{
        display: flex;
        justify-content: flex-start;
    }
    .permissionManaTool .el-input{
        width: 300px;
        margin-right: 6px;
    }
    .permissManaMain{
        margin-top: 10px;
        width: 700px;
    }

</style>