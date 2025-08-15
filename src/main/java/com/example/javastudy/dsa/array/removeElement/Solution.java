package com.example.javastudy.dsa.array.removeElement;

/**
 * 同向法（稳定/保序）：
 *  每遇到非 val 就写到 slow，slow++。写入次数≈非 val 的数量。
 * @author zhangzhiwen30
 * @date 2025-08-14 15:09:17
 */

class Solution {
    public int removeElement(int[] nums, int val) {
        int slow = 0;
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != val) {
                nums[slow++] = nums[fast];
            }
        }
        return slow; // k
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = {0, 1, 2, 2, 3, 0, 4, 2};
        int val = 2;
        int k = solution.removeElement(nums, val);
        System.out.println("新数组长度: " + k);
        System.out.print("新数组内容: ");
        for (int i = 0; i < k; i++) {
            System.out.print(nums[i] + " ");
        }
        System.out.println();
        //完整输出:
        System.out.print("完整数组内容: ");
        for (int num : nums) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
