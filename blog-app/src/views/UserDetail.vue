<template>
  <div class="user-detail-container">
    <el-card class="user-card">
      <div class="user-info">
        <!-- 头像区域 -->
        <div class="avatar-container">
          <el-avatar
            :size="100"
            :src="userForm.avatar"
            fit="cover"
            class="avatar"
          ></el-avatar>
          <div class="avatar-upload" v-if="isEditing">
            <el-upload
              class="avatar-uploader"
              :action="uploadAction"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
              @error="handleUploadError"
            >
              <el-button type="primary" plain>更换头像</el-button>
            </el-upload>
          </div>
        </div>
        <!-- 分割线 -->
        <div class="divider"></div>
        <!-- 用户信息区域 -->
        <div class="info-container">
          <el-form
            ref="userForm"
            :model="userForm"
            label-width="120px"
            :rules="rules"
            v-if="!isEditing"
            class="info-form-display"
          >
            <el-form-item label="账号">
              <div class="info-display">{{ userForm.account }}</div>
            </el-form-item>
            <el-form-item label="昵称">
              <div class="info-display">{{ userForm.nickname }}</div>
            </el-form-item>
            <el-form-item label="手机号">
              <div class="info-display">{{ userForm.mobilePhoneNumber }}</div>
            </el-form-item>
            <el-form-item label="邮箱">
              <div class="info-display">{{ userForm.email }}</div>
            </el-form-item>
            <el-form-item label="创建时间">
              <div class="info-display">{{ userForm.createDate }}</div>
            </el-form-item>
            <el-form-item label="最后登录时间">
              <div class="info-display">{{ userForm.lastLogin }}</div>
            </el-form-item>
            <el-form-item>
              <div style="text-align: right;">
                <el-button
                  type="primary"
                  @click="handleEdit"
                >
                  编辑
                </el-button>
              </div>
            </el-form-item>
          </el-form>

          <el-form
            ref="userFormEdit"
            :model="userForm"
            label-width="120px"
            :rules="rules"
            v-else
            class="info-form-edit"
          >
            <el-form-item label="账号" prop="account">
              <el-input
                v-model="userForm.account"
                disabled
                class="info-input"
              ></el-input>
            </el-form-item>
            <el-form-item label="昵称" prop="nickname">
              <el-input
                v-model="userForm.nickname"
                class="info-input"
              ></el-input>
            </el-form-item>
            <el-form-item label="手机号" prop="mobilePhoneNumber">
              <el-input
                v-model="userForm.mobilePhoneNumber"
                class="info-input"
              ></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input
                v-model="userForm.email"
                class="info-input"
              ></el-input>
            </el-form-item>
            <el-form-item>
              <div style="text-align: right;">
                <el-button
                  @click="cancelEdit"
                >
                  取消
                </el-button>
                <el-button
                  type="primary"
                  @click="submitForm"
                >
                  保存
                </el-button>
              </div>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import {getUserInfo, setUserInfo} from '@/api/login';

export default {
  data() {
    return {
      isEditing: false,
      userForm: {
        id: null,
        account: '',
        avatar: '',
        mobilePhoneNumber: '',
        createDate: '',
        lastLogin: '',
        email: '',
        nickname: ''
      },
      rules: {
        nickname: [
          {required: true, message: '请输入昵称', trigger: 'blur'}
        ],
        mobilePhoneNumber: [
          {required: true, message: '请输入手机号', trigger: 'blur'}
        ],
        email: [
          {required: true, message: '请输入邮箱', trigger: 'blur'},
          {type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur'}
        ]
      },
      uploadAction: '/user/upload' // 替换为你的上传接口地址
    };
  },
  methods: {
    // 获取用户详细信息（需自行实现后端交互）
    async getUserInfo() {
      // 模拟获取用户数据
      this.userForm = (await getUserInfo()).data
      console.log(this.userForm)
    },
    // 编辑用户信息
    handleEdit() {
      this.isEditing = true;
    },
    // 取消编辑
    cancelEdit() {
      this.isEditing = false;
      this.getUserInfo(); // 重新获取用户信息以确保数据是最新的
    },
    // 提交表单
    submitForm() {
      this.$refs.userFormEdit.validate(valid => {
        if (valid) {
          // 提交到后端（需自行实现后端交互）
          console.log('提交的用户信息：', this.userForm);
          setUserInfo(this.userForm)
          this.isEditing = false;
          this.$message.success('保存成功');
        } else {
          this.$message.error('表单验证失败');
          return false;
        }
      });
    },
    // 头像上传成功
    handleAvatarSuccess(res, file) {
      // 假设后端返回的头像 URL 在 res.data 中
      this.userForm.avatar = res.data || URL.createObjectURL(file.raw);
      console.log('头像上传成功:', res);
    },
    // 头像上传失败
    handleUploadError(err) {
      console.error('头像上传失败:', err);
      this.$message.error('头像上传失败，请重试');
    },
    // 头像上传前的校验
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 或 PNG 格式!');
        return false;
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
        return false;
      }
      return true;
    }
  },
  mounted() {
    this.getUserInfo();
  }
};
</script>

<style scoped>
.user-detail-container {
  width: 100%;
  padding: 20px;
  box-sizing: border-box;
}

.user-card {
  width: 80%;
  margin: 0 auto;
  background-color: rgba(255, 255, 255, 0.8);
  border-radius: 10px;
}

.user-info {
  display: flex;
  padding: 20px;
}

.avatar-container {
  width: 30%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
}

.avatar-upload {
  margin-top: 10px;
}

.divider {
  width: 1px;
  height: 300px;
  background-color: #dcdfe6;
  margin: 0 30px;
}

.info-container {
  flex: 1;
}

.info-form-display .el-form-item,
.info-form-edit .el-form-item {
  margin-bottom: 20px;
}

.info-display {
  padding: 8px 12px;
  background-color: #f5f7fa;
  border-radius: 5px;
  display: inline-block;
  width: 100%;
  box-sizing: border-box;
}

.info-input {
  width: 100%;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 150px;
  height: 40px;
  text-align: center;
  line-height: 40px;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar {
  width: 150px;
  height: 150px;
  display: block;
}

/* 确保标签和内容垂直居中对齐 */
.el-form-item {
  display: flex;
  align-items: center;
}

.el-form-item__label {
  display: flex;
  align-items: center;
}

.avatar{
  text-align: center;
}
</style>
