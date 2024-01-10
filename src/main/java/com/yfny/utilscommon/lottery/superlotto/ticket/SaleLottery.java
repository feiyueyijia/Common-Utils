package com.yfny.utilscommon.lottery.superlotto.ticket;

import com.yfny.utilscommon.lottery.superlotto.strategy.BettingStrategy;
import com.yfny.utilscommon.util.MathUtils;

/**
 * 销售彩票
 * Author JiSong_ZHOU
 * Date  2023/8/26
 */
public class SaleLottery {

    //期号
    private String issue;

    //红区
    private int[] reds;

    //蓝区
    private int[] blues;

    //红区数量
    private int redSize;

    //蓝区数量
    private int blueSize;

    //倍数
    private int duplication;

    //价格
    private int price;

    public SaleLottery(String issue, int redSize, int blueSize, int duplication) {
        this.issue = issue;
        this.duplication = duplication;
        this.redSize = redSize;
        this.blueSize = blueSize;
        if (redSize < 5 || redSize > 20 || blueSize < 2 || blueSize > 12) {
            this.duplication = 0;
        } else {
            this.price = calculatePrice();
            if (isLessThanMax()) {
                BettingStrategy strategy = new BettingStrategy();
                this.reds = choiceReds(strategy);
                this.blues = MathUtils.getRandom(this.blueSize, 1, 12);
            } else {
                this.duplication = 0;
            }
        }

    }

    public int[] choiceReds(BettingStrategy strategy) {
        int[] reds = MathUtils.getRandom(this.redSize, 1, 35);
        boolean flag = strategy.applyStrategy(reds);
        if (flag) {
            return choiceReds(strategy);
        } else {
            return reds;
        }
    }

    public boolean isLessThanMax() {
        return this.price < 20000;
    }

    public int calculatePrice() {
        long redPro = MathUtils.combination(this.redSize, 5);
        long bluePro = MathUtils.combination(this.blueSize, 2);
        long price = redPro * bluePro * this.duplication * 2;
        return (int) price;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public int[] getReds() {
        return reds;
    }

    public void setReds(int[] reds) {
        this.reds = reds;
    }

    public int[] getBlues() {
        return blues;
    }

    public void setBlues(int[] blues) {
        this.blues = blues;
    }

    public int getDuplication() {
        return duplication;
    }

    public void setDuplication(int duplication) {
        this.duplication = duplication;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
