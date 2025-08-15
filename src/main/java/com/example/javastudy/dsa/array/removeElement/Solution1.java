package com.example.javastudy.dsa.array.removeElement;

/**
 * 换尾法
 * 换尾法（不保序/写入少）：
 * 遇 val 与 right 交换并收缩尾部；
 * 只在遇到 val 时写入。val 稀少时更优。
 */
class Solution1 {
    public int removeElement(int[] nums, int val) {
        int i = 0, right = nums.length - 1;
        while (i <= right) {
            if (nums[i] == val) {
                nums[i] = nums[right];
                right--;               // 收缩有效尾
                // 注意：此处不 i++，因为换来的元素还未检查
            } else {
                i++;
            }
        }
        return right + 1;              // k
    }

    public static void main(String[] args) {
        Solution1 solution1 = new Solution1();
        int[] nums = {0,1,2,2,3,0,4,2};
        int val = 2;
        int k = solution1.removeElement(nums, val);
        System.out.println("新数组长度: " + k);
        System.out.print("新数组内容: ");
        for (int i = 0; i < k; i++) {
            System.out.print(nums[i] + " ");
        }
        System.out.println();
    }
}
