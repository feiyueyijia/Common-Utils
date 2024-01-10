package com.yfny.utilscommon.lottery.award;

import com.yfny.utilscommon.util.MathUtils;

/**
 * 彩票奖项
 * Author JiSong_ZHOU
 * Date  2023/8/25
 */
public class Award {

    //奖等
    private String level;

    //奖金
    private int bonus;

    //中奖条件
    private String condition;

    //中奖概率
    private double probability;

    public Award() {

    }

    public Award(String level, int bonus, String condition, double probability) {
        this.level = level;
        this.bonus = bonus;
        this.condition = condition;
        this.probability = MathUtils.mathAve(probability, 1, 10);
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}
