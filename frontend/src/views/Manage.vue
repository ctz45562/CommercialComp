<template>
  <div v-if="user.role === 'admin'">
    <!--  头部开始 -->
    <div style="height: 60px; background-color: #3c7fff; display: flex; align-items: center">
      <div style="width: 200px; display: flex; align-items: center; padding-left: 15px">
        <img style="width: 40px" src="@/assets/logo.svg" alt="">
        <span style="font-size: 22px; color: white; margin-left: 5px">后台管理系统</span>
      </div>
      <div style="flex: 1"></div>
      <div style="width: fit-content; display: flex; align-items: center; padding-right: 30px">
        <el-image src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" alt="" style="width: 40px; height: 40px"></el-image>
        <span style="color: white; margin-left: 10px">{{ user.name }}</span>
      </div>
    </div>
    <!--  头部结束 -->

    <!-- 下面部分开始 -->
    <div style="display: flex">
      <!-- 左侧导航菜单开始 -->
      <div style="width: 200px; border-right: 1px solid #ddd; min-height: calc(100vh - 60px)">
        <el-menu router :default-active="router.currentRoute.value.path" :default-openeds="['1']" style="border: 0">
<!--          <el-menu-item index="/manage/home">-->
<!--            <el-icon><House /></el-icon>-->
<!--            首  页-->
<!--          </el-menu-item>-->
          <el-menu-item index="/manage/commercial">
            <el-icon><Goods /></el-icon>
            商品管理
          </el-menu-item>
          <el-menu-item index="/manage/user">
            <el-icon><User /></el-icon>
            用户管理
          </el-menu-item>
          <el-menu-item index="/manage/info">
            <el-icon><Setting /></el-icon>
            个人信息
          </el-menu-item>
          <el-menu-item @click="logout">
            <el-icon><SwitchButton /></el-icon>
            退出登录
          </el-menu-item>
        </el-menu>
      </div>
      <!-- 左侧导航菜单结束 -->

      <!-- 右侧主体区域开始 -->
      <div style="flex: 1; width: 0; background-color: #f5f7ff; padding: 10px">
        <RouterView />
      </div>
      <!-- 右侧主体区域结束 -->
    </div>
    <!-- 下面部分结束 -->

  </div>
</template>

<script setup>
import {reactive} from "vue";
import router from "@/router/index.js";
import {Goods, Setting} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";

const user=JSON.parse(localStorage.getItem('current-user'))
console.log(user)
if(user == null){
  ElMessage.error('请登录')
  setTimeout(() => {
    location.href = '/login'
  }, 1500)
}
if(user.role !== 'admin'){
  ElMessage.error('您没有权限查看此页面')
  setTimeout(()=>{
    location.href = '/home/rand'
  }, 2000)
}

const logout = () => {
  localStorage.removeItem('current-user')
  location.href = '/login'
}

</script>

<style>
.el-menu .is-active {
  background-color: #e6ecf7 !important;
}
</style>

