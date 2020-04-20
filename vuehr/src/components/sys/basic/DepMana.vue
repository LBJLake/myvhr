<template>
        <div style="width: 500px">
            <el-input
                    prefix-icon="el-icon-search"
                    placeholder="请输入部门名称进行搜索"
                    v-model="filterText">
            </el-input>

            <el-tree
                    v-loading="loading"
                    element-loading-text="正在加载中..."
                    element-loading-spinner="el-icon-loading"
                    element-loading-background="rgba(0, 0, 0, 0.8)"
                    :data="deps"
                    :props="defaultProps"
                    :filter-node-method="filterNode"
                    :expand-on-click-node="false"
                    ref="tree">

                 <span class="custom-tree-node" style="display: flex;justify-content: space-between;width: 100%" slot-scope="{ node, data }">
                    <span>{{ node.label }}</span>
                    <span>
                      <el-button
                              class="depBtn"
                              type="primary"
                              size="mini"
                              @click="() => showAddDepView(data)">
                        添加部门
                      </el-button>
                      <el-button
                              class="depBtn"
                              type="danger"
                              size="mini"
                              @click="() => deleteDep(data)">
                        删除部门
                      </el-button>
                    </span>
                 </span>

            </el-tree>

            <el-dialog
                    title="添加部门"
                    :visible.sync="dialogVisible"
                    width="30%">
                <div>
                    <table>
                        <tr>
                            <td><el-tag>上级部门</el-tag></td>
                            <td>{{pname}}</td>
                        </tr>
                        <tr>
                            <td><el-tag>部门名称</el-tag></td>
                            <td><el-input v-model="dep.name" placeholder="请输入部门名称..." @keydown.enter.native="doAddDep"></el-input></td>
                        </tr>
                    </table>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">取 消</el-button>
                    <el-button type="primary" @click="doAddDep">确 定</el-button>
                </span>
            </el-dialog>
        </div>

</template>

<script>
    export default {
        name: "DepMana",
        methods: {
            initDeps(){
                this.loading=true;
                this.getRequest("/system/basic/department/").then(resp=>{
                    this.loading=false;
                    if (resp){
                        this.deps=resp;
                    }
                });
            },
            showAddDepView(data){
                this.pname=data.name;
                this.dep.parentId=data.id;
                this.dialogVisible=true;

            },
                removeDepFromDeps(p,deps, id) {
                if (deps==null)return;
                for (let i=0;i<deps.length;i++){
                    let d=deps[i];
                    if (id==d.id){
                        deps.splice(i,1);
                        if (deps.length==0){
                            p.parent=false;
                        }
                        return;
                    }else {
                        this.removeDepFromDeps(d,d.children,id);
                    }
                }
            },
            deleteDep(data){
                if (data.parent){
                    this.$message.error("父部门删除失败");
                    return;
                }
                this.$confirm('此操作将永远删除【'+data.name+'】部门, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/basic/department/"+data.id).then(resp=>{
                        if (resp){
                            this.removeDepFromDeps(null,this.deps,data.id);
                        }
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消操作'
                    });
                });
            },
            //递归动态添加部门
            addDep2Deps(deps,dep){
                if (deps==null)return;
                for (let i=0;i<deps.length;i++){
                    let d=deps[i];
                    if (d.id==dep.parentId){
                        d.children=d.children.concat(dep);
                        d.parent=true;
                        return;
                    }else{
                        this.addDep2Deps(d.children,dep);
                    }
                }
            },
            doAddDep(){
                if (this.dep.name&&this.dep.name.trim().length>0){
                    this.postRequest("/system/basic/department/",this.dep).then(resp=>{
                        if (resp){
                            this.addDep2Deps(this.deps,resp.obj);
                            this.dialogVisible=false;
                            this.dep.name='';
                        }
                    });
                }else {
                    this.$message.error('部门名称不能为空！');
                }
            },
            filterNode(value, data) {
                if (!value) return true;
                return data.name.indexOf(value) !== -1;
            },
        },
        mounted() {
            this.initDeps();
        },
        data(){
            return{
                dialogVisible:false,
                filterText:'',
                deps:[],
                dep:{
                    name:'',
                    parentId:-1,
                },
                pname:'',
                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
                loading:false,

            }
        },
        watch: {
            filterText(val) {
                this.$refs.tree.filter(val);
            }
        },
    }
</script>

<style>
    .depBtn{
        padding:2px;
    }

</style>