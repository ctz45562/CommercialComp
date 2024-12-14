
<template>
  <div class="login-page">
    <div class="login-container">
      <div style="margin-bottom:30px;text-align: center;font-size: 30px; color: #075591; font-weight: bold">欢迎登录电商比价平台</div>
      <el-form ref="formRef" :rules="data.rules" :model="data.form">
        <el-form-item prop="name">
          <el-input size="large" v-model="data.form.name" :prefix-icon="User" placeholder="请输入用户名" autocomplete="off" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input show-password size="large" v-model="data.form.password" :prefix-icon="Lock" placeholder="请输入密码" autocomplete="off" />
        </el-form-item>
      </el-form>
      <el-button style="width:100%;font-size:16px" size="large" type="primary" @click="login">登录</el-button>
      <div style="text-align: right;margin-top: 20px">还没有账号？请去<a style="color: #0742b1; text-decoration: none" href="/register">注册</a></div>
    </div>
  </div>
</template>
<script setup>
import {Lock, User} from "@element-plus/icons-vue";
import {reactive, ref} from "vue";
import request from "@/utils/request.js";


const formRef=ref()
const data=reactive({
  form:{},
  rules: {
    name: [
      { required: true, message: '请输入用户名', trigger: 'blur' }
    ],
    password: [
      { required: true, message: '请输入密码', trigger: 'blur' }
    ]
  }
})

const login=()=>{
  formRef.value.validate((valid) => {
    if (valid) {
      request.post('/user/login', data.form).then(
        res => {
        if (res.code === 200) {  // 登录成功
          // 存储后台返回的用户数据信息
          localStorage.setItem('current-user', JSON.stringify(res.data))  // 把json对象转换成 json字符串存储
          ElMessage.success('登录成功')
          setTimeout(() => {
            if(res.data.role=='admin')location.href = '/manage/commercial'
            else if(res.data.role=='user'){
              if(isMobile()) location.href='/mobile/rand'
              else location.href = '/home/rand'
            }
          }, 500)
        } else {
          ElMessage.error(res.message)
        }
      })
    }
  })
}

const isMobile=()=>{
  const userAgent = navigator.userAgent;
  return /Android|iPhone|iPad|iPod|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone/i.test(userAgent)
}
</script>

<style scoped>
.login-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  width: 100vw;
  background-size: cover !important;
  overflow: hidden;
  background: url('../assets/login-background.jpg') no-repeat center ;
}

.login-container {
  background: rgba(255, 255, 255, 0.9); /* 调整透明度 */
  padding: 40px; /* 调整内边距 */
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  width: 500px; /* 设置容器宽度 */
}

</style>
