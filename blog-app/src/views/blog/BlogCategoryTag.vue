<template>
  <div class="me-ct-body" v-title :data-title="title">
    <el-container class="me-ct-container">
      <el-main>
        <div class="me-ct-title me-area">
          <template v-if="this.$route.params.type === 'tag'">
            <img class="me-ct-picture" :src="ct.avatar?ct.avatar:defaultAvatar"/>
            <h3 class="me-ct-name">{{ct.tagName}}</h3>
          </template>

          <template v-else>
            <img class="me-ct-picture" :src="ct.avatar?ct.avatar:defaultAvatar"/>
            <h3 class="me-ct-name">{{ct.categoryName}}</h3>
            <p>{{ct.description}}</p>
          </template>

          <span class="me-ct-meta">{{ct.articles}} 文章</span>
        </div>

        <div class="me-ct-articles">
          <article-scroll-page v-bind="article"></article-scroll-page>
        </div>

      </el-main>
    </el-container>
  </div>
</template>

<script>
  import ArticleScrollPage from '@/views/common/ArticleScrollPage'
  import {getArticlesByTag} from '@/api/article'
  import {getCategoryDetail} from '@/api/category'
  import defaultAvatar from '@/assets/img/4.png'


  export default {
    name: 'BlogCategoryTag',
    created() {
      this.getCategoryOrTagAndArticles()
    },
    watch: {
      '$route': 'getCategoryOrTagAndArticles'
    },
    data() {
      return {
        defaultAvatar: defaultAvatar,
        ct: {},
        article: {
          query: {
            tagId: '',
            categoryId: ''
          }
        },
      }
    },
    computed: {
      title() {
        if(this.$route.params.type === 'tag'){
          return `${this.ct.tagName} - 标签 - Warren`
        }
        return `${this.ct.categoryName} - 文章分类 - Warren`
      }
    },
    methods: {
      getCategoryOrTagAndArticles() {
        let id = this.$route.params.id
        let type = this.$route.params.type
        if ('tag' === type) {
          this.getTagDetail(id)
          this.article.query.tagId = id
        } else {
          this.getCategoryDetail(id)
          this.article.query.categoryId = id
        }

      },
      getCategoryDetail(id) {
        let that = this
        getCategoryDetail(id).then(data => {
          that.ct = data.data
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '文章分类加载失败', showClose: true})
          }
        })
      }
    },
    components: {
      ArticleScrollPage
    }
  }
</script>
<style scoped>
/* 新增：进入时淡入上移动画 */
@keyframes fadeUp {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 主体：增加进入动画，让页面更柔和地显现 */
.me-ct-body {
  margin: 60px auto 140px;
  min-width: 100%;
  animation: fadeUp 0.6s ease-out;
  transition: transform 0.3s ease;
}
.me-ct-body:hover {
  transform: translateY(-3px);
}

/* 主内容区域 */
.el-main {
  padding: 0;
}

/* 标题区域：悬停时轻微上浮 & 文字颜色变化 */
.me-ct-title {
  text-align: center;
  height: 150px;
  padding: 20px;
  transition: transform 0.3s ease, color 0.3s ease;
}
.me-ct-title:hover {
  transform: translateY(-3px);
  color: #409eff;
}

/* 头像：悬停放大并带阴影 */
.me-ct-picture {
  width: 60px;
  height: 60px;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}
.me-ct-picture:hover {
  transform: scale(1.1);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
}

/* 名称：悬停时颜色变亮 */
.me-ct-name {
  font-size: 28px;
  transition: color 0.3s ease;
}
.me-ct-name:hover {
  color: #409eff;
}

/* 元信息：悬停时颜色变深 */
.me-ct-meta {
  font-size: 12px;
  color: #969696;
  transition: color 0.3s ease;
}
.me-ct-meta:hover {
  color: #666;
}

/* 文章容器：悬停时浮动 */
.me-ct-articles {
  width: 640px;
  margin: 30px auto;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}
.me-ct-articles:hover {
  transform: translateY(-3px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}
</style>

