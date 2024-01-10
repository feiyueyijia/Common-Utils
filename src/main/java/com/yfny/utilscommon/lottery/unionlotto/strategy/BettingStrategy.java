package com.yfny.utilscommon.lottery.unionlotto.strategy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 投注策略
 * Author JiSong_ZHOU
 * Date  2023/8/26
 */
public class BettingStrategy {

    public boolean applyRedStrategy(int[] nums) {
        return notUpperHalves(4, nums) || notLowerHalves(1, nums) || notConsecutive(nums)
                || notAllOdd(1, nums) || notAllEven(1, nums) || sumRange(65, 142, nums)
                || spanRange(15, 32, nums);
    }

    public boolean applyBlueStrategy(int[] nums) {
        return false;
    }

    /**
     * 单式红区不选6连号
     */
    private boolean notConsecutive(int[] nums) {
        return isConsecutive(nums);
    }

    /**
     * 判断是否连续
     */
    private boolean isConsecutive(int[] nums) {
        List<Integer> list = Arrays.asList(Arrays.stream(nums).boxed().toArray(Integer[]::new));
        // 判断列表是否为空或只包含一个元素
        if (list.isEmpty() || list.size() == 1) {
            return true;
        }
        // 对列表进行排序
        List<Integer> sortedList = list.stream().sorted().collect(Collectors.toList());
        // 使用IntStream创建一段连续的整数序列
        IntStream range = IntStream.range(sortedList.get(0), sortedList.get(sortedList.size() - 1) + 1);
        // 将排序后的列表中的元素与生成的连续整数序列逐个比较
        return range.allMatch(sortedList::contains);
    }

    /**
     * 单式红区上半区控制
     */
    private boolean notUpperHalves(int index, int[] nums) {
        if (index < 0 || index > 5) {
            index = 5;
        }
        return nums[index] < 17;
    }

    /**
     * 单式红区下半区控制
     */
    private boolean notLowerHalves(int index, int[] nums) {
        if (index < 0 || index > 5) {
            index = 0;
        }
        return nums[index] > 17;
    }

    /**
     * 单式红区奇数控制
     */
    private boolean notAllOdd(int refNum, int[] nums) {
        boolean result = true;
        int count = 0;
        for (int num : nums) {
            boolean isOdd = isOdd(num);
            if (!isOdd) {
                count++;
                if (count >= refNum) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 单式红区偶数控制
     */
    private boolean notAllEven(int refNum, int[] nums) {
        boolean result = true;
        int count = 0;
        for (int num : nums) {
            boolean isOdd = isOdd(num);
            if (isOdd) {
                count++;
                if (count >= refNum) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 判断是否为奇数
     */
    private boolean isOdd(int num) {
        return (num & 1) == 1;
    }

    /**
     * 单式红区和值控制
     */
    public boolean sumRange(int min, int max, int[] nums) {
        boolean result = true;
        int sum = Arrays.stream(nums).sum();
        if (sum > min || sum < max) {
            result = false;
        }
        return result;
    }

    /**
     * 单式红区跨度控制
     */
    public boolean spanRange(int min, int max, int[] nums) {
        boolean result = true;
        int span = nums[5] - nums[0];
        if (span > min || span < max) {
            result = false;
        }
        return result;
    }
}
