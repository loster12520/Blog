<template>
  <el-card class="me-author-card">
    <h1 class="me-author-name">Warren</h1>
    <div class="me-author-description">
      <span><i class="el-icon-location-outline"></i> &nbsp;江西&赣州</span>
      <span><i class="me-icon-job"></i> &nbsp;Java学习</span>
    </div>
    <div class="me-author-tool">
      <!-- QQ 还是使用弹窗提示的方式 -->
      <i
        @click="showTool(qq)"
        :title="qq.title"
        class="iconfont icon-qq"
      ></i>

      <!-- Bilibili 改为直接跳转 -->
      <i
        @click="goToBilibili"
        :title="bilibili.title"
        class="iconfont icon-bilibili-fill"
      ></i>
    </div>
  </el-card>
</template>

<script>
export default {
  name: 'CardMe',
  data() {
    return {
      qq: {
        title: 'QQ',
        message: '2246574790'
      },
      bilibili: {
        title: '哔哩哔哩',
        url: 'https://space.bilibili.com/503091992?spm_id_from=333.1007.0.0'
      }
    }
  },
  methods: {
    // QQ 弹窗提示
    showTool(tool) {
      this.$message({
        duration: 0,
        showClose: true,
        dangerouslyUseHTMLString: true,
        message: '<strong>' + tool.message + '</strong>'
      });
    },
    // 直接跳转到 B 站
    goToBilibili() {
      window.open(this.bilibili.url, '_blank');
    }
  }
}
</script>

<style scoped>
/* 卡片整体外观的优化 */
.me-author-card {
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.15);
  padding: 20px;
  transition: box-shadow 0.3s ease;
  background-color: #fff;
}
.me-author-card:hover {
  box-shadow: 0 4px 12px rgba(0,0,0,0.25);
}

/* 姓名的样式 */
.me-author-name {
  text-align: center;
  font-size: 30px;
  border-bottom: 1px solid #5FB878;
  margin-bottom: 10px;
  padding-bottom: 10px;
}

/* 描述信息容器 */
.me-author-description {
  padding: 8px 0;
  text-align: center;
}

/* 工具栏图标 */
.me-author-tool {
  text-align: center;
  padding-top: 10px;
}
.me-author-tool i {
  cursor: pointer;
  padding: 4px 10px;
  font-size: 30px;
  color: #999;
  transition: color 0.3s ease;
}
.me-author-tool i:hover {
  color: #409eff;
}
</style>
