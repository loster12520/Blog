<template>
  <!-- 增加一个自定义类 .me-article-card 方便在CSS中精细控制 -->
  <el-card class="me-area me-article-card" :body-style="{ padding: '16px' }">
    <div class="me-article-header">
      <a @click="view(id)" class="me-article-title">{{ title }}</a>
      <el-button v-if="weight > 0" class="me-article-icon" type="text">置顶</el-button>

      <span class="me-pull-right me-article-count">
        <i class="me-icon-comment"></i>&nbsp;{{ commentCounts }}
      </span>
      <span class="me-pull-right me-article-count">
        <i class="el-icon-view"></i>&nbsp;{{ viewCounts }}
      </span>
    </div>

    <div class="me-artile-description">
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
  </el-card>
</template>

<script>
import { formatTime } from "../../utils/time";

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
    createDate: String
  },
  methods: {
    view(id) {
      this.$router.push({ path: `/view/${id}` });
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
</style>
