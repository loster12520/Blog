module.exports = {
  devServer: {
    proxy: {
      // 添加路径排除规则，避免静态文件被代理
      '**': {
        target: '',
        bypass: function (req) {
          if (req.url.startsWith('/static/') || req.url.startsWith('/assets/')) {
            return req.url;
          }
        }
      }
    }
  }
};
