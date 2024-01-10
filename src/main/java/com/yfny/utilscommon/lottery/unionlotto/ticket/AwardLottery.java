package com.yfny.utilscommon.lottery.unionlotto.ticket;

import com.yfny.utilscommon.util.MathUtils;

/**
 * 开奖号码
 * Author JiSong_ZHOU
 * Date  2023/8/8
 */
public class AwardLottery {

    //期号
    private String issue;

    //红区
    private int[] reds;

    //蓝区
    private int[] blues;

    public AwardLottery(String issue) {
        int[] reds = MathUtils.getRandom(6, 1, 33);
        int[] blues = MathUtils.getRandom(1, 1, 16);
        this.issue = issue;
        this.reds = reds;
        this.blues = blues;
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
}
