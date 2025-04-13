
import Vue from 'vue'
import App from './App'

import router from './router'
import store from './store'


import lodash from 'lodash'

import ElementUI from 'element-ui'
import '@/assets/theme/index.css'
import "@/assets/theme/global.css";
import '@/assets/icon/iconfont.css'

import {formatTime} from "./utils/time";


Vue.config.productionTip = false

Vue.use(ElementUI)

Object.defineProperty(Vue.prototype, '$_', { value: lodash })


Vue.directive('title',  function (el, binding) {
  document.title = el.dataset.title
})
// 格式话时间
Vue.filter('format', formatTime)

new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App }
})
document.addEventListener("click", (e) => {
  const ripple = document.createElement("div");
  ripple.className = "ripple";
  document.body.appendChild(ripple);

  // 让波纹中心在点击位置
  ripple.style.left = `${e.clientX - 10}px`;
  ripple.style.top = `${e.clientY - 10}px`;

  // 一定时间后移除
  setTimeout(() => {
    ripple.remove();
  }, 1200);
});

