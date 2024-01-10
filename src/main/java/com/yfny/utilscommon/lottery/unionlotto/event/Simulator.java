package com.yfny.utilscommon.lottery.unionlotto.event;

import com.yfny.utilscommon.lottery.award.Award;
import com.yfny.utilscommon.lottery.unionlotto.ticket.AwardLottery;
import com.yfny.utilscommon.lottery.unionlotto.ticket.SaleLottery;
import com.yfny.utilscommon.util.MathUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 模拟器
 * Author JiSong_ZHOU
 * Date  2023/8/25
 */
public class Simulator {

    /**
     * 设置奖项
     */
    public List<Award> showAward() {
        List<Award> awards = new ArrayList<>();
        int total = 17721088;
        int scale = 10;
        awards.add(new Award("一等奖", 5000000, "6+1", MathUtils.mathAve(1.0, total, scale)));
        awards.add(new Award("二等奖", 100000, "6+0", MathUtils.mathAve(15.0, total, scale)));
        awards.add(new Award("三等奖", 3000, "5+1", MathUtils.mathAve(162.0, total, scale)));
        awards.add(new Award("四等奖", 200, "5+0,4+1", MathUtils.mathAve(7695.0, total, scale)));
        awards.add(new Award("五等奖", 10, "4+0,3+1", MathUtils.mathAve(137475.0, total, scale)));
        awards.add(new Award("六等奖", 5, "2+1,1+1,0+1", MathUtils.mathAve(1043640.0, total, scale)));
        return awards;
    }

    public Award winAward(SaleLottery saleLottery, AwardLottery awardLottery) {
        Award award;
        int winRed = matchNum(saleLottery.getReds(), awardLottery.getReds());
        int winBlue = matchNum(saleLottery.getBlues(), awardLottery.getBlues());
        String winResult = winRed + "+" + winBlue;
        switch (winResult) {
            case "6+1":
                award = showAward().get(0);
                break;
            case "6+0":
                award = showAward().get(1);
                break;
            case "5+1":
                award = showAward().get(2);
                break;
            case "5+0":
            case "4+1":
                award = showAward().get(3);
                break;
            case "4+0":
            case "3+1":
                award = showAward().get(4);
                break;
            case "2+1":
            case "1+1":
            case "0+1":
                award = showAward().get(5);
                break;
            default:
                award = null;
                break;
        }
        return award;
    }

    public int matchNum(int[] sales, int[] awards) {
        int result = 0;
        for (int sale : sales) {
            boolean contains = IntStream.of(awards).anyMatch(x -> x == sale);
            if (contains) {
                result++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Simulator simulator = new Simulator();
        String issue = "第 1 期";
        List<Award> awards = new ArrayList<>();
        AwardLottery awardLottery = new AwardLottery(issue);
        int times = 17721088;
        //int times = 10;
        int redSize = 6;
        int blueSize = 16;
        int duplication = 1;
        int scale = 10;
        int cost = 0;
        int bonus = 0;
        for (int i = 0; i < times; i++) {
            SaleLottery saleLottery = new SaleLottery(issue, redSize, blueSize, duplication);
            //System.out.println("本期购彩号码为：" + Arrays.toString(saleLottery.getReds()) + " " + Arrays.toString(saleLottery.getBlues()));
            cost = cost + saleLottery.getPrice();
            Award award = simulator.winAward(saleLottery, awardLottery);
            if (award != null) {
                awards.add(award);
                bonus = bonus + award.getBonus() * saleLottery.getDuplication();
                if (award.getBonus() > 3000) {
                    System.out.println("恭喜在第" + (i + 1) + "次获得" + award.getLevel());
                }
            }
        }
        double awardCount = awards.size();
        double probability = MathUtils.mathAve(awardCount * 100, times, scale);
        double award1Count = (int) awards.stream().filter(award -> award.getLevel().equals("一等奖")).count();
        double probability1 = MathUtils.mathAve(award1Count * 100, times, scale);
        double award2Count = (int) awards.stream().filter(award -> award.getLevel().equals("二等奖")).count();
        double probability2 = MathUtils.mathAve(award2Count * 100, times, scale);
        double award3Count = (int) awards.stream().filter(award -> award.getLevel().equals("三等奖")).count();
        double probability3 = MathUtils.mathAve(award3Count * 100, times, scale);
        double award4Count = (int) awards.stream().filter(award -> award.getLevel().equals("四等奖")).count();
        double probability4 = MathUtils.mathAve(award4Count * 100, times, scale);
        double award5Count = (int) awards.stream().filter(award -> award.getLevel().equals("五等奖")).count();
        double probability5 = MathUtils.mathAve(award5Count * 100, times, scale);
        double award6Count = (int) awards.stream().filter(award -> award.getLevel().equals("六等奖")).count();
        double probability6 = MathUtils.mathAve(award6Count * 100, times, scale);
        double returnRate = MathUtils.mathAve((double) (bonus - cost) * 100, cost, scale);
        System.out.println("本期中奖号码为：" + Arrays.toString(awardLottery.getReds()) + " " + Arrays.toString(awardLottery.getBlues()));
        System.out.println("共购买" + times + "注，中奖" + awardCount + "注，中奖概率" + probability + "(" +  6.71 * blueSize + ")%");
        System.out.println("一等奖" + award1Count + "注，中奖概率" + probability1 + "(" + simulator.showAward().get(0).getProbability() * blueSize * 100 + ")%");
        System.out.println("二等奖" + award2Count + "注，中奖概率" + probability2 + "(" + simulator.showAward().get(1).getProbability() * blueSize * 100 + ")%");
        System.out.println("三等奖" + award3Count + "注，中奖概率" + probability3 + "(" + simulator.showAward().get(2).getProbability() * blueSize * 100 + ")%");
        System.out.println("四等奖" + award4Count + "注，中奖概率" + probability4 + "(" + simulator.showAward().get(3).getProbability() * blueSize * 100 + ")%");
        System.out.println("五等奖" + award5Count + "注，中奖概率" + probability5 + "(" + simulator.showAward().get(4).getProbability() * blueSize * 100 + ")%");
        System.out.println("六等奖" + award6Count + "注，中奖概率" + probability6 + "(" + simulator.showAward().get(5).getProbability() * blueSize * 100 + ")%");
        System.out.println("共花费" + cost + "元，奖金" + bonus + "元，总获利" + (bonus - cost) + "元，回报率" + returnRate + "%");
    }
}
