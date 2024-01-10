package com.yfny.utilscommon.lottery.award;

/**
 * 号码计数器
 * Author JiSong_ZHOU
 * Date  2024/1/10
 */
public class Counter {

    private int num;

    private int count;

    public Counter() {

    }

    public Counter(int num, int count) {
        this.num = num;
        this.count = count;
    }

    public void addCount() {
        this.count += 1;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
