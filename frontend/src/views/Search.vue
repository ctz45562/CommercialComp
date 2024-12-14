

<template>
<div>
  <div class="card" style="margin-bottom: 5px">
    <el-input style="width: 300px; margin-right: 10px" v-model="data.search_name" placeholder="请输入名称查询" prefix-icon="Search"></el-input>
    <el-button type="primary" @click="search">查 询</el-button>
    <el-button type="warning" @click="reset">重 置</el-button>
  </div>
    <div class="card">
    <el-table v-loading="data.taobao_loading || data.jingdong_loading" :data="data.commercial_list" stripe>
      <el-table-column width="150px">
        <template #default="scope">
          <el-image :src="scope.row.imgUrl" alt="" style="width:60px" loading="lazy"/>
        </template>
      </el-table-column>
      <el-table-column label="平台" width="150px" prop="platform"/>
      <el-table-column label="商品名" width="500px" show-overflow-tooltip>
        <template #default="scope">
          <el-link :href="scope.row.url" target="_blank" type="primary">{{scope.row.title}}</el-link>
        </template>
      </el-table-column>
      <el-table-column label="价格" prop="price" width="150px"/>
        <el-table-column label="店铺" width="300px" show-overflow-tooltip>
          <template #default="scope">
            <el-link :href="scope.row.shopUrl" target="_blank" type="danger">{{scope.row.shop}}</el-link>
          </template>
        </el-table-column>
    </el-table>
    </div>
  </div>
</template>
<script setup>
import {reactive} from "vue";
import {Delete, Edit} from "@element-plus/icons-vue";
import request_search from "@/utils/request_search.js";
import request from "@/utils/request.js";

const data=reactive({
  search_name: null,
  commercial_list:[],
  taobao_loading: false,
  jingdong_loading: false
})

const sort=()=>{
  data.commercial_list.sort((a,b)=>{
    let priceA = parseFloat(a.price.replaceAll('?', '0'));
    let priceB = parseFloat(b.price.replaceAll('?', '0'));
    return priceA - priceB;
  })
}

const search=()=>{
  if(data.search_name==null)return
  data.commercial_list = []
  data.taobao_loading = true
  data.jingdong_loading = true
  request_search.get('/searchInTaobao',{
    params:{
      keyword: data.search_name
    }}).then((res)=>{
      data.commercial_list=data.commercial_list.concat(res);
      request.post('/commercial/newUniqueCommercials',res)
      data.taobao_loading=false
      if(!data.jingdong_loading) sort()
  })
  request_search.get('/searchInJingdong',{
    params:{
      keyword: data.search_name
    }}).then((res)=>{
      data.commercial_list=data.commercial_list.concat(res);
      request.post('/commercial/newUniqueCommercials',res)
      data.jingdong_loading=false
      if(!data.taobao_loading) sort()
  })

}

const reset=()=>{
  data.search_name=null
  data.commercial_list=[]
}

</script>
<style scoped>

</style>