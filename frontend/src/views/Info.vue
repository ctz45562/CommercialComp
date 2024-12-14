
<template>
  <div>
      <el-form label-width="auto" style="max-width: 800px" ref="formRef" :rules="data.rules" :model="data.form">
        <el-form-item prop="name" label="用户名">
          <el-input disabled size="large" v-model="data.form.name" :prefix-icon="User" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item prop="email" label="邮箱">
          <el-input size="large" v-model="data.form.email" :prefix-icon="Message" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item prop="oldPassword" label="旧密码">
          <el-input show-password size="large" v-model="data.form.oldPassword" :prefix-icon="Unlock" placeholder="请输入旧密码" />
        </el-form-item>
        <el-form-item prop="password" label="新密码">
          <el-input show-password size="large" v-model="data.form.password" :prefix-icon="Lock" placeholder="请输入新密码"/>
        </el-form-item>
        <el-form-item prop="confirmPassword" label="确认新密码">
          <el-input show-password size="large" v-model="data.form.confirmPassword" :prefix-icon="Lock" placeholder="请确认新密码"/>
        </el-form-item>
        <div style="text-align: center"><el-button style="margin-top:25px;width:100px;font-size:16px" size="large" type="primary" @click="update" >修改</el-button></div>

      </el-form>


  </div>
</template>

<script setup>
import {Lock, Message, Unlock, User} from "@element-plus/icons-vue";
import {reactive,ref} from "vue";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";


const validatePass = (rule, value, callback) => {
  if (value !== data.form.password) {
    callback(new Error("两次输入的密码不一致"))
  } else {
    callback()
  }
}

const data=reactive({
  user: JSON.parse(localStorage.getItem('current-user')),
  form:{},
  rules: {
    name: [
      { required: true, message: '请输入用户名', trigger: 'blur' }
    ],
    email: [
      { required: true, message: '请输入邮箱', trigger: 'blur' }
    ],
    oldPassword: [
      { required: true, message: '请输入旧密码', trigger: 'blur' }
    ],
    password: [
      { required: true, message: '请输入新密码', trigger: 'blur' }
    ],
    confirmPassword: [
      { required: true, trigger: "blur", message: "请再次输入您的密码" },
      { required: true, validator: validatePass, trigger: "blur" }
    ]
  }
})

data.form=JSON.parse(JSON.stringify(data.user))
data.form.password=null
data.form.oldPassword=null
data.form.confirmPassword=null

const formRef=ref()

const update=()=>{
  formRef.value.validate((valid) => {
    if (valid) {
      if(data.form.oldPassword !== data.user.password){
        console.log(data.user)
        ElMessage.error("旧密码错误")
        return
      }
      request.put('/user/updateUser', data.form).then(
          res => {
            if (res.code === 200) {
              localStorage.setItem('current-user', JSON.stringify(data.form))
              data.form.oldPassword=null
              data.user=JSON.parse(JSON.stringify(data.form))
              data.form.password=null
              data.form.confirmPassword=null
              ElMessage.success('修改成功')
            } else {
              ElMessage.error(res.message)
            }
          })
    }
  })
}
</script>

<style scoped>

</style>
