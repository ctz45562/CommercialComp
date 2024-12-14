

<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input style="width: 100%; margin-right: 10px" v-model="data.search_name" placeholder="在商品库中查询" prefix-icon="Search"></el-input>

    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-button type="primary" @click="search">查 询</el-button>
      <el-button type="warning" @click="reset">重 置</el-button>
      <el-button type="info" @click="load">刷 新</el-button>
    </div>

    <div class="card">
      <el-table v-loading="data.taobao_loading || data.jingdong_loading" :data="data.commercial_list" stripe>
        <el-table-column width="80px">
          <template #default="scope">
            <el-link :href="scope.row.url" target="_blank">
              <el-image :src="scope.row.imgUrl" alt="" style="width:60px" loading="lazy"/>
            </el-link>
          </template>
        </el-table-column>
        <el-table-column label="平台" width="70px" prop="platform"/>
        <el-table-column label="商品名" width="150px" show-overflow-tooltip>
          <template #default="scope">
            <el-link :href="scope.row.url" target="_blank" type="primary">{{scope.row.title}}</el-link>
          </template>
        </el-table-column>
        <el-table-column label="价格" prop="price" width="80px"/>
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