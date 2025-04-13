<template>
  <div v-title :data-title="title">
    <el-container>

      <el-aside class="me-area">
        <ul class="me-month-list">
          <li v-for="a in archives" :key="a.year + a.month" class="me-month-item">
            <el-badge :value="a.count">
              <el-button @click="changeArchive(a.year, a.month)" size="small">{{a.year +'年' + a.month + '月'}}
              </el-button>
            </el-badge>
          </li>
        </ul>

      </el-aside>


      <el-main class="me-articles">
        <div class="me-month-title">{{currentArchive}}</div>

        <article-scroll-page v-bind="article"></article-scroll-page>

      </el-main>
    </el-container>
  </div>

</template>

<script>
  import ArticleScrollPage from '@/views/common/ArticleScrollPage'
  import {listArchives} from '@/api/article'

  export default {
    name: "BlogArchive",
    components: {
      ArticleScrollPage
    },
    created() {
      this.listArchives()
    },
    watch: {
      '$route'() {
        if (this.$route.params.year && this.$route.params.month) {
          this.article.query.year = this.$route.params.year
          this.article.query.month = this.$route.params.month
        }
      }
    },
    data() {
      return {
        article: {
          query: {
            month: this.$route.params.month,
            year: this.$route.params.year
          }
        },
        archives: []
      }
    },
    computed: {
      title (){
        return this.currentArchive + ' - 文章归档 - Warren'
      },
      currentArchive (){
        if(this.article.query.year && this.article.query.month){
          return `${this.article.query.year}年${this.article.query.month}月`
        }
        return '全部'
      }
    },
    methods: {

      changeArchive(year, month) {
        // this.currentArchive = `${year}年${month}月`
        // this.article.query.year = year
        // this.article.query.month = month
        this.$router.push({path: `/archives/${year}/${month}`})
      },
      listArchives() {
        listArchives().then((data => {
          this.archives = data.data
        })).catch(error => {
          that.$message({type: 'error', message: '文章归档加载失败!', showClose: true})
        })
      }
    }
  }
</script>

<style scoped>
.el-container {
  width: 640px;
  transition: all 0.3s ease; /* 添加过渡效果 */
}

.el-aside {
  position: fixed;
  left: 200px;
  margin-right: 50px;
  width: 150px !important;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
  transition: all 0.3s ease; /* 添加过渡效果 */
}

.el-aside:hover {
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2); /* 鼠标悬停时增加阴影强度 */
  transform: translateY(-5px); /* 鼠标悬停时向上浮动 */
}

.el-main {
  padding: 0px;
  line-height: 16px;
  transition: all 0.3s ease; /* 添加过渡效果 */
}

.me-month-list {
  margin-top: 10px;
  margin-bottom: 10px;
  text-align: center;
  list-style-type: none;
  transition: all 0.3s ease; /* 添加过渡效果 */
}

.me-month-item {
  margin-top: 18px;
  padding: 4px;
  font-size: 18px;
  color: #5FB878;
  transition: all 0.3s ease; /* 添加过渡效果 */
}

.me-month-item:hover {
  background-color: #f0f0f0; /* 鼠标悬停时改变背景颜色 */
  transform: scale(1.05); /* 鼠标悬停时放大 */
}

.me-order-list {
  float: right;
  transition: all 0.3s ease; /* 添加过渡效果 */
}

.me-month-title {
  margin-left: 4px;
  margin-bottom: 12px;
  transition: all 0.3s ease; /* 添加过渡效果 */
}
</style>
