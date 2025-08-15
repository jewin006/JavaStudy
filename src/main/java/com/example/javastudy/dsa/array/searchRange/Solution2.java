package com.example.javastudy.dsa.array.searchRange;

class Solution2 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        int[] nums = {5, 7, 7, 8, 8, 10};
        int target = 8;
        int[] range = solution.searchRange(nums, target);
        System.out.println("Range: [" + range[0] + ", " + range[1] + "]"); // 输出: Range: [3, 4]
    }

    public int[] searchRange(int[] nums, int target) {
        int rightBorder = getRightBorder(nums, target);
        if (rightBorder == -1 || nums[rightBorder] != target) {
            return new int[]{-1, -1}; // 没有找到target
        }
        int leftBorder = getLeftBorder(nums, target);
        return new int[]{leftBorder,rightBorder};
    }

    int getRightBorder(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int rightBorder = -1; // 记录一下rightBorder没有被赋值的情况
        while (left <= right) {
            int middle = left + ((right - left) >>> 1);
            if (nums[middle] <= target) {
                rightBorder = middle; // 寻找右边界，nums[middle] == target的时候更新left
                left = middle + 1;
            } else {
                right = middle - 1;

            }
        }
        return rightBorder;
    }

    int getLeftBorder(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int leftBorder = -1; // 记录一下leftBorder没有被赋值的情况
        while (left <= right) {
            int middle = left + ((right - left) >>> 1);
            if (nums[middle] >= target) { // 寻找左边界，nums[middle] == target的时候更新right
                leftBorder = middle;
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return leftBorder;
    }
}