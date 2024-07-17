public class MaxWaterContainer {
    public int maxArea(int[] height) {
        int maxArea = 0;
        int left = 0;
        int right = height.length - 1;

        while (left < right) {
            int minHeight = Math.min(height[left], height[right]);
            int area = minHeight * (right - left);
            maxArea = Math.max(maxArea, area);

            // Move the pointer pointing to the shorter line
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }

    public static void main(String[] args) {
        MaxWaterContainer solution = new MaxWaterContainer();

        // Example usage
        int[] heights1 = { 1, 8, 6, 2, 5, 4, 8, 3, 7 };
        System.out.println(solution.maxArea(heights1)); // Output: 49

        int[] heights2 = { 1, 1 };
        System.out.println(solution.maxArea(heights2)); // Output: 1

        int[] heights3 = { 4, 3, 2, 1, 4 };
        System.out.println(solution.maxArea(heights3)); // Output: 16

        int[] heights4 = { 1, 2, 1 };
        System.out.println(solution.maxArea(heights4)); // Output: 2
    }
}
