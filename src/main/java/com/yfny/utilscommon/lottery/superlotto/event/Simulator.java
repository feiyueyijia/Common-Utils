package com.yfny.utilscommon.lottery.superlotto.event;

import com.yfny.utilscommon.lottery.award.Award;
import com.yfny.utilscommon.lottery.superlotto.ticket.AwardLottery;
import com.yfny.utilscommon.lottery.superlotto.ticket.SaleLottery;
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
        int total = 21425712;
        int scale = 10;
        awards.add(new Award("一等奖", 10000000, "5+2", MathUtils.mathAve(1.0, total, scale)));
        awards.add(new Award("二等奖", 150000, "5+1", MathUtils.mathAve(20.0, total, scale)));
        awards.add(new Award("三等奖", 10000, "5+0", MathUtils.mathAve(45.0, total, scale)));
        awards.add(new Award("四等奖", 3000, "4+2", MathUtils.mathAve(150.0, total, scale)));
        awards.add(new Award("五等奖", 300, "4+1", MathUtils.mathAve(3000.0, total, scale)));
        awards.add(new Award("六等奖", 200, "3+2", MathUtils.mathAve(4350.0, total, scale)));
        awards.add(new Award("七等奖", 100, "4+0", MathUtils.mathAve(6750.0, total, scale)));
        awards.add(new Award("八等奖", 15, "3+1,2+2", MathUtils.mathAve(127600.0, total, scale)));
        awards.add(new Award("九等奖", 5, "3+0,2+1,1+2,0+2", MathUtils.mathAve(1287281.0, total, scale)));
        return awards;
    }

    public Award winAward(SaleLottery saleLottery, AwardLottery awardLottery) {
        Award award;
        int winRed = matchNum(saleLottery.getReds(), awardLottery.getReds());
        int winBlue = matchNum(saleLottery.getBlues(), awardLottery.getBlues());
        String winResult = winRed + "+" + winBlue;
        switch (winResult) {
            case "5+2":
                award = showAward().get(0);
                break;
            case "5+1":
                award = showAward().get(1);
                break;
            case "5+0":
                award = showAward().get(2);
                break;
            case "4+2":
                award = showAward().get(3);
                break;
            case "4+1":
                award = showAward().get(4);
                break;
            case "3+2":
                award = showAward().get(5);
                break;
            case "4+0":
                award = showAward().get(6);
                break;
            case "3+1":
            case "2+2":
                award = showAward().get(7);
                break;
            case "3+0":
            case "2+1":
            case "1+2":
            case "0+2":
                award = showAward().get(8);
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
        //int times = 21425712;
        int times = 1000;
        int cost = 0;
        int bonus = 0;
        for (int i = 0; i < times; i++) {
            SaleLottery saleLottery = new SaleLottery(issue, 5, 2, 1);
            //System.out.println("本期购彩号码为：" + Arrays.toString(saleLottery.getReds()) + " " + Arrays.toString(saleLottery.getBlues()));
            cost = cost + saleLottery.getPrice();
            Award award = simulator.winAward(saleLottery, awardLottery);
            if (award != null) {
                awards.add(award);
                bonus = bonus + award.getBonus() * saleLottery.getDuplication();
                if (award.getBonus() > 10000) {
                    System.out.println("恭喜在第" + (i + 1) + "次获得" + award.getLevel());
                }
            }
        }
        double awardCount = awards.size();
        double probability = MathUtils.mathAve(awardCount * 100, times, 10);
        double award1Count = (int) awards.stream().filter(award -> award.getLevel().equals("一等奖")).count();
        double probability1 = MathUtils.mathAve(award1Count * 100, times, 10);
        double award2Count = (int) awards.stream().filter(award -> award.getLevel().equals("二等奖")).count();
        double probability2 = MathUtils.mathAve(award2Count * 100, times, 10);
        double award3Count = (int) awards.stream().filter(award -> award.getLevel().equals("三等奖")).count();
        double probability3 = MathUtils.mathAve(award3Count * 100, times, 10);
        double award4Count = (int) awards.stream().filter(award -> award.getLevel().equals("四等奖")).count();
        double probability4 = MathUtils.mathAve(award4Count * 100, times, 10);
        double award5Count = (int) awards.stream().filter(award -> award.getLevel().equals("五等奖")).count();
        double probability5 = MathUtils.mathAve(award5Count * 100, times, 10);
        double award6Count = (int) awards.stream().filter(award -> award.getLevel().equals("六等奖")).count();
        double probability6 = MathUtils.mathAve(award6Count * 100, times, 10);
        double award7Count = (int) awards.stream().filter(award -> award.getLevel().equals("七等奖")).count();
        double probability7 = MathUtils.mathAve(award7Count * 100, times, 10);
        double award8Count = (int) awards.stream().filter(award -> award.getLevel().equals("八等奖")).count();
        double probability8 = MathUtils.mathAve(award8Count * 100, times, 10);
        double award9Count = (int) awards.stream().filter(award -> award.getLevel().equals("九等奖")).count();
        double probability9 = MathUtils.mathAve(award9Count * 100, times, 10);
        double returnRate = MathUtils.mathAve((double) (bonus - cost) * 100, cost, 10);
        System.out.println("本期中奖号码为：" + Arrays.toString(awardLottery.getReds()) + " " + Arrays.toString(awardLottery.getBlues()));
        System.out.println("共购买" + times + "注，中奖" + awardCount + "注，中奖概率" + probability + "(6.67)%");
        System.out.println("一等奖" + award1Count + "注，中奖概率" + probability1 + "(" + simulator.showAward().get(0).getProbability() * 100 + ")%");
        System.out.println("二等奖" + award2Count + "注，中奖概率" + probability2 + "(" + simulator.showAward().get(1).getProbability() * 100 + ")%");
        System.out.println("三等奖" + award3Count + "注，中奖概率" + probability3 + "(" + simulator.showAward().get(2).getProbability() * 100 + ")%");
        System.out.println("四等奖" + award4Count + "注，中奖概率" + probability4 + "(" + simulator.showAward().get(3).getProbability() * 100 + ")%");
        System.out.println("五等奖" + award5Count + "注，中奖概率" + probability5 + "(" + simulator.showAward().get(4).getProbability() * 100 + ")%");
        System.out.println("六等奖" + award6Count + "注，中奖概率" + probability6 + "(" + simulator.showAward().get(5).getProbability() * 100 + ")%");
        System.out.println("七等奖" + award7Count + "注，中奖概率" + probability7 + "(" + simulator.showAward().get(6).getProbability() * 100 + ")%");
        System.out.println("八等奖" + award8Count + "注，中奖概率" + probability8 + "(" + simulator.showAward().get(7).getProbability() * 100 + ")%");
        System.out.println("九等奖" + award9Count + "注，中奖概率" + probability9 + "(" + simulator.showAward().get(8).getProbability() * 100 + ")%");
        System.out.println("共花费" + cost + "元，奖金" + bonus + "元，总获利" + (bonus - cost) + "元，回报率" + returnRate + "%");
    }
}
