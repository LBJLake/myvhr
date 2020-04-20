let proxyObj={};

proxyObj['/ws/*']={
    ws:true,
    target:'ws://192.168.31.196:8081'
};

proxyObj['/']={
    ws:false,
    target:'http://192.168.31.196:8081',
    changeOrigin: true,
    pathRewrite:{
        '^/':''
    }
};
module.exports={
    devServer:{
        host:'192.168.31.196',
        port:8080,
        proxy:proxyObj
    }
}