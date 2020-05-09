<template>
    <div>
        <div style="display: flex;justify-content: space-between;">
            <el-button icon="el-icon-plus" type="primary" @click="showAddSalaryView">添加工资账套</el-button>
            <el-button icon="el-icon-refresh" type="success" @click="initSalaries"></el-button>

        </div>
        <div style="margin-top: 10px">
            <el-table :data="salaries" border stripe>
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column width="120" prop="name" label="账套名称"></el-table-column>
                <el-table-column width="70" prop="basicSalary" label="基本工资"></el-table-column>
                <el-table-column width="70" prop="trafficSalary" label="交通补助"></el-table-column>
                <el-table-column width="70" prop="lunchSalary" label="午餐补助"></el-table-column>
                <el-table-column width="70" prop="bonus" label="奖金"></el-table-column>
                <el-table-column width="70" prop="createDate" label="启用时间"></el-table-column>
                <el-table-column label="养老金" align="center">
                    <el-table-column width="70" prop="pensionPer" label="比率"></el-table-column>
                    <el-table-column width="70" prop="pensionBase" label="基数"></el-table-column>
                </el-table-column>
                <el-table-column label="医疗保险" align="center">
                    <el-table-column width="70" prop="medicalPer" label="比率"></el-table-column>
                    <el-table-column width="70" prop="medicalBase" label="基数"></el-table-column>
                </el-table-column>
                <el-table-column label="公积金" align="center">
                    <el-table-column width="70" prop="accumulationFundPer" label="比率"></el-table-column>
                    <el-table-column width="70" prop="accumulationFundBase" label="基数"></el-table-column>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button @click="showEditSalaryView(scope.row)">编辑</el-button>
                        <el-button type="danger" @click="deleteSalaryHandler(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <el-dialog
                :title="dialogTitle"
                :visible.sync="dialogVisible"
                width="50%">
            <div style="display: flex;justify-content: space-around;align-items: center">
                <el-steps direction="vertical" :active="activeItemIndex">
                    <el-step :title="itemName" v-for="(itemName,index) in salaryItemName" :key="index"></el-step>
                </el-steps>
                <el-input
                        v-for="(value,title,index) in salary"
                        v-model="salary[title]"
                        :placeholder="'请输入'+salaryItemName[index]+'...'"
                        v-show="activeItemIndex==index"
                         style="width: 200px;"
                        :key="index">

                </el-input>
            </div>
            <span slot="footer" class="dialog-footer">
    <el-button @click="preStep">{{activeItemIndex==0?'取消':'上一步'}}</el-button>
    <el-button type="primary" @click="nextStep">{{activeItemIndex==10?'完成':'下一步'}}</el-button>
  </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "SalSob",
        data(){
            return{
                dialogVisible:false,
                dialogTitle:'添加工资账套',
                salaries:[],
                salaryItemName:[
                    '基本工资',
                    '交通补助',
                    '午餐补助',
                    '奖金',
                    '养老金比率',
                    '养老金基数',
                    '医疗保险比率',
                    '医疗保险基数',
                    '公积金比率',
                    '公积金基数',
                    '账号名称'
                ],
                salary:{
                    basicSalary:0,
                    trafficSalary:0,
                    lunchSalary:0,
                    bonus:0,
                    pensionPer:0,
                    pensionBase:0,
                    medicalPer:0,
                    medicalBase:0,
                    accumulationFundPer:0,
                    accumulationFundBase:0,
                    name:''
                },
                activeItemIndex:0,
            }
        },
        methods:{

            deleteSalaryHandler(data){
                this.$confirm('此操作将永久删除【'+data.name+'】职称, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/vhr-web/salary/sob/"+data.id).then(resp=>{
                        if (resp){
                            this.initSalaries();
                        }
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });

            },
            nextStep(){
                if (this.activeItemIndex==10){
                    if (this.dialogTitle=='修改工资账套'){
                        this.putRequest("/vhr-web/salary/sob/",this.salary).then(resp=>{
                            if (resp){
                                this.initSalaries();
                                this.dialogVisible=false;
                            }
                        });
                    }else{
                        this.postRequest("/vhr-web/salary/sob/",this.salary).then(resp=>{
                            if (resp){
                                this.initSalaries();
                                this.dialogVisible=false;

                            }
                        });
                    }

                    return;
                }
                this.activeItemIndex++;
            },
            preStep(){
                if (this.activeItemIndex==0){
                    this.dialogVisible=false;
                    return;
                }
                this.activeItemIndex--;
            },
            showAddSalaryView(){
                this.activeItemIndex=0;
                this.salary={
                    basicSalary:0,
                    trafficSalary:0,
                    lunchSalary:0,
                    bonus:0,
                    pensionPer:0,
                    pensionBase:0,
                    medicalPer:0,
                    medicalBase:0,
                    accumulationFundPer:0,
                    accumulationFundBase:0,
                    name:''
                };
                this.dialogVisible=true;
            },
            showEditSalaryView(data){
                this.dialogTitle='修改工资账套';
                this.activeItemIndex=0;
                this.salary={
                    basicSalary:data.basicSalary,
                    trafficSalary:data.trafficSalary,
                    lunchSalary:data.lunchSalary,
                    bonus:data.bonus,
                    pensionPer:data.pensionPer,
                    pensionBase:data.pensionBase,
                    medicalPer:data.medicalPer,
                    medicalBase:data.medicalBase,
                    accumulationFundPer:data.accumulationFundPer,
                    accumulationFundBase:data.accumulationFundBase,
                    name:data.name,
                    id:data.id
                };
                this.dialogVisible=true;
            },
            initSalaries(){
                this.getRequest("/vhr-web/salary/sob/").then(resp=>{
                   if (resp){
                       this.salaries=resp;
                   }
                });
            }
        },
        mounted() {
            this.initSalaries();
        }
    }
</script>

<style scoped>

</style>