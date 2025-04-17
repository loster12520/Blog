<template>
  <div class="me-view-body" v-title :data-title="title">
    <el-container class="me-view-container">
      <el-main>
        <div class="me-view-card">
          <h1 class="me-view-title">{{ article.title }}</h1>
          <div class="me-view-author">
            <a>
              <img class="me-view-picture" :src="article.author.avatar" />
            </a>
            <div class="me-view-info">
              <span>{{ article.author.nickname }}</span>
              <div class="me-view-meta">
                <span>{{ article.createDate | format }}</span>
                <span>阅读 {{ article.viewCounts }}</span>
                <span>评论 {{ article.commentCounts }}</span>
              </div>
            </div>
            <!-- 编辑按钮 -->
            <el-button
              v-if="article.author.id == $store.state.id"
              class="me-action-button"
              @click="editArticle()"
              style="position: absolute; left: 60%;"
              size="mini"
              round
              icon="el-icon-edit"
            >
              编辑
            </el-button>
            <!-- 删除按钮 -->
            <el-button
              v-if="article.author.id == $store.state.id"
              class="me-action-button"
              @click="deleteArticle()"
              style="position: absolute; left: 70%;"
              size="mini"
              round
              icon="el-icon-delete"
            >
              删除
            </el-button>
          </div>

          <div class="me-view-content">
            <!-- 文章正文，通过 mavon-editor 渲染（只读） -->
            <markdown-editor :editor="article.editor"></markdown-editor>
          </div>

          <div class="me-view-end">
            <el-alert
              title="文章End..."
              type="success"
              center
              :closable="false"
            />
          </div>

          <!-- 标签区域：带有悬停浮动效果 -->
          <div class="me-view-tag">
            标签：
            <el-button
              v-for="t in article.tags"
              :key="t.id"
              size="mini"
              type="primary"
              round
              plain
              @click="tagOrCategory('tag', t.id)"
            >
              {{ t.tagName }}
            </el-button>
          </div>

          <!-- 分类区域：带有悬停浮动效果 -->
          <div class="me-view-tag">
            文章分类：
            <el-button
              size="mini"
              type="primary"
              round
              plain
              @click="tagOrCategory('category', article.category.id)"
            >
              {{ article.category.categoryName }}
            </el-button>
          </div>

          <!-- 评论区域 -->
          <div class="me-view-comment">
            <!-- 评论输入框 -->
            <div class="me-view-comment-write">
              <el-row :gutter="20">
                <el-col :span="2">
                  <a>
                    <img class="me-view-picture" :src="avatar" />
                  </a>
                </el-col>
                <el-col :span="22">
                  <el-input
                    type="textarea"
                    :autosize="{ minRows: 2 }"
                    placeholder="你的评论..."
                    class="me-view-comment-text"
                    v-model="comment.content"
                    resize="none"
                  ></el-input>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :span="2" :offset="22">
                  <!-- 评论按钮 -->
                  <el-button
                    class="me-comment-button"
                    type="text"
                    @click="publishComment()"
                  >
                    评论
                  </el-button>
                </el-col>
              </el-row>
            </div>

            <!-- 评论标题 -->
            <div class="me-view-comment-title">
              <span>{{ article.commentCounts }} 条评论</span>
            </div>
            <!-- 评论列表，使用 transition-group 实现过渡效果 -->
            <div ref="commentSection">
              <transition-group name="list" tag="div">
                <CommmentItem
                  v-for="(c, index) in comments"
                  :key="c.id"
                  :comment="c"
                  :articleId="article.id"
                  :articleAuthorId="article.author.id"
                  :currentUserId="$store.state.id"
                  :index="index"
                  :rootCommentCounts="comments.length"
                  @remove-comment="handleRemoveComment"
                  @commentCountsIncrement="commentCountsIncrement"
                />
              </transition-group>
            </div>
          </div>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import MarkdownEditor from '@/components/markdown/MarkdownEditor'
import CommmentItem from '@/components/comment/CommentItem'
import { viewArticle, removeArticlesBatch } from '@/api/article'
import { getCommentsByArticle, publishComment } from '@/api/comment'
import default_avatar from '@/assets/img/3.png'

export default {
  name: 'BlogView',
  created() {
    this.getArticle()
  },
  watch: {
    '$route': 'getArticle'
  },
  data() {
    return {
      article: {
        id: '',
        title: '',
        commentCounts: 0,
        viewCounts: 0,
        summary: '',
        author: {},
        tags: [],
        category: {},
        createDate: '',
        editor: {
          value: '',
          toolbarsFlag: false,
          subfield: false,
          defaultOpen: 'preview'
        }
      },
      comments: [],
      comment: {
        article: {},
        content: ''
      }
    }
  },
  computed: {
    avatar() {
      const avatar = this.$store.state.avatar
      return avatar && avatar.length > 0 ? avatar : default_avatar
    },
    title() {
      return `${this.article.title} - 文章 - Warren`
    }
  },
  methods: {
    tagOrCategory(type, id) {
      this.$router.push({ path: `/${type}/${id}` })
    },
    editArticle() {
      this.$router.push({ path: `/write/${this.article.id}` })
    },
    getArticle() {
      viewArticle(this.$route.params.id)
        .then((data) => {
          Object.assign(this.article, data.data)
          this.article.editor.value = data.data.body.content
          this.getCommentsByArticle()
        })
        .catch((error) => {
          if (error !== 'error') {
            this.$message({ type: 'error', message: '文章加载失败', showClose: true })
          }
        })
    },
    publishComment() {
      if (!this.comment.content) return
      this.comment.article.id = this.article.id
      const parms = { articleId: this.article.id, content: this.comment.content }
      publishComment(parms, this.$store.state.token)
        .then((data) => {
          if (data.success) {
            this.$message({ type: 'success', message: '评论成功', showClose: true })
            this.comment.content = ''
            this.getCommentsByArticle()
          } else {
            this.$message({ type: 'error', message: data.msg, showClose: true })
          }
        })
        .catch((error) => {
          if (error !== 'error') {
            this.$message({ type: 'error', message: '评论失败', showClose: true })
          }
        })
    },
    getCommentsByArticle() {
      getCommentsByArticle(this.article.id)
        .then((data) => {
          if (data.success) {
            this.comments = data.data
            this.article.commentCounts = data.data.length
          } else {
            this.$message({ type: 'error', message: '评论加载失败', showClose: true })
          }
        })
        .catch((error) => {
          if (error !== 'error') {
            this.$message({ type: 'error', message: '评论加载失败', showClose: true })
          }
        })
    },
    commentCountsIncrement() {
      this.getCommentsByArticle()
    },
    handleRemoveComment(commentId) {
      this.comments = this.comments.filter(item => item.id !== commentId)
      this.article.commentCounts = this.comments.length
    },
    deleteArticle() {
      this.$confirm('确定要删除这篇文章吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          const token = this.$store.state.token
          removeArticlesBatch([this.article.id], token)
            .then(res => {
              if (res.data === '删除成功!') {
                this.$message.success('删除成功')
                this.$router.push('/')
              } else {
                this.$message.error('删除失败: ' + res.data)
              }
            })
            .catch(err => {
              console.error('删除请求出错', err)
              this.$message.error('请求出错')
            })
        })
        .catch(() => {
          console.log('取消删除')
        })
    }
  },
  components: {
    'markdown-editor': MarkdownEditor,
    CommmentItem
  },
  beforeRouteEnter(to, from, next) {
    window.document.body.style.backgroundColor = '#fff'
    next()
  },
  beforeRouteLeave(to, from, next) {
    window.document.body.style.backgroundColor = '#f5f5f5'
    next()
  }
}
</script>

<style scoped>
/* 新增进入时的淡入动画 */
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

.me-view-body {
  margin: 100px auto 140px;
  transition: transform 0.3s ease;
  animation: fadeUp 0.6s ease-out;
}
.me-view-body:hover {
  transform: translateY(-3px);
}

.me-view-container {
  width: 800px;
  transition: box-shadow 0.3s ease;
}
.me-view-container:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.el-main {
  overflow: hidden;
}

/* 标题加大一点 */
.me-view-title {
  font-size: 36px;      /* 加大文章标题字号 */
  font-weight: 800;
  line-height: 1.3;
  transition: color 0.3s ease, letter-spacing 0.3s ease;
}
.me-view-title:hover {
  color: #409eff;
  letter-spacing: 1px;
}

.me-view-author {
  margin-top: 30px;
  vertical-align: middle;
  transition: transform 0.3s ease;
}
.me-view-author:hover {
  transform: translateX(5px);
}

.me-view-picture {
  width: 40px;
  height: 40px;
  border: 1px solid #ddd;
  border-radius: 50%;
  vertical-align: middle;
  background-color: #5fb878;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}
.me-view-picture:hover {
  transform: scale(1.1);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.me-view-info {
  display: inline-block;
  vertical-align: middle;
  margin-left: 8px;
  transition: transform 0.3s ease;
}
.me-view-info:hover {
  transform: translateY(-2px);
}

.me-view-meta {
  font-size: 14px; /* 原12px -> 14px */
  color: #969696;
  transition: color 0.3s ease;
}
.me-view-meta:hover {
  color: #409eff;
}

.me-view-end {
  margin-top: 20px;
  transition: opacity 0.3s ease;
}
.me-view-end:hover {
  opacity: 0.8;
}

/* ============ 标签/分类区域：悬停放大 + 阴影 + 文字变大 ============ */
.me-view-tag {
  margin-top: 20px;
  padding-left: 6px;
  border-left: 4px solid #c5cac3;
  transition: border-color 0.3s ease;
  font-size: 16px; /* 原15px -> 16px */
}
.me-view-tag:hover {
  border-left-color: #409eff;
}

.me-view-tag .el-button {
  margin-right: 8px;
  font-size: 15px; /* 原14px -> 15px */
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}
.me-view-tag .el-button:hover {
  transform: translateY(-2px) scale(1.05);
  box-shadow: 0 4px 8px rgba(64, 158, 255, 0.2);
}
.me-view-tag .el-button:active {
  transform: scale(0.95);
}

.me-view-tag-item {
  margin: 0 4px;
  transition: transform 0.2s ease;
}
.me-view-tag-item:hover {
  transform: scale(1.1);
}

/* ============ 评论整体 ============ */
.me-view-comment {
  margin-top: 60px;
  transition: transform 0.3s ease;
}
.me-view-comment:hover {
  transform: translateY(-3px);
}

/* 评论标题 */
.me-view-comment-title {
  font-weight: 600;
  border-bottom: 1px solid #f0f0f0;
  padding-bottom: 20px;
  transition: border-color 0.3s ease;
  font-size: 18px; /* 加大评论标题字号 */
}
.me-view-comment-title:hover {
  border-color: #409eff;
}

/* 评论编辑区 */
.me-view-comment-write {
  margin-top: 20px;
}

/* 评论文本 */
.me-view-comment-text {
  font-size: 18px; /* 加大评论输入框文字 */
  transition: background-color 0.3s ease;
}
.me-view-comment-text:hover {
  background-color: rgba(64, 158, 255, 0.05);
}

/* ============ 编辑、删除、评论按钮效果 ============ */
.me-action-button {
  transition: transform 0.3s ease, font-size 0.3s ease;
  font-size: 15px; /* 原13px -> 15px */
}
.me-action-button:hover {
  transform: translateY(-2px) scale(1.05);
  font-size: 16px;
}
.me-action-button:active {
  transform: scale(0.95);
}

.me-comment-button {
  transition: transform 0.3s ease, font-size 0.3s ease;
  font-size: 16px; /* 原14px -> 16px */
  color: #409eff;
}
.me-comment-button:hover {
  transform: translateY(-2px) scale(1.05);
  font-size: 17px;
}
.me-comment-button:active {
  transform: scale(0.95);
}

/* 过渡效果：评论列表淡入淡出 */
.list-enter-active,
.list-leave-active {
  transition: all 0.5s;
}
.list-enter,
.list-leave-to {
  opacity: 0;
  transform: translateY(20px);
}

/* ============ 文章正文字体增强 ============ */
/* 使用 ::v-deep 或 >>> 穿透 scoped 限制，覆盖 mavon-editor 渲染的 markdown-body */
.me-view-content ::v-deep .markdown-body {
  font-size: 20px;       /* 整体正文字号更大 */
  line-height: 1.85;
  font-family: "Microsoft YaHei", sans-serif;
}

/* Markdown 中的标题更有层次感 */
.me-view-content ::v-deep .markdown-body h1 {
  font-size: 2.2em;
  margin: 0.6em 0 0.4em;
}
.me-view-content ::v-deep .markdown-body h2 {
  font-size: 1.8em;
  margin: 0.6em 0 0.4em;
}
.me-view-content ::v-deep .markdown-body h3 {
  font-size: 1.6em;
  margin: 0.5em 0 0.3em;
}

/* ============ 代码块增强 ============ */
.me-view-content ::v-deep .markdown-body pre {
  font-size: 18px;         /* 代码块的字体也增大 */
  line-height: 1.65;       /* 代码行距 */
  background-color: #f7f7f7;
  padding: 1em;
  overflow: auto;
  border-radius: 6px;
  margin: 1em 0;
}

/* 让内联代码（`code`）也更明显一些 */
.me-view-content ::v-deep .markdown-body code {
  font-size: 18px;
  background: #f7f7f7;
  padding: 0.2em 0.4em;
  border-radius: 4px;
}
</style>
