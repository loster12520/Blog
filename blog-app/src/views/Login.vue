<template>
  <div id="login" v-title data-title="登录 - Warren">
    <div class="me-login-container">
      <div class="me-login-box">
        <h1>Warren博客登录</h1>
        <el-form ref="userForm" :model="userForm" :rules="rules">
          <el-form-item prop="account">
            <el-input placeholder="用户名" v-model="userForm.account" prefix-icon="el-icon-user"></el-input>
          </el-form-item>

          <el-form-item prop="password">
            <el-input placeholder="密码" type="password" v-model="userForm.password"
                      prefix-icon="el-icon-lock"></el-input>
          </el-form-item>

          <el-form-item class="me-login-button">
            <el-button type="primary" @click.native.prevent="login('userForm')">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import {login} from "@/api/login"
import {getToken} from '@/request/token'

export default {
  name: 'Login',
  data() {
    return {
      userForm: {
        account: '',
        password: ''
      },
      rules: {
        account: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {max: 10, message: '不能大于10个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {max: 10, message: '不能大于10个字符', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    login(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          login(this.userForm.account, this.userForm.password).then(() => {
            this.$router.go(-1)
          }).catch(error => {
            if (error !== 'error') {
              this.$message({message: error, type: 'error', showClose: true});
            }
          })
        }
      });
    }
  }
}
</script>

<style scoped>
/* 整体背景：浅色渐变 */
#login {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #f4f5f7, #ffffff);
  animation: fadeIn 1s ease-in-out;
  overflow: hidden;
}

/* 背景花纹点缀（低调，不高调） */
#login::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: url("data:image/svg+xml,%3Csvg width='20' height='20' viewBox='0 0 20 20' xmlns='http://www.w3.org/2000/svg'%3E%3Ccircle cx='10' cy='10' r='1' fill='%23d0d0d0' /%3E%3C/svg%3E") repeat;
  opacity: 0.08;
  pointer-events: none;
}

/* 登录容器 */
.me-login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  position: relative;
  z-index: 1;
}

/* 登录框：玻璃拟态效果 + 轻微浮动 */
.me-login-box {
  width: 350px;
  padding: 40px;
  background: rgba(255, 255, 255, 0.7);
  backdrop-filter: blur(12px);
  border-radius: 12px;
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.08);
  text-align: center;
  color: #333;
  transition: all 0.3s ease-in-out, transform 0.3s ease-in-out;
  animation: floatUp 0.8s ease-in-out;
}

/* 登录框 hover 轻微浮动 */
.me-login-box:hover {
  transform: translateY(-5px);
  box-shadow: 0px 6px 16px rgba(0, 0, 0, 0.12);
}

/* 标题 */
.me-login-box h1 {
  font-size: 24px;
  margin-bottom: 20px;
  font-weight: bold;
  color: #444;
  opacity: 0;
  animation: fadeIn 1s ease-in-out forwards 0.5s;
}

/* 输入框 */
.el-input input {
  background: rgba(0, 0, 0, 0.05);
  border: none;
  color: #333;
  border-radius: 8px;
  padding: 10px;
  transition: all 0.3s ease-in-out, transform 0.2s ease-in-out;
}

/* 输入框聚焦时：边框发光 + 轻微放大 */
.el-input input:focus {
  outline: none;
  border: 1px solid #409eff;
  box-shadow: 0 0 8px rgba(64, 158, 255, 0.4);
  transform: scale(1.02);
}

/* 输入框 placeholder 颜色 */
.el-input input::placeholder {
  color: rgba(0, 0, 0, 0.4);
}

/* 按钮容器 */
.me-login-button {
  margin-top: 20px;
}

/* 登录按钮 */
.me-login-button .el-button {
  width: 100%;
  border-radius: 20px;
  background: #409eff;
  border: none;
  color: white;
  font-weight: bold;
  transition: all 0.3s ease-in-out, transform 0.2s ease-in-out;
}

/* 按钮 hover 交互：上浮 */
.me-login-button .el-button:hover {
  background: #337ecc;
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(64, 158, 255, 0.3);
}

/* 按钮点击交互：轻微缩小 */
.me-login-button .el-button:active {
  transform: scale(0.96);
}

/* 进入时的浮动动画 */
@keyframes floatUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 淡入动画 */
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
</style>



