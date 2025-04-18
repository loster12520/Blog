//package warren.myblog.common;
//
//import warren.myblog.pojo.SysUser;
//
///**
// * 使用线程池获取登录用户信息.
// */
//public class UserThreadLocal {
//
//    private UserThreadLocal(){}
//
//    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();
//
//    public static void put(SysUser sysUser){
//        LOCAL.set(sysUser);
//    }
//    public static SysUser get(){
//        return LOCAL.get();
//    }
//    public static void remove(){
//        LOCAL.remove();
//    }
//}