# springboot-redis-cache2
redis做缓存（非注解）

## 开发工具
- sts 4.x
- maven
- mybatis-plus
- jdk 1.8
- redis
- springboot 2.x

## 介绍
采用非注解形式，同过 RedisTemplate ，对数据进行操作
- 查询 ，将数据从数据库查出后，放入到缓存中去
- 更新， 将数据库的对象跟新后，判断要更新的对象是否存在于缓存中，若存在  将更新后的对象替换缓存中原来的对象；若不存在，缓存中的数据不进行任何处理
- 删除，将数据库中的数据成功删除后，再删除缓存中的数据
