package com.example.javastudy.dsa.array.searchRange;

/**
 * 左闭右闭
 */
class Solution1 {

    public int[] searchRange(int[] nums, int target) {
        // 左边界：第一个 >= target
        int l = 0;
        int r = nums.length - 1;
        while (l <= r) {
            int mid = l + ((r - l) >>> 1); // 左中位
            if (nums[mid] >= target)
                r = mid - 1; // 相等也压右侧
            else
                l = mid + 1;
        }
        int start = r + 1 ;
        if (start == nums.length || nums[start] != target)
            return new int[] { -1, -1 };

        // 右边界：最后一个 <= target（取右中位避免卡死）
        l = start;
        r = nums.length - 1;
        while (l <= r) {
            int mid = l + ((r - l) >>> 1);
            if (nums[mid] <= target)
                l = mid + 1;
            else
                r = mid - 1;
        }
        int end = l - 1;
        return new int[]{start,end};
    }

    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        int[] nums = {5,7,7,8,8,10};
        int target = 8;
        int[] range = solution.searchRange(nums, target);
        System.out.println("Range: [" + range[0] + ", " + range[1] + "]"); // 输出: Range: [3, 4]
    }
}