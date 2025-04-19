<template>
  <el-header class="me-area">
    <el-row class="me-header">
      <!-- Logo 区域 -->
      <el-col :span="2" class="me-header-left">
        <router-link to="/" class="me-title">
          <img src="../assets/img/4.png" alt="Logo"/>
        </router-link>
      </el-col>

      <!-- 菜单区域（非简单模式下显示） -->
      <el-col v-if="!simple" :span="12">
        <el-menu
          :router="true"
          menu-trigger="click"
          active-text-color="#5FB878"
          :default-active="activeIndex"
          mode="horizontal"
        >
          <el-menu-item index="/">首页</el-menu-item>
          <el-menu-item index="/category/all">文章分类</el-menu-item>
          <el-menu-item index="/tag/all">标签</el-menu-item>
          <el-menu-item index="/archives">文章归档</el-menu-item>
          <el-col :span="4" :offset="4">
            <el-menu-item index="/write">
              <i class="el-icon-edit"></i>写文章
            </el-menu-item>
          </el-col>
        </el-menu>
      </el-col>

      <!-- 简单模式时的插槽 -->
      <template v-else>
        <slot></slot>
      </template>

      <!-- 搜索框区域：仅在路由不是 /write 时显示 -->
      <el-col v-if="$route.path !== '/write'" :span="6">
        <el-menu mode="horizontal" active-text-color="#5FB878">
          <el-menu-item>
            <el-autocomplete
              v-model="search"
              :fetch-suggestions="querySearchAsync"
              placeholder="请输入内容"
              @select="handleSelect"
            ></el-autocomplete>
          </el-menu-item>
        </el-menu>
      </el-col>

      <!-- 登录/注册/用户头像区域 -->
      <el-col :span="$route.path === '/write' ? 10 : 4">
        <el-menu :router="true" menu-trigger="click" mode="horizontal" active-text-color="#5FB878">
          <template v-if="!user.login">
            <el-menu-item index="/login">
              <el-button type="text">登录</el-button>
            </el-menu-item>
            <el-menu-item index="/register">
              <el-button type="text">注册</el-button>
            </el-menu-item>
          </template>
          <template v-else>
            <!-- 修改判断：只要是 /write 相关路径（包括编辑页 /write/:id）都不显示 -->
            <el-submenu v-if="!isWritePage" index>
              <template slot="title">
                <img class="me-header-picture" :src="user.avatar" alt="头像"/>
              </template>
              <el-menu-item index @click="details">
                个人信息
              </el-menu-item>
              <br/>
              <el-menu-item index @click="logout">
                <i class="el-icon-back"></i>退出
              </el-menu-item>
            </el-submenu>
          </template>
        </el-menu>
      </el-col>
    </el-row>
  </el-header>
</template>

<script>
import {searchArticle} from '@/api/article'

export default {
  name: 'BaseHeader',
  props: {
    activeIndex: String,
    simple: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      search: '', // 搜索框输入
      articles: [] // 搜索结果列表
    }
  },
  computed: {
    user() {
      const login = this.$store.state.account.length !== 0
      const avatar = this.$store.state.avatar
      return {login, avatar}
    },
    // 判断当前是否为写文章相关页面（包括 /write 和 /write/:id）
    isWritePage() {
      return this.$route.path.startsWith('/write')
    }
  },
  methods: {
    details() {
      this.$router.push("userDetail")
    },
    logout() {
      this.$store.dispatch('logout')
        .then(() => {
          this.$router.push({path: '/'})
        })
        .catch(error => {
          if (error !== 'error') {
            this.$message({message: error, type: 'error', showClose: true})
          }
        })
    },
    // 异步查询建议
    querySearchAsync(queryString, cb) {
      searchArticle(this.search).then(res => {
        if (res.success) {
          const results = res.data.map(item => ({
            id: item.id,
            value: item.title
          }))
          cb(results)
        }
      })
    },
    // 选择某个搜索结果时跳转到对应文章详情
    handleSelect(item) {
      this.$router.push({path: '/view/' + item.id})
    }
  }
}
</script>

<style>
.el-header {
  position: fixed;
  z-index: 1024;
  min-width: 100%;
  box-shadow: 0 2px 3px hsla(0, 0%, 7%, 0.1), 0 0 0 1px hsla(0, 0%, 7%, 0.1);
  background: #fff;
}

.me-title {
  margin-top: 10px;
  font-size: 24px;
}

.me-header-left {
  margin-top: 10px;
}

.me-title img {
  max-height: 2.4rem;
  max-width: 100%;
}

.me-header-picture {
  width: 36px;
  height: 36px;
  border: 1px solid #ddd;
  border-radius: 50%;
  vertical-align: middle;
  background-color: #5fb878;
}

/* 导航菜单交互样式 */
.me-header .el-menu-item {
  transition: transform 0.3s ease, font-size 0.3s ease;
  font-size: 14px;
}

.me-header .el-menu-item:hover {
  transform: translateY(-2px) scale(1.05);
  font-size: 15px;
}

.me-header .el-menu-item:active {
  transform: scale(0.95);
}
</style>
