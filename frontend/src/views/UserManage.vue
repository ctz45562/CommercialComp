<template>
  <div>
<!--    <div class="card" style="margin-bottom: 5px">-->
<!--      <el-input style="width: 240px; margin-right: 10px" v-model="data.name" placeholder="请输入名称查询" prefix-icon="Search"></el-input>-->
<!--      <el-button type="primary">查 询</el-button>-->
<!--      <el-button type="warning">重 置</el-button>-->
<!--    </div>-->
    <div class="card" style="margin-bottom: 5px">
      <el-button type="primary">新增</el-button>
      <el-button type="warning">删除</el-button>
      <el-button type="info">编辑</el-button>

<!--      <el-button type="info">导入</el-button>-->
<!--      <el-button type="success">导出</el-button>-->
    </div>

    <div class="card" style="margin-bottom: 5px">
      <el-table :data="data.tableData" stripe>
        <el-table-column label="用户名" prop="name" />
        <el-table-column label="邮箱" prop="email" />
        <el-table-column label="密码" prop="password" />
      </el-table>
      <div style="margin-top: 15px">
        <el-pagination
            v-model:current-page="data.pageNum"
            v-model:page-size="data.pageSize"
            :page-sizes="[5, 10, 15, 20]"
            background
            layout="total, sizes, prev, pager, next, jumper"
            :total="data.total"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import { Search } from "@element-plus/icons-vue"

const data = reactive({
  name: null,
  tableData: [
    {  name: '亲哥哥哥', email:'111@111', password:'123456'},
    {  name: '小心小心', email:'111231@1333.c11', password:'ccascnvdovn6' },
    {  name: 'CCCCas', email:'cctz45562@163.com', password:'45555656???aaas.' },
  ],
  pageNum: 1,
  pageSize: 10,
  total: 3
})
</script>

<style scoped>

</style>