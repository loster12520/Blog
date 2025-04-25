<template>
  <div id="register" v-title data-title="注册 - Warren">
    <!--<video preload="auto" class="me-video-player" autoplay="autoplay" loop="loop">
          <source src="../../static/vedio/sea.mp4" type="video/mp4">
      </video>-->

    <div class="me-login-box me-login-box-radius">
      <h1>Warren 注册</h1>

      <el-form ref="userForm" :model="userForm" :rules="rules">
        <el-form-item prop="account">
          <el-input placeholder="用户名" v-model="userForm.account"></el-input>
        </el-form-item>

        <el-form-item prop="nickname">
          <el-input placeholder="昵称" v-model="userForm.nickname"></el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input placeholder="密码" type="password" v-model="userForm.password"></el-input>
        </el-form-item>

        <el-form-item size="small" class="me-login-button">
          <el-button type="primary" @click.native.prevent="register('userForm')">注册</el-button>
        </el-form-item>
      </el-form>



    </div>
  </div>
</template>

<script>
  import {register} from '@/api/login'

  export default {
    name: 'Register',
    data() {
      return {
        userForm: {
          account: '',
          nickname: '',
          password: ''
        },
        rules: {
          account: [
            {required: true, message: '请输入用户名', trigger: 'blur'},
            {max: 10, message: '不能大于10个字符', trigger: 'blur'}
          ],
          nickname: [
            {required: true, message: '请输入昵称', trigger: 'blur'},
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
      register(formName) {
        let that = this
        this.$refs[formName].validate((valid) => {
          if (valid) {

            that.$store.dispatch('register', that.userForm).then(() => {
              that.$message({message: '注册成功 快写文章吧', type: 'success', showClose: true});
              that.$router.push({path: '/'})
            }).catch((error) => {
              if (error !== 'error') {
                that.$message({message: error, type: 'error', showClose: true});
              }
            })

          } else {
            return false;
          }
        });

      }

    }
  }
</script>
<style scoped>
/* 整体背景：浅色渐变 */
#register {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #f4f5f780, #ffffff80);
  animation: fadeIn 1s ease-in-out;
  overflow: hidden;
}

/* 背景花纹点缀（低调，不高调） */
#register::before {
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

/* 视频背景保持透明，并铺满整个屏幕 */
.me-video-player {
  background-color: transparent;
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
  position: absolute;
  left: 0;
  top: 0;
  z-index: 0;
}

/* 登录框：采用玻璃拟态风格，增加圆角、阴影及悬停时浮动效果 */
.me-login-box {
  position: absolute;
  width: 300px;
  height: 320px;
  background-color: rgba(255, 255, 255, 0.85);
  left: 50%;
  margin-top: 150px;
  margin-left: -150px;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  z-index: 1;
}
.me-login-box:hover {
  transform: translateY(-5px);
  box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.15);
}

/* 标题：居中显示，字体适中，并在悬停时变色 */
.me-login-box h1 {
  text-align: center;
  font-size: 24px;
  margin-bottom: 20px;
  vertical-align: middle;
  color: #444;
  transition: color 0.3s ease;
}
.me-login-box h1:hover {
  color: #409eff;
}

/* 设计说明部分 */
.me-login-design {
  text-align: center;
  font-family: 'Open Sans', sans-serif;
  font-size: 18px;
  transition: color 0.3s ease;
}
.me-login-design-color {
  color: #5FB878 !important;
}

/* 按钮区域：居中并增加间距 */
.me-login-button {
  text-align: center;
  margin-top: 20px;
}
.me-login-button button {
  width: 100%;
  padding: 10px;
  border-radius: 20px;
  background-color: #409eff;
  border: none;
  color: #fff;
  font-weight: bold;
  transition: transform 0.2s ease, background-color 0.3s ease;
}
.me-login-button button:hover {
  background-color: #337ecc;
  transform: translateY(-2px);
}
.me-login-button button:active {
  transform: scale(0.96);
}
</style>
