<template>
  <div class="me-allct-body" v-title :data-title="categoryTagTitle">
    <el-container class="me-allct-container">
      <el-main>
        <el-tabs v-model="activeName">
          <!-- 文章分类 -->
          <el-tab-pane label="文章分类" name="category">
            <ul class="me-allct-items">
              <li
                v-for="c in categorys"
                :key="c.id"
                class="me-allct-item"
                @click="view(c.id)"
              >
                <div class="me-allct-content">
                  <a class="me-allct-info">
                    <img
                      class="me-allct-img"
                      :src="c.avatar ? c.avatar : defaultAvatar"
                    />
                    <h4 class="me-allct-name">{{ c.categoryName }}</h4>
                    <p class="me-allct-description">{{ c.description }}</p>
                  </a>
                  <div class="me-allct-meta">
                    <span>{{ c.articles }} 文章</span>
                  </div>
                </div>
              </li>
            </ul>
          </el-tab-pane>

          <!-- 标签 -->
          <el-tab-pane label="标签" name="tag">
            <!-- 新增标签表单 -->
            <div style="margin-bottom: 20px; display: flex; align-items: center;">
              <el-input
                v-model="newTag.tagName"
                placeholder="请输入标签名称"
                style="width: 200px; margin-right: 10px;"
              ></el-input>
              <el-button type="primary" @click="addNewTag">添加标签</el-button>
            </div>

            <!-- 批量删除复选框组和按钮 -->
            <div style="margin-bottom: 20px; display: flex; align-items: center;">
              <el-checkbox-group v-model="selectedTags">
                <!-- 允许选择任意标签 -->
                <el-checkbox
                  v-for="t in tags"
                  :label="t.id"
                  :key="t.id"
                >
                  {{ t.tagName }}
                </el-checkbox>
              </el-checkbox-group>
              <el-button
                type="danger"
                style="margin-left: 20px;"
                @click="deleteSelectedTags"
              >
                删除标签
              </el-button>
            </div>

            <!-- 标签列表 -->
            <ul class="me-allct-items">
              <li v-for="t in tags" :key="t.id" class="me-allct-item">
                <div class="me-allct-content" @click="view(t.id)">
                  <a class="me-allct-info">
                    <img
                      class="me-allct-img"
                      :src="t.avatar ? t.avatar : defaultAvatar"
                    />
                    <h4 class="me-allct-name">{{ t.tagName }}</h4>
                  </a>
                  <div class="me-allct-meta">
                    <span>{{ t.articles }} 文章</span>
                  </div>
                </div>
              </li>
            </ul>
          </el-tab-pane>
        </el-tabs>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import defaultAvatar from '@/assets/img/4.png';
import { getAllCategorysDetail } from '@/api/category';
import { getAllTagsDetail, addTag, removeTag } from '@/api/tag';

export default {
  name: 'BlogAllCategoryTag',
  created() {
    this.getCategorys();
    this.getTags();
  },
  data() {
    return {
      defaultAvatar: defaultAvatar,
      categorys: [],
      tags: [],
      selectedTags: [], // 复选框选中的标签ID集合
      currentActiveName: 'category',
      newTag: {
        tagName: '' // 新标签的名称
      }
    };
  },
  computed: {
    activeName: {
      get() {
        return this.$route.params.type;
      },
      set(newValue) {
        this.currentActiveName = newValue;
      }
    },
    categoryTagTitle() {
      return this.currentActiveName === 'category'
        ? '文章分类 - Warren'
        : '标签 - Warren';
    },
    // 当前登录用户的ID，从 localStorage 获取（转换为数字）
    currentUserId() {
      return Number(localStorage.getItem('userId'));
    }
  },
  methods: {
    view(id) {
      this.$router.push({ path: `/${this.currentActiveName}/${id}` });
    },
    getCategorys() {
      getAllCategorysDetail()
        .then((data) => {
          this.categorys = data.data;
        })
        .catch(() => {
          this.$message({
            type: 'error',
            message: '文章分类加载失败',
            showClose: true
          });
        });
    },
    getTags() {
      getAllTagsDetail()
        .then((data) => {
          this.tags = data.data;
          // 调试：打印所有标签的 createId
          console.log("标签数据：", this.tags);
          this.tags.forEach(tag => {
            console.log(`标签ID: ${tag.id}, tag.createId: ${tag.createId}`);
          });
          console.log("当前用户ID:", this.currentUserId);
        })
        .catch(() => {
          this.$message({
            type: 'error',
            message: '标签加载失败',
            showClose: true
          });
        });
    },
    // 新增标签方法：在添加之前将当前用户ID设置到 newTag.createId，再调用接口
    addNewTag() {
      if (!this.newTag.tagName.trim()) {
        this.$message.error('标签名称不能为空');
        return;
      }
      // 设置当前用户ID到 newTag 的 createId 字段（与后端一致）
      this.newTag.createId = this.currentUserId;

      addTag(this.newTag)
        .then((response) => {
          if (response.success) {
            this.$message.success('标签添加成功');
            this.newTag.tagName = '';
            this.getTags();
          } else {
            this.$message.error('标签添加失败: ' + response.message);
          }
        })
        .catch((error) => {
          console.error('添加标签错误:', error);
          this.$message.error('添加标签时发生错误');
        });
    },
    // 批量删除标签方法：先判断登录状态，再检查是否都是当前用户添加的
    deleteSelectedTags() {
      console.log('deleteSelectedTags triggered');
      if (this.selectedTags.length === 0) {
        this.$message.error('请先选择要删除的标签');
        return;
      }
      console.log('selectedTags:', this.selectedTags);
      const currentUserId = this.currentUserId;
      // 使用 createId 进行比对
      const unauthorized = this.selectedTags.some((selectedId) => {
        const tag = this.tags.find((t) => t.id === selectedId);
        console.log(`比较标签ID: ${selectedId}, tag.createId: ${tag ? tag.createId : 'undefined'}, currentUserId: ${currentUserId}`);
        return !tag || tag.createId !== currentUserId;
      });
      if (unauthorized) {
        this.$message({
          type: 'warning',
          message: '只能删除自己添加的标签',
          showClose: true
        });
        return;
      }
      this.$confirm('确认删除选中的标签吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          console.log('用户确认删除，开始发起删除请求');
          Promise.all(this.selectedTags.map((id) => {
            console.log(`开始删除标签，ID: ${id}`);
            return removeTag(id);
          }))
            .then(() => {
              this.$message.success('删除标签成功');
              this.selectedTags = [];
              this.getTags();
            })
            .catch((err) => {
              console.error('批量删除错误:', err);
              this.$message.error('删除标签时发生错误');
            });
        })
        .catch(() => {
          console.log('用户取消删除');
        });
    }
  },
  beforeRouteEnter(to, from, next) {
    window.document.body.style.backgroundColor = '#fff';
    next();
  },
  beforeRouteLeave(to, from, next) {
    window.document.body.style.backgroundColor = '#f5f5f5';
    next();
  }
};
</script>

<style scoped>
.me-allct-body {
  margin: 60px auto 140px;
}
.me-allct-container {
  width: 1000px;
}
.me-allct-items {
  padding-top: 2rem;
}
.me-allct-item {
  width: 25%;
  display: inline-block;
  margin-bottom: 2.4rem;
  padding: 0 0.7rem;
  box-sizing: border-box;
  position: relative;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}
.me-allct-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
}
.me-allct-content {
  display: inline-block;
  width: 100%;
  background-color: #fff;
  border: 1px solid #f1f1f1;
  transition: border-color 0.3s, background-color 0.3s ease;
  text-align: center;
  padding: 1.5rem 0;
}
.me-allct-content:hover {
  border-color: #409eff;
  background-color: #f9f9f9;
}
.me-allct-info {
  cursor: pointer;
  transition: color 0.3s ease;
}
.me-allct-info:hover {
  color: #409eff;
}
.me-allct-img {
  margin: -40px 0 10px;
  width: 60px;
  height: 60px;
  vertical-align: middle;
  transition: transform 0.3s ease;
}
.me-allct-img:hover {
  transform: scale(1.1);
}
.me-allct-name {
  font-size: 21px;
  font-weight: 150;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin-top: 4px;
  transition: color 0.3s ease;
}
.me-allct-name:hover {
  color: #409eff;
}
.me-allct-description {
  min-height: 50px;
  font-size: 13px;
  line-height: 25px;
}
.me-allct-meta {
  font-size: 12px;
  color: #969696;
  transition: color 0.3s ease;
}
.me-allct-meta:hover {
  color: #666;
}
</style>
