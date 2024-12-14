<template>
  <div>
    <!--  头部开始 -->
    <div style="height: 60px; background-color: #3c7fff; display: flex; align-items: center">
      <el-button type="primary" style="margin-left:10px;width:45px;height:45px" @click="toggleMenu" size="large">
        <el-icon size="25"><Operation/></el-icon>
      </el-button>
      <div style="width: 200px; display: flex; align-items: center; padding-left: 15px">
        <img style="width: 40px" src="@/assets/logo.svg" alt="">
        <span style="font-size: 20px; color: white; margin-left: 5px">商品比价平台</span>
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
        <el-aside v-show="data.menuVisible" width="100%" height="auto">
        <el-menu router :default-active="router.currentRoute.value.path" :default-openeds="['1']" style="border: 0;box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);">
          <el-menu-item index="/mobile/rand">
            <el-icon><ShoppingCart/></el-icon>
            随便逛逛
          </el-menu-item>
          <el-menu-item index="/mobile/search">
            <el-icon><Search /></el-icon>
            搜 索
          </el-menu-item>
          <el-menu-item index="/mobile/info">
            <el-icon><Setting /></el-icon>
            个人信息
          </el-menu-item>
          <el-menu-item @click="logout">
            <el-icon><SwitchButton /></el-icon>
            退出登录
          </el-menu-item>
        </el-menu>
        </el-aside>
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
import {Goods, Setting, SwitchButton, Search, ShoppingCart, Menu, Operation} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";

const user=JSON.parse(localStorage.getItem('current-user'))
if(user == null){
  ElMessage.error('请登录')
  setTimeout(() => {
    location.href = '/login'
  }, 2000)
}
const logout = () => {
  localStorage.removeItem('current-user')
  location.href = '/login'
}
const data = reactive({
  menuWidth: 0,
  menuVisible: false
})
const toggleMenu = ()=>{
  if(data.menuVisible) data.menuVisible=false;
  else data.menuVisible=true;
}
</script>

<style>
.el-menu .is-active {
  background-color: #e6ecf7 !important;
}

</style>

