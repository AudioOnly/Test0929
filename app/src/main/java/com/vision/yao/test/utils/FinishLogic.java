package com.vision.yao.test.utils;

/**
 * 再按一次退出逻辑类
 */
public abstract class FinishLogic {

    private long clickTime = 0;
    private long doubleTime = 2000;

    public FinishLogic() {
    }

    public FinishLogic(int doubleTime) {
        this.doubleTime = doubleTime;
    }

    public final void onKeyBack() {
        long now = System.currentTimeMillis();
        if (Math.abs(now - clickTime) > doubleTime) {
            clickTime = now;
            touchAgain();
        } else {
            onFinish();
        }
    }

    /**
     * 结束
     */
    public abstract void onFinish();

    /**
     * 再按一次
     */
    public abstract void touchAgain();

}
