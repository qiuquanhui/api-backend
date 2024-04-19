package com.hui.apiclient.utils;/**
 * 作者:灰爪哇
 * 时间:2024-04-17
 */

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 *
 *
 * @author: Hui
 **/
public class SignUtil {

    public static String getSign(String body, String secretKey){
        Digester digester = new Digester(DigestAlgorithm.SHA256);
        return digester.digestHex(body + secretKey);
    }
}
