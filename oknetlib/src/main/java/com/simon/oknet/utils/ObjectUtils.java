package com.simon.oknet.utils;

/**
 * description: 对象工具类
 * author: Simon
 * created at 2017/9/1 下午7:05
 */

public class ObjectUtils {

    /**
     * check obs does not contain null
     *
     * @param obs ob arrays
     * @return if not contain null return true else return false
     */
    public static boolean checkNotNull(Object... obs) {

        //--check obs--
        if (obs == null) {
            return false;
        }

        //--traverse obs--
        for (Object ob : obs) {
            if (ob == null) {
                return false;
            }
        }

        //--Excute here to explain obs does not contain null--
        return true;
    }

    /**
     * check obs  not  null,if it is true nothing to do else throws an exception.
     *
     * @param errMsg -- show err msg
     * @param obs    -- ob array
     */
    public static void checkNotNullErr(Object obs, String errMsg) {
        if (!checkNotNull(obs)) {
            ExceptionUtils.throwNullPointerException(errMsg);
        }
    }
}
