<template>
    <div>
        <el-tabs v-model="activeName" type="card">
            <el-tab-pane label="部门管理" name="depMana"><DepMana></DepMana></el-tab-pane>
            <el-tab-pane label="职位管理" name="posMana"><PosMana></PosMana></el-tab-pane>
            <el-tab-pane label="职称管理" name="jobLevelMana"><JobLevelMana></JobLevelMana></el-tab-pane>
            <el-tab-pane label="奖惩规则" name="ecMana"><EcMana></EcMana></el-tab-pane>
            <el-tab-pane label="权限组" name="pemissionMana"><PemissionMana></PemissionMana></el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
    import DepMana from "../../components/sys/basic/DepMana";
    import PosMana from "../../components/sys/basic/PosMana";
    import JobLevelMana from "../../components/sys/basic/JobLevelMana";
    import PemissionMana from "../../components/sys/basic/PemissionMana";
    import EcMana from "../../components/sys/basic/EcMana";
    export default {
        name: "SysBasic",
        data(){
            return{
                activeName:'depMana',
            }
        },
        components:{
            DepMana,
            PosMana,
            JobLevelMana,
            PemissionMana,
            EcMana,
        }
    }
</script>

<style>

</style>