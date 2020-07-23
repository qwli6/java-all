## Nginx 相关操作

启动停止 nginx

```nginx
./nginx -s stop
./nginx
```

重新加载 nginx 的配置文件

```nginx
./nginx -s reload
```

验证 nginx 的配置文件是否正确

```nginx
./nginx -t
```

启动前验证配置文件是否正确

```nginx
./nginx -t -c 配置文件地址
```