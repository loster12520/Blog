<template>
  <div class="me-view-comment-item">
    <div class="me-view-comment-author">
      <a>
        <img class="me-view-picture" :src="comment.commentator.avatar" />
      </a>
      <div class="me-view-info">
        <span class="me-view-nickname">{{ comment.commentator.nickname }}</span>
        <div class="me-view-meta">
          <span>{{ rootCommentCounts - index }}楼</span>
          <span>{{ comment.createDate | format }}</span>
        </div>
      </div>
    </div>
    <div>
      <p class="me-view-comment-content">{{ comment.content }}</p>
      <div class="me-view-comment-tools">
        <!-- 点击显示回复框 -->
        <a class="me-view-comment-tool" @click="showComment(-1, comment.commentator)">
          <i class="me-icon-comment"></i>&nbsp; 评论
        </a>
        <!-- 删除按钮：仅当 canDeleteComment(comment) 返回 true 时显示 -->
        <el-button
          v-if="canDeleteComment(comment)"
          size="mini"
          type="text"
          icon="el-icon-delete"
          @click="deleteComment(comment)"
        >
          删除
        </el-button>
      </div>

      <!-- 子评论列表 -->
      <div class="me-reply-list" v-if="comment.childrens && comment.childrens.length > 0">
        <div class="me-reply-item" v-for="(child, childIndex) in comment.childrens" :key="child.id">
          <div style="font-size: 14px">
            <span class="me-reply-user">{{ child.commentator.nickname }}:&nbsp;&nbsp;</span>
            <span v-if="child.level === 2" class="me-reply-user">@{{ child.toUser.nickname }} </span>
            <span>{{ child.content }}</span>
          </div>
          <div class="me-view-meta">
            <span style="padding-right: 10px">{{ child.createDate | format }}</span>
            <!-- 子评论删除按钮 -->
            <el-button
              v-if="canDeleteChildComment(child)"
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="deleteChildComment(child)"
            >
              删除
            </el-button>
          </div>
        </div>
      </div>

      <!-- 回复输入框 -->
      <div class="me-view-comment-write" v-show="commentShow">
        <el-input
          v-model="reply.content"
          type="input"
          style="width: 90%"
          :placeholder="placeholder"
          class="me-view-comment-text"
          resize="none"
        ></el-input>
        <el-button style="margin-left: 8px" @click="publishComment()" type="text">评论</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { publishComment, deleteCommentAPI } from '@/api/comment'

export default {
  name: 'CommentItem',
  props: {
    // 文章ID
    articleId: {
      type: [Number, String],
      required: true
    },
    // 当前评论对象
    comment: {
      type: Object,
      required: true
    },
    // 评论在列表中的索引
    index: Number,
    // 根评论总数（显示几楼）
    rootCommentCounts: Number,
    // 文章作者ID
    articleAuthorId: {
      type: [Number, String],
      required: true
    },
    // 当前登录用户ID
    currentUserId: {
      type: [Number, String],
      required: true
    }
  },
  data() {
    return {
      placeholder: '你的评论...',
      commentShow: false,
      commentShowIndex: '',
      reply: this.getEmptyReply()
    }
  },
  methods: {
    /**
     * 显示/隐藏回复框
     */
    showComment(commentShowIndex, toUser) {
      this.reply = this.getEmptyReply()
      if (this.commentShowIndex !== commentShowIndex) {
        if (toUser) {
          this.placeholder = `@${toUser.nickname} `
          this.reply.toUserId = toUser.id
        } else {
          this.placeholder = '你的评论...'
        }
        this.commentShow = true
        this.commentShowIndex = commentShowIndex
      } else {
        this.commentShow = false
        this.commentShowIndex = ''
      }
    },
    /**
     * 发布子评论
     */
    publishComment() {
      if (!this.reply.content) return
      this.reply.articleId = this.articleId
      this.reply.parent = this.comment.id

      publishComment(this.reply, this.$store.state.token)
        .then(data => {
          if (data.success) {
            this.$message({ type: 'success', message: '评论成功', showClose: true })
            if (!this.comment.childrens) {
              this.comment.childrens = []
            }
            // 插入新评论并自动更新评论数量
            this.comment.childrens.unshift(data.data)
            this.$emit('commentCountsIncrement') // 触发评论数量更新
            this.showComment(this.commentShowIndex) // 关闭回复框
          } else {
            this.$message({ type: 'error', message: data.msg, showClose: true })
          }
        })
        .catch(() => {
          this.$message({ type: 'error', message: '评论失败', showClose: true })
        })
    }
,
    /**
     * 初始化空的回复对象
     */
    getEmptyReply() {
      return {
        articleId: this.articleId,
        parent: this.comment.id,
        toUserId: '',
        content: ''
      }
    },
    /**
     * 判断是否能删除该评论：文章作者或评论作者
     */
    canDeleteComment(aComment) {
      const articleAuthorId = Number(this.articleAuthorId)
      const currentUserId = Number(this.currentUserId)
      const commentAuthorId = Number(aComment.commentator.id)
      return articleAuthorId === currentUserId || commentAuthorId === currentUserId
    },
    /**
     * 判断是否能删除子评论
     */
    canDeleteChildComment(childComment) {
      const articleAuthorId = Number(this.articleAuthorId)
      const currentUserId = Number(this.currentUserId)
      const commentAuthorId = Number(childComment.commentator.id)
      return articleAuthorId === currentUserId || commentAuthorId === currentUserId
    },
    /**
     * 删除评论（主评论）
     */
    deleteComment(aComment) {
      this.$confirm('确定要删除该评论吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          const token = this.$store.state.token
          deleteCommentAPI(aComment.id, token)
            .then(res => {
              if (res.data === '删除成功!') {
                this.$message.success('删除成功')
                this.$emit('remove-comment', aComment.id)
              } else {
                this.$message.error(res || '删除失败')
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
    },
    /**
     * 删除子评论
     */
    deleteChildComment(childComment) {
      this.$confirm('确定要删除该子评论吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          const token = this.$store.state.token
          deleteCommentAPI(childComment.id, token)
            .then(res => {
              if (res === '删除成功!') {
                this.$message.success('删除成功')
                // 过滤掉被删除的子评论
                this.comment.childrens = this.comment.childrens.filter(c => c.id !== childComment.id)
                this.$emit('commentCountsIncrement') // 触发评论数量更新
              } else {
                this.$message.error(res || '删除失败')
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

  }
}
</script>

<style scoped>
/* 整个评论容器，增大上方和下方间距，并加一个浅色背景或边框 */
.me-view-comment {
  margin-top: 40px;
  padding: 20px;
  border: 1px solid #f0f0f0;
  border-radius: 6px;
  background-color: #fff;
}

/* 评论标题：增大字体，增加下方留白 */
.me-view-comment-title {
  font-size: 16px;
  font-weight: 600;
  border-bottom: 1px solid #e0e0e0;
  padding-bottom: 10px;
  margin-bottom: 20px;
}

/* 评论单项之间增加间距 */
.me-view-comment-item {
  margin-bottom: 30px; /* 每条评论之间留点空 */
}

/* 评论作者区域：让头像和文字排版更整齐 */
.me-view-comment-author {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}
.me-view-comment-author .me-view-picture {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  margin-right: 10px;
}
.me-view-info {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

/* 评论内容：增大字号，留点空间 */
.me-view-comment-content {
  font-size: 15px;
  line-height: 1.6;
  margin: 8px 0 12px;
}

/* 评论工具条：删除/回复按钮之类，放在同一行 */
.me-view-comment-tools {
  display: flex;
  align-items: center;
  gap: 15px; /* 按钮之间留点空 */
}

/* 回复输入框所在区域 */
.me-view-comment-write {
  margin-top: 15px;
}

/* 回复输入框：让它看起来更大一些 */
.me-view-comment-text {
  width: 100%;
  font-size: 14px;
  min-height: 60px;
  margin-bottom: 8px;
}

/* 子评论列表：让子评论有一定缩进，并与主评论分开 */
.me-reply-list {
  margin-top: 10px;
  padding-left: 50px;
  border-left: 2px solid #f0f0f0;
}
.me-reply-item {
  margin-bottom: 8px; /* 每条子评论之间一点间距 */
  font-size: 14px;
  line-height: 1.4;
}
.me-reply-user {
  color: #409eff;
  font-weight: 500;
}

/* 自定义回复按钮图标 */
.me-view-comment-tool i {
  color: #409eff;
  margin-right: 2px;
}

/* 你原本的 .me-view-tag-item 等其他样式保持即可，如无冲突就不需要动 */
</style>
