<template>
    <div>
        <div>
            <el-input
                    size="small"
                    style="width: 300px;"
                    placeholder="添加职称..."
                    prefix-icon="el-icon-plus"
                    v-model="jl.name">
            </el-input>
            <el-select size="small" v-model="jl.titleLevel" placeholder="职称等级" style="margin-left: 5px;margin-right: 5px">
                <el-option
                        v-for="item in titleLevels"
                        :key="item"
                        :label="item"
                        :value="item">
                </el-option>
            </el-select>
            <el-button icon="el-icon-plus" size="small" type="primary" @click="addJobLevel">添加</el-button>
        </div>

        <div style="margin-top: 10px">
            <el-table
                    v-loading="loading"
                    element-loading-text="正在加载中..."
                    element-loading-spinner="el-icon-loading"
                    element-loading-background="rgba(0, 0, 0, 0.8)"
                    :data="jls"
                    border
                    stripe
                    @selection-change="handleSelectionChange"
                    size="small"
                    style="width: 80%">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="id"
                        label="编号"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="name"
                        width="180"
                        label="职称名称">
                </el-table-column>
                <el-table-column
                        prop="titleLevel"
                        width="150"
                        label="职称级别">
                </el-table-column>
                <el-table-column
                        prop="createDate"
                        width="150"
                        label="创建时间">
                </el-table-column>
                <el-table-column
                        width="120"
                        label="是否启用">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.enabled" type="success">已启动</el-tag>
                        <el-tag v-else type="danger">未启动</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                        width="175"
                        label="操作">
                    <template slot-scope="scope">
                        <el-button size="small" @click="showEditView(scope.row)">编辑</el-button>
                        <el-button size="small" @click="deleteHandler(scope.row)" type="danger">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-button type="danger" size="small" style="margin-top: 10px;" :disabled="multipleSelection.length==0" @click="deleteMany">
                批量删除
            </el-button>
        </div>

        <el-dialog
                title="修改职称"
                :visible.sync="dialogVisible"
                width="30%">
            <div>
                <table>
                    <tr>
                        <td><el-tag>职称名</el-tag></td>
                        <td><el-input size="small" v-model="updateJl.name"></el-input></td>
                    </tr>
                    <tr>
                        <td><el-tag>职称级别</el-tag></td>
                        <td>
                            <el-select size="small" v-model="updateJl.titleLevel" placeholder="职称等级" style="margin-left: 5px;margin-right: 5px">
                                <el-option
                                        v-for="item in titleLevels"
                                        :key="item"
                                        :label="item"
                                        :value="item">
                                </el-option>
                            </el-select>
                        </td>
                    </tr>
                    <tr>
                        <td><el-tag>是否启用</el-tag></td>
                        <td>
                            <el-switch
                                v-model="updateJl.enabled"
                                active-text="启动"
                                inactive-text="禁用">
                            </el-switch>
                        </td>
                    </tr>
                </table>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button size="small" @click="dialogVisible = false">取 消</el-button>
                <el-button size="small" type="primary" @click="doUpdate">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "JobLevelMana",
        data(){
            return{
                jl:{
                    name:'',
                    titleLevel:'',
                },
                jls:{},
                titleLevels:[
                    '正高级',
                    '副高级',
                    '中级',
                    '初级',
                    '员级',

                ],
                dialogVisible:false,
                updateJl:{
                    name:'',
                    titleLevel:'',
                    enabled:false
                },
                multipleSelection:[],
                loading:false,

            }
        },
        mounted() {
            this.initJls();
        },
        methods:{
            addJobLevel(){
                if (this.jl.name&&this.jl.name.trim().length>0&&this.jl.titleLevel){
                    this.postRequest("/system/basic/joblevel/",this.jl).then(resp=>{
                        if (resp){
                            this.initJls();
                        }
                    });
                }else {
                    this.$message.error("添加字段不可以为空！");
                }
            },
            deleteHandler(data){
                this.$confirm('此操作将永久删除【'+data.name+'】职称, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/basic/joblevel/"+data.id).then(resp=>{
                        if (resp){
                            this.initJls();
                        }
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            showEditView(data){
                Object.assign(this.updateJl,data);
                this.dialogVisible=true;

            },
            doUpdate(){
                this.putRequest("/system/basic/joblevel/",this.updateJl).then(resp=>{
                    if (resp){
                        this.initJls();
                        this.dialogVisible=false;

                    }
                });
            },
            deleteMany(){
                this.$confirm('此操作将永久删除【'+this.multipleSelection.length+'】条记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let ids='?';
                    this.multipleSelection.forEach(item=>{
                        ids+='ids='+item.id+'&';
                    });
                    this.deleteRequest("/system/basic/joblevel/"+ids).then(resp=>{
                        if (resp){
                            this.initJls();
                        }
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            //获取点击表格选中框行数组
            handleSelectionChange(val){
                this.multipleSelection=val;
            },
            initJls(){
                this.loading=true;
                this.getRequest("/system/basic/joblevel/").then(resp=>{
                    this.loading=false;
                    if (resp){
                        this.jls=resp;
                        this.jl={
                            name:'',
                            titleLevel:'',
                        };
                    }
                });
            },
        }
    }
</script>

<style scoped>

</style>