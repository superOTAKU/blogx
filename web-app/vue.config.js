const path = require('path')

module.exports = {
    devServer: {
      port: 8000, // 端口
    },
    configureWebpack: {  
        resolve: {  
          alias: {  
              '@': path.resolve(__dirname, 'src/')
          },  
        },  
    }
  }