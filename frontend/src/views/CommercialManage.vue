<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input style="width: 240px; margin-right: 10px" v-model="data.search_name" placeholder="请输入商品名查询" prefix-icon="Search"></el-input>
      <el-button type="primary" @click="load">查 询</el-button>
      <el-button type="warning" @click="reset">重 置</el-button>
    </div>
    <div class="card"  style="margin-bottom: 5px">
      <el-button type="primary" @click="data.filter_platform=null;load()" :disabled="data.filter_platform==null">全部显示</el-button>
      <el-button type="info" @click="data.filter_platform='淘宝';load()" :disabled="data.filter_platform=='淘宝'">淘宝</el-button>
      <el-button type="danger" @click="data.filter_platform='京东';load()" :disabled="data.filter_platform=='京东'">京东</el-button>

    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-button type="primary" @click="openNewForm">新 增</el-button>
      <el-button type="warning" @click="batchDel">批量删除</el-button>

      <!--      <el-button type="info">导入</el-button>-->
      <!--      <el-button type="success">导出</el-button>-->
    </div>

    <div class="card" style="margin-bottom: 5px">
      <el-table :data="data.commercialList" @selection-change="onSelectionChange" stripe>
        <el-table-column type="selection"/>
        <el-table-column label="编号" prop="id" width="80px"/>
        <el-table-column label="平台" prop="platform" width="90px"/>
        <el-table-column label="商品名" prop="title" show-overflow-tooltip/>
        <el-table-column label="价格" prop="price" width="100px"/>
        <el-table-column label="店铺" prop="shop" />
        <el-table-column label="商品链接" prop="url" width="100px">
          <template #default="scope">
            <el-link :href="scope.row.url" type="primary" :icon="Link" target="_blank">Link</el-link>
          </template>
        </el-table-column>
        <el-table-column label="店铺链接" width="100px">
          <template #default="scope">
            <el-link :href="scope.row.shopUrl" type="primary" :icon="Link" target="_blank">Link</el-link>
          </template>
        </el-table-column>
        <el-table-column label="图片链接" width="100px">
          <template #default="scope">
            <el-link :href="scope.row.imgUrl" type="primary" :icon="Link" target="_blank">Link</el-link>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button type="primary" @click="edit(scope.row)" :icon="Edit" link>编辑</el-button>
            <el-button type="danger" @click="del(scope.row.id,scope.row.title)" :icon="Delete" link>删除</el-button>
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

    <el-dialog title="新增商品" v-model="data.newFormVisible" width="500">
      <el-form :model="data.newForm" label-width="80px" style="padding-right: 40px; padding-top: 20px">
        <el-form-item label="商品名">
          <el-input v-model="data.newForm.title" placeholder="请输入商品名" autocomplete="off" />
        </el-form-item>
        <el-form-item label="价格">
          <el-input-number v-model="data.newForm.price" :precision="2" :step="0.1" />
        </el-form-item>
        <el-form-item label="店铺名称">
          <el-input v-model="data.newForm.shop" placeholder="请输入店铺名称" autocomplete="off" />
        </el-form-item>
        <el-form-item label="商品链接">
          <el-input v-model="data.newForm.url" placeholder="请输入商品链接" autocomplete="off" />
        </el-form-item>
        <el-form-item label="图片链接">
          <el-input v-model="data.newForm.imgUrl" placeholder="请输入图片链接" autocomplete="off" />
        </el-form-item>
        <el-form-item label="店铺链接">
          <el-input v-model="data.newForm.shopUrl" placeholder="请输入店铺链接" autocomplete="off" />
        </el-form-item>
        <el-form-item label="平台">
          <el-radio-group v-model="data.newForm.platform">
            <el-radio value="淘宝" label="淘宝"></el-radio>
            <el-radio value="京东" label="京东"></el-radio>
          </el-radio-group>
        </el-form-item>

      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="newCommercial">新 增</el-button>
          <el-button @click="data.newFormVisible = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog title="编辑商品" v-model="data.editFormVisible" width="500">
      <el-form :model="data.editForm" label-width="80px" style="padding-right: 40px; padding-top: 20px">
        <el-form-item label="商品名">
          <el-input v-model="data.editForm.title" placeholder="请输入商品名" autocomplete="off" />
        </el-form-item>
        <el-form-item label="价格">
          <el-input-number v-model="data.editForm.price" :precision="2" :step="0.1" />
        </el-form-item>
        <el-form-item label="店铺名称">
          <el-input v-model="data.editForm.shop" placeholder="请输入店铺名称" autocomplete="off" />
        </el-form-item>
        <el-form-item label="商品链接">
          <el-input v-model="data.editForm.url" placeholder="请输入商品链接" autocomplete="off" />
        </el-form-item>
        <el-form-item label="图片链接">
          <el-input v-model="data.editForm.imgUrl" placeholder="请输入图片链接" autocomplete="off" />
        </el-form-item>
        <el-form-item label="店铺链接">
          <el-input v-model="data.editForm.shopUrl" placeholder="请输入店铺链接" autocomplete="off" />
        </el-form-item>
        <el-form-item label="平台">
          <el-radio-group v-model="data.editForm.platform">
            <el-radio value="淘宝" label="淘宝"></el-radio>
            <el-radio value="京东" label="京东"></el-radio>
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
import {Delete, Edit, Link, Search} from "@element-plus/icons-vue"
import request from "@/utils/request.js";
import {ElMessage, ElMessageBox} from "element-plus";


const data = reactive({
  commercialList: [],
  pageNum: 1,
  pageSize: 10,
  total: 3,
  search_name: null,
  filter_platform: null,
  newFormVisible: false,
  newForm: {},
  editFormVisible: false,
  editForm: {},
  selectCommercials:{}
})

const load = ()=>{
  request.get('/commercial/getPage',{
    params:{
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      title: data.search_name,
      platform: data.filter_platform
    }
  }).then(
      res=>{
        data.commercialList=res.data.list
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

const newCommercial = ()=>{
  request.post("/commercial/newCommercial", data.newForm).then(
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

const edit = (commercial)=>{
  data.editForm=JSON.parse(JSON.stringify(commercial))
  data.editFormVisible=true
}

const putUpdate = ()=>{
  request.put("/commercial/updateCommercial",data.editForm).then(
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

const del=(id,title)=>{
  ElMessageBox.confirm('确认删除商品'+title+'？','确认删除',{
    type:'warning'
  }).then(()=>{
    // request.put("/user/updateUser",JSON.parse(JSON.stringify(user))).then(
    request.delete("/commercial/deleteCommercial",{
      data: [id]
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
  data.selectCommercials = rows;
}

const batchDel = ()=>{
  if(data.selectCommercials.length == 0){
    ElMessage.warning("未选择商品")
    return
  }
  ElMessageBox.confirm('确认删除选中的所有商品？','确认删除',{
    type:'warning'
  }).then(()=>{
    // request.put("/user/updateUser",JSON.parse(JSON.stringify(user))).then(
    request.delete("/commercial/deleteCommercial",{
      data: data.selectCommercials.map(row=>row.id)
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