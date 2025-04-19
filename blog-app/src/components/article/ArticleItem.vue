<template>
  <!-- 增加一个自定义类 .me-article-card 方便在CSS中精细控制 -->
  <el-card class="me-area me-article-card" :body-style="{ padding: '16px' }">
    <a class="me-article-container" @click="view(id)">
      <!-- 新增的头像区域 -->
      <div class="me-article-avatar">
        <el-avatar :size="50" :src="pictureUrl" fit="cover"></el-avatar>
      </div>
      <!-- 分割线 -->
      <div class="me-article-divider"></div>
      <!-- 原有的文章内容区域 -->
      <div class="me-article-content">
        <div class="me-article-header">
          <div class="me-article-title">{{ title }}</div>
          <el-button v-if="weight > 0" class="me-article-icon" type="text">置顶</el-button>

          <span class="me-pull-right me-article-count">
            <i class="me-icon-comment"></i>&nbsp;{{ commentCounts }}
          </span>
          <span class="me-pull-right me-article-count">
            <i class="el-icon-view"></i>&nbsp;{{ viewCounts }}
          </span>
        </div>

        <div class="me-article-description">
          {{ summary }}
        </div>
        <div class="me-article-footer">
          <span class="me-article-author">
            <i class="me-icon-author"></i>&nbsp;{{ author.nickname }}
          </span>

          <el-tag
            v-for="t in tags"
            :key="t.tagName"
            size="mini"
            type="success"
          >
            {{ t.tagName }}
          </el-tag>

          <span class="me-pull-right me-article-count">
            <i class="el-icon-time"></i>&nbsp;{{ createDate | format }}
          </span>
        </div>
      </div>
    </a>
  </el-card>
</template>

<script>
import {formatTime} from "../../utils/time";

export default {
  name: 'ArticleItem',
  props: {
    id: String,
    weight: Number,
    title: String,
    commentCounts: Number,
    viewCounts: Number,
    summary: String,
    author: Object,
    tags: Array,
    createDate: String,
    pictureUrl: String,
  },
  methods: {
    view(id) {
      this.$router.push({path: `/view/${id}`});
    }
  }
};
</script>

<style scoped>
/* ====== 文章卡片结构 ====== */
.me-article-header {
  padding-bottom: 10px;
}

.me-article-title {
  font-weight: 600;
  font-size: 16px;
  /* 鼠标变成手型，提示可点击 */
  cursor: pointer;
  /* 加过渡，让悬停时颜色变化更平滑 */
  transition: color 0.3s ease;
}

/* 标题悬停时颜色变化，或加下划线 */
.me-article-title:hover {
  color: #409eff;
  text-decoration: underline;
}

.me-article-icon {
  padding: 3px 8px;
}

.me-article-count {
  color: #a6a6a6;
  padding-left: 14px;
  font-size: 13px;
}

.me-pull-right {
  float: right;
}

.me-artile-description {
  font-size: 13px;
  line-height: 24px;
  margin-bottom: 10px;
}

.me-article-author {
  color: #a6a6a6;
  padding-right: 18px;
  font-size: 13px;
}

.el-tag {
  margin-left: 6px;
}

/* ====== 卡片整体的浮动交互效果 ====== */
.me-article-card {
  /* 给卡片添加平滑的 transform/box-shadow 过渡 */
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

/* 鼠标悬停时，卡片微微上浮并加深阴影 */
.me-article-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
}

/* 鼠标按下时，卡片略微缩放，增加动感 */
.me-article-card:active {
  transform: scale(0.98);
}

.me-area {
  margin-bottom: 20px;
  border-radius: 8px;
  overflow: hidden;
}

.me-article-card {
  border: none;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.me-article-container {
  display: flex;
}

.me-article-avatar {
  width: 80px;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0 16px;
}

.me-article-divider {
  width: 1px;
  background-color: #ebeef5;
  margin: 0 16px;
}

.me-article-content {
  flex: 1;
}

.me-article-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 10px;
}

.me-article-title {
  font-size: 18px;
  font-weight: bold;
  color: #333;
  text-decoration: none;
  cursor: pointer;
}

.me-article-title:hover {
  color: #409eff;
}

.me-article-icon {
  margin-left: 10px;
  color: #409eff;
}

.me-article-count {
  margin-left: 15px;
  color: #999;
  font-size: 14px;
}

.me-article-description {
  color: #666;
  margin-bottom: 10px;
  line-height: 1.5;
}

.me-article-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  color: #999;
  font-size: 14px;
}

.me-article-author {
  display: flex;
  align-items: center;
}

.el-tag {
  margin-right: 10px;
}

.me-pull-right {
  float: right;
}
</style>
