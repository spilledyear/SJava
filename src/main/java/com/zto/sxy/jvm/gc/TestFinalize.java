package com.zto.sxy.jvm.gc;

public class TestFinalize {
    private static TestFinalize SAVE_HOOK = null;

    public void isAlive(){
        System.out.println("year, i am still alive");
    }


    @Override
    protected void finalize() throws Throwable{
        super.finalize();
        System.out.println("finalize method executed");
        TestFinalize.SAVE_HOOK = this;

    }

    public static void main(String[] args) throws Throwable{
        SAVE_HOOK = new TestFinalize();


        // 第一次成功拯救自己
        SAVE_HOOK = null;
        // 虚拟机自动建立的、低优先级的Finalized线程去执行
        System.gc();
        // 因为执行finalize方法的线程优先级低，所以等待1s让它执行完成
        Thread.sleep(1000);
        if(SAVE_HOOK != null){
            SAVE_HOOK.isAlive();
        }else{
            System.out.println("no, i am dead");
        }


        // 上面这段代码与上面的完全相同，但这次自救却失败了
        SAVE_HOOK = null;
        System.gc();
        Thread.sleep(500);
        if(SAVE_HOOK != null){
            SAVE_HOOK.isAlive();
        }else{
            System.out.println("no, i am dead");
        }
    }

}
