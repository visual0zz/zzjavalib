#流加密工具
1. HashService
> 对java系统的MessageDigest的一个简单包装，提供了byte数组和十六进制字符串的转换
提供哈希服务
2. InputStreamEncrypter OutputStreamEncrypter
>流加密器，基于最简单的异或加密，本身定义为一个流包装器，
直接安装在某个流上就可以进行加密。
3. RandomGenerator
>随机生成器，给一个种子，生成无限长的伪随机序列。