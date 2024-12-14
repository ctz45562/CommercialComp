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
      <div v-if="data.searching" style="margin-top: 15px">
        <el-pagination
            @current-change="load"
            @size-change="load"
            v-model:current-page="data.pageNum"
            v-model:page-size="data.pageSize"
            background
            layout="prev, pager, next"
            :total="data.total"
        />
      </div>
      <el-table :data="data.commercial_list" stripe>
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
import { reactive } from "vue";
import request from "@/utils/request.js";


const data = reactive({
  commercial_list: [],
  search_name: null,
  searching: false,
  pageNum:1,
  pageSize:15,
  total:3
})
const sort=()=>{
  data.commercial_list.sort((a,b)=>{
    let priceA = parseFloat(a.price.replaceAll('?', '0'));
    let priceB = parseFloat(b.price.replaceAll('?', '0'));
    console.log(a.price + " => " + priceA);
    console.log(b.price + " => " + priceB);
    return priceA - priceB;
  })
}
const load = ()=>{
  if(data.searching) {
    request.get('/commercial/getPage', {
      params: {
        pageNum: data.pageNum,
        pageSize: data.pageSize,
        title: data.search_name
      }
    }).then(
        res => {
          data.commercial_list = res.data.list
          data.total = res.data.total
        }
    );
  }
  else {
    request.get('/commercial/getRandomCommercials').then(
        res=>{
          data.commercial_list=res.data
          sort()
        }
    )
  }
}

const search = ()=>{
  data.searching = data.search_name != null;
  load()
}

const reset = ()=>{
  data.search_name=null
  data.searching=false
  load()
}


load()
</script>

<style scoped>

</style>