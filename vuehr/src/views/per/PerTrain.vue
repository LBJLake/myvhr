<template>
    <div>
        <div style="display: flex;justify-content: space-between;margin-bottom: 10px;width: 80%">
            <el-input
                    prefix-icon="el-icon-search"
                    placeholder="请输入员工名称进行搜索"
                    @input="initEmptrains"
                    v-model="keyword"
                    style="width: 300px;">
            </el-input>

            <el-button type="primary" icon="el-icon-plus" @click="showEmptrainView('添加员工培训')">添加员工培训</el-button>
        </div>
        <div>
            <el-table
                    :data="emptranins"
                    border
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
                        prop="employee.name"
                        label="员工姓名"
                        width="120">
                </el-table-column>
                <el-table-column
                        prop="trainDate"
                        label="培训日期"
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="trainContent"
                        label="培训内容"
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="remark"
                        label="备注"
                        width="150">
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                @click="showEmptrainView('编辑员工培训', scope.row)">编辑</el-button>
                        <el-button
                                size="mini"
                                type="danger"
                                @click="deleteEmptrain(scope.row.id)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <el-dialog
                :title="title"
                :visible.sync="dialogVisible"
                width="30%">
            <div>
                <table >
                    <tr>
                        <td><el-tag style="margin-right: 10px">员工姓名：</el-tag></td>
                        <td>
                            <el-select filterable  v-model="emptrain.eid" placeholder="请选择"员工姓名...>
                                <el-option
                                        v-for="item in employees"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        </td>
                    </tr>
                    <tr>
                        <td><el-tag>培训日期：</el-tag></td>
                        <td>
                            <el-date-picker
                                    v-model="emptrain.trainDate"
                                    type="date"
                                    placeholder="选择日期">
                            </el-date-picker>
                        </td>
                    </tr>
                    <tr>
                        <td><el-tag>培训内容：</el-tag></td>
                        <td><el-input type="text" size="mini" v-model="emptrain.trainContent"></el-input></td>
                    </tr>
                    <tr>
                        <td><el-tag>备注：</el-tag></td>
                        <td><el-input type="text" size="mini" v-model="emptrain.remark"></el-input></td>
                    </tr>
                </table>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addEditEmptrain">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import {deleteRequest, getRequest, postRequest, putRequest} from "../../utils/api";

    export default {
        name: "PerTrain",
        data(){
            return{
                emptranins:[],
                emptrain:{
                    eid:null,
                    trainDate:null,
                    trainContent:null,
                    remark:null
                },
                employees:[],
                keyword:'',
                dialogVisible:false,
                title:'添加员工培训',

            }
        },
        methods:{
            deleteEmptrain(id){
                this.$confirm('此操作将永久删除该条员工培训, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    deleteRequest("/vhr-web/personnel/train/"+id).then(resp=>{
                        if (resp){
                            this.initEmptrains();
                        }
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });



            },
            addEditEmptrain(){
                if (this.emptrain.id){
                    //修改
                    putRequest("/vhr-web/personnel/train/",this.emptrain).then(resp=>{
                        if (resp){
                            this.initEmptrains();
                            this.dialogVisible=false;
                        }
                    });
                }else {
                    //添加
                    postRequest("/vhr-web/personnel/train/",this.emptrain).then(resp=>{
                        if (resp){
                            this.initEmptrains();
                            this.dialogVisible=false;
                        }
                    });
                }
            },
            showEmptrainView(title,data){
                this.title=title;
                if (title=='添加员工培训'){
                    this.emptrain.id=null;
                    this.emptrain.eid=null;
                    this.emptrain.trainDate=null;
                    this.emptrain.trainContent='';
                    this.emptrain.remark='';
                }else {
                    this.emptrain=Object.assign({},data);
                }
                this.initEmployees();
                this.dialogVisible=true;
            },
            initEmployees(){
                getRequest("/vhr-web/personnel/train/employees").then(resp=>{
                    if (resp){
                        this.employees=resp;
                    }
                });
            },
            initEmptrains(){
                getRequest("/vhr-web/personnel/train/?keyword="+this.keyword).then(resp=>{
                    if (resp){
                        this.emptranins=resp;
                    }
                });
            },
        },
        mounted() {
            this.initEmptrains();
        }
    }
</script>

<style scoped>

</style>