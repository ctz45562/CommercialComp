
<template>
  <div class="login-page">
    <div class="login-container">
      <div style="margin-bottom:30px;text-align: center;font-size: 30px; color: #075591; font-weight: bold">注 册</div>
      <el-form ref="formRef" :rules="data.rules" :model="data.form">
        <el-form-item prop="name">
          <el-input size="large" v-model="data.form.name" :prefix-icon="User" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item prop="email">
          <el-input size="large" v-model="data.form.email" :prefix-icon="Message" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input show-password size="large" v-model="data.form.password" :prefix-icon="Lock" placeholder="请输入密码"/>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input show-password size="large" v-model="data.form.confirmPassword" :prefix-icon="Lock" placeholder="请确认密码"/>
        </el-form-item>
      </el-form>

      <el-button style="width:100%;font-size:16px" size="large" type="primary" @click="register">注册</el-button>
      <div style="text-align: right;margin-top: 20px">已有账号？<a style="color: #0742b1; text-decoration: none" href="/login">点此登录</a></div>
    </div>
  </div>
</template>

<script setup>
import {Lock, Message, User} from "@element-plus/icons-vue";
import {reactive,ref} from "vue";
import request from "@/utils/request.js";


const validatePass = (rule, value, callback) => {
  if (value !== data.form.password) {
    callback(new Error("两次输入的密码不一致"))
  } else {
    callback()
  }
}

const data=reactive({
  form:{},
  rules: {
    name: [
      { required: true, message: '请输入用户名', trigger: 'blur' }
    ],
    email: [
      { required: true, message: '请输入邮箱', trigger: 'blur' }
    ],
    password: [
      { required: true, message: '请输入密码', trigger: 'blur' }
    ],
    confirmPassword: [
      { required: true, trigger: "blur", message: "请再次输入您的密码" },
      { required: true, validator: validatePass, trigger: "blur" }
    ]
  }
})


const formRef=ref()

const register=()=>{
  formRef.value.validate((valid) => {
    if (valid) {
      data.form.role='user'
      request.post('/user/newUser', data.form).then(
          res => {
            if (res.code === 200) {  // 登录成功
              // 存储后台返回的用户数据信息
              localStorage.setItem('current-user', JSON.stringify(res.data))  // 把json对象转换成 json字符串存储
              ElMessage.success('注册成功')
              setTimeout(() => {
                location.href = '/login'
              }, 500)
            } else {
              ElMessage.error(res.message)
            }
          })
    }
  })
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
