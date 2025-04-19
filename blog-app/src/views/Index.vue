<template>
  <div v-title data-title="Warren">
    <el-container>

      <el-main class="me-articles">
        <article-scroll-page></article-scroll-page>
      </el-main>

      <el-aside>
        <card-me class="me-area"></card-me>
        <card-tag :tags="hotTags"></card-tag>
        <card-article cardHeader="最热文章" :articles="hotArticles"></card-article>
        <card-archive cardHeader="文章归档" :archives="archives"></card-archive>
        <card-article cardHeader="最新文章" :articles="newArticles"></card-article>
      </el-aside>

    </el-container>
  </div>
</template>

<script>
import CardMe from '@/components/card/CardMe'
import CardArticle from '@/components/card/CardArticle'
import CardArchive from '@/components/card/CardArchive'
import CardTag from '@/components/card/CardTag'
import ArticleScrollPage from '@/views/common/ArticleScrollPage'

import { getHotArtices, getNewArtices, listArchives } from '@/api/article'
import { getHotTags } from '@/api/tag'

export default {
  name: 'Index',
  created() {
    this.getHotArtices()
    this.getNewArtices()
    this.getHotTags()
    this.listArchives()
  },
  data() {
    return {
      hotTags: [],
      hotArticles: [],
      newArticles: [],
      archives: []
    }
  },
  methods: {
    // 获取最热文章
    getHotArtices() {
      getHotArtices()
        .then((data) => {
          this.hotArticles = data.data
        })
        .catch((error) => {
          if (error !== 'error') {
            this.$message({ type: 'error', message: '最热文章加载失败!', showClose: true })
          }
        })
    },
    // 获取最新文章
    getNewArtices() {
      getNewArtices()
        .then((data) => {
          this.newArticles = data.data
        })
        .catch((error) => {
          console.log(error)
          if (error !== 'error') {
            this.$message({ type: 'error', message: '最新文章加载失败!', showClose: true })
          }
        })
    },
    // 获取最热标签
    getHotTags() {
      getHotTags()
        .then((data) => {
          this.hotTags = data.data
        })
        .catch((error) => {
          if (error !== 'error') {
            this.$message({ type: 'error', message: '最热标签加载失败!', showClose: true })
          }
        })
    },
    // 获取文章归档
    listArchives() {
      listArchives()
        .then((data) => {
          this.archives = data.data
        })
        .catch((error) => {
          if (error !== 'error') {
            this.$message({ type: 'error', message: '文章归档加载失败!', showClose: true })
          }
        })
    }
  },
  components: {
    'card-me': CardMe,
    'card-article': CardArticle,
    'card-tag': CardTag,
    ArticleScrollPage,
    CardArchive
  }
}
</script>

<style scoped>
/* 容器布局：自适应宽度，居中对齐 */
.el-container {
  max-width: 1100px;
  width: 100%;
  margin: 0 auto;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}

/* 主要内容区域 */
.el-main {
  flex: 1;
  min-width: 640px;
  padding: 0;
  line-height: 1.8;
  transition: all 0.3s ease-in-out;
}

/* 侧边栏 */
.el-aside {
  width: 280px;
  margin-left: 24px;
  transition: transform 0.3s ease-in-out;
}

/* 侧边栏 hover 交互：微微上浮 */
.el-aside:hover {
  transform: translateY(-5px);
}

/* 统一卡片样式 */
.el-card {
  border-radius: 12px;
  box-shadow: 0 3px 8px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease-in-out;
  background: #fff;
  overflow: hidden;
}

/* 卡片 hover 交互：轻微上浮，阴影加深 */
.el-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.12);
}

/* 卡片点击交互：轻微缩小，增加动感 */
.el-card:active {
  transform: scale(0.98);
}

/* 卡片间距 */
.el-card:not(:first-child) {
  margin-top: 20px;
}

/* 文章列表 hover 时增加底部边框，突出交互感 */
.el-card .article-item {
  padding: 12px;
  transition: border-bottom 0.3s ease-in-out;
}

.el-card .article-item:hover {
  border-bottom: 2px solid #409eff;
}
</style>
