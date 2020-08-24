## 证书申请

在阿里云上申请证书，证书的申请比较简单





下载下来的证书上传至服务器上，其中包含一个 cem 文件和一个 key 文件



## 修改 nginx 配置

找到 nginx 的安装目录，在 conf 目录下修改 nginx.conf

找到以下的配置信息

```nginx
# HTTPS server
server {
  listen 443;
  server_name localhost;
  ssl on;
  ssl_certificate cert.pem;
  ssl_certificate_key cert.key;
  ssl_session_timeout 5m;
  ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
  ssl_ciphers ALL:!ADH:!EXPORT56:RC4+RSA:+HIGH:+MEDIUM:+LOW:+SSLv2:+EXP;
  ssl_prefer_server_ciphers on;
  location / {
    ...
  }
}
```

按照下文修改如下配置文件

```nginx
# 以下属性中以ssl开头的属性代表与证书配置有关，其他属性请根据自己的需要进行配置。
server {
    listen 443 ssl;   #SSL协议访问端口号为443。此处如未添加ssl，可能会造成Nginx无法启动。
    server_name localhost;  #将localhost修改为您证书绑定的域名，例如：www.example.com。
    root html;
    index index.html index.htm;
    ssl_certificate cert/domain name.pem;   #将domain name.pem替换成您证书的文件名。
    ssl_certificate_key cert/domain name.key;   #将domain name.key替换成您证书的密钥文件名。
    ssl_session_timeout 5m;
    ssl_ciphers ECDHE-RSA-AES128-GCM-SHA256:ECDHE:ECDH:AES:HIGH:!NULL:!aNULL:!MD5:!ADH:!RC4;  #使用此加密套件。
    ssl_protocols TLSv1 TLSv1.1 TLSv1.2;   #使用该协议进行配置。
    ssl_prefer_server_ciphers on;   
    location / {
    root html;   #站点目录。
    index index.html index.htm;   
	}
}                     
```

修改完毕后，保存文件退出。

重启 nginx

```shell
./nginx -s stop  //停止 nginx 服务
./nginx   //启动 nginx 服务
```

## 配置 http 自动跳转到 https

```nginx
server {
 	listen 80;
 	server_name localhost;   #将localhost修改为您证书绑定的域名，例如：www.example.com。
	rewrite ^(.*)$ https://$host$1 permanent;   #将所有http请求通过rewrite重定向到https。
 	location / {
		index index.html index.htm;
	}
}
```

