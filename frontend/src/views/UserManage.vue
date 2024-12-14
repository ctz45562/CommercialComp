<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input style="width: 240px; margin-right: 10px" v-model="data.search_name" placeholder="请输入名称查询" prefix-icon="Search"></el-input>
      <el-button type="primary" @click="load">查 询</el-button>
      <el-button type="warning" @click="reset">重 置</el-button>
    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-button type="primary" @click="openNewForm">新 增</el-button>
      <el-button type="warning" @click="batchDel">批量删除</el-button>

<!--      <el-button type="info">导入</el-button>-->
<!--      <el-button type="success">导出</el-button>-->
    </div>

    <div class="card" style="margin-bottom: 5px">
      <el-table :data="data.userList" @selection-change="onSelectionChange" stripe>
        <el-table-column type="selection"/>
        <el-table-column label="用户名" prop="name" width="200px" show-overflow-tooltip/>
        <el-table-column label="身份" prop="role" width="150px"/>
        <el-table-column label="邮箱" prop="email" />
        <el-table-column label="密码" prop="password" />
        <el-table-column label="操作">
          <template #default="scope">
            <el-button type="primary" @click="edit(scope.row)" :icon="Edit" link>编辑</el-button>
            <el-button type="danger" @click="del(scope.row.name)" :icon="Delete" link>删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin-top: 15px">
        <el-pagination
            @current-change="load"
            @size-change="load"
            v-model:current-page="data.pageNum"
            v-model:page-size="data.pageSize"
            :page-sizes="[5, 10, 15, 20]"
            background
            layout="total, sizes, prev, pager, next, jumper"
            :total="data.total"
        />
      </div>
    </div>

    <el-dialog title="新增用户" v-model="data.newFormVisible" width="500">
      <el-form :model="data.newForm" label-width="80px" style="padding-right: 40px; padding-top: 20px">
        <el-form-item label="用户名">
          <el-input v-model="data.newForm.name" placeholder="请输入用户名" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="data.newForm.password" placeholder="请输入密码" autocomplete="off" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="data.newForm.email" placeholder="请输入邮箱" autocomplete="off" />
        </el-form-item>

        <el-form-item label="身份">
          <el-radio-group v-model="data.newForm.role">
            <el-radio value="user" label="用户"></el-radio>
            <el-radio value="admin" label="管理员"></el-radio>
          </el-radio-group>
        </el-form-item>

      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="newUser">新 增</el-button>
          <el-button @click="data.newFormVisible = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog title="编辑用户" v-model="data.editFormVisible" width="500">
      <el-form :model="data.editForm" label-width="80px" style="padding-right: 40px; padding-top: 20px">
        <el-form-item label="用户名">
          <el-input v-model="data.editForm.name" placeholder="请输入用户名" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="data.editForm.password" placeholder="请输入密码" autocomplete="off" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="data.editForm.email" placeholder="请输入邮箱" autocomplete="off" />
        </el-form-item>

        <el-form-item label="身份">
          <el-radio-group v-model="data.editForm.role">
            <el-radio value="user" label="用户"></el-radio>
            <el-radio value="admin" label="管理员"></el-radio>
          </el-radio-group>
        </el-form-item>

      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="putUpdate">确 定</el-button>
          <el-button @click="data.editFormVisible = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import {Delete, Edit, Search} from "@element-plus/icons-vue"
import request from "@/utils/request.js";
import {ElMessage, ElMessageBox} from "element-plus";


const data = reactive({
  userList: [],
  pageNum: 1,
  pageSize: 10,
  total: 3,
  search_name: null,
  newFormVisible: false,
  newForm: {},
  editFormVisible: false,
  editForm: {},
  selectUsers:{}
})

const load = ()=>{
  request.get('/user/getPage',{
    params:{
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.search_name
    }
  }).then(
      res=>{
        data.userList=res.data.list
        data.total=res.data.total
      }
  );
}

const reset = ()=>{
  data.search_name=null
  load()
}

const openNewForm = ()=>{
  data.newFormVisible=true
  data.newForm={}
}

const newUser = ()=>{
  request.post("/user/newUser", data.newForm).then(
      res=>{
        console.log(res)
        if(res.code == 200){
          data.newFormVisible=false
          ElMessage.success('添加成功')
          load()
        }
        else {
          ElMessage.error(res.message)
        }
      }
  )

}

const edit = (user)=>{
  data.editForm=JSON.parse(JSON.stringify(user))
  data.editFormVisible=true
}

const putUpdate = ()=>{
  console.log(typeof data.editForm)
  request.put("/user/updateUser",data.editForm).then(
      res=>{
        console.log(res)
        if(res.code == 200){
          data.editFormVisible=false
          ElMessage.success('更新成功')
          load()
        }
        else {
          ElMessage.error(res.message)
        }
      }
  )
}

const del=(name)=>{
  ElMessageBox.confirm('确认删除用户'+name+'？','确认删除',{
    type:'warning'
  }).then(()=>{
    // request.put("/user/updateUser",JSON.parse(JSON.stringify(user))).then(
    request.delete("/user/deleteUser",{
      data: [name]
    }).then(
        (res)=>{
          console.log(res)
          if(res.code == 200){
            ElMessage.success('删除成功')
            load()
          }
          else {
            ElMessage.error(res.message)
          }
        }
    )
  })
}

const onSelectionChange = (rows)=>{
  data.selectUsers = rows;
}

const batchDel = ()=>{
  if(data.selectUsers.length == 0){
    ElMessage.warning("未选择用户")
    return
  }
  ElMessageBox.confirm('确认删除选中的所有用户？','确认删除',{
    type:'warning'
  }).then(()=>{
    // request.put("/user/updateUser",JSON.parse(JSON.stringify(user))).then(
    request.delete("/user/deleteUser",{
      data: data.selectUsers.map(row=>row.name)
    }).then(
        (res)=>{
          console.log(res)
          if(res.code == 200){
            ElMessage.success('删除成功')
            load()
          }
          else {
            ElMessage.error(res.message)
          }
        }
    )
  })
}

load()
</script>

<style scoped>

</style>