package com.zz.utils.threadsafe.random;

import com.zz.utils.threadsafe.basicwork.Incomplete;

/**
 * 线性同余法
 * N[i+1] = ( A*N[i]+B )%M
 * 为了使周期达到理论最大值M 参数需要满足条件
 * 1. B M 互质
 * 2. M的所有质因数都能整除 A-1
 * 3. 若M是4的倍数 A-1也需要是
 * 4. A B N[0] 都小于M
 * 5. A B都是正整数
 */
@Incomplete
@Deprecated
public class LCGRandom {
}
