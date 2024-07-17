import java.util.Random;

public class KthLargestElement {
    public int findKthLargest(int[] nums, int k) {
        // Adjust k to be 1-based index for the kth largest element
        int targetIndex = nums.length - k;
        return quickSelect(nums, 0, nums.length - 1, targetIndex);
    }

    private int quickSelect(int[] nums, int left, int right, int targetIndex) {
        if (left == right) {
            return nums[left];
        }

        Random random = new Random();
        int pivotIndex = left + random.nextInt(right - left + 1);
        pivotIndex = partition(nums, left, right, pivotIndex);

        if (pivotIndex == targetIndex) {
            return nums[pivotIndex];
        } else if (pivotIndex < targetIndex) {
            return quickSelect(nums, pivotIndex + 1, right, targetIndex);
        } else {
            return quickSelect(nums, left, pivotIndex - 1, targetIndex);
        }
    }

    private int partition(int[] nums, int left, int right, int pivotIndex) {
        int pivotValue = nums[pivotIndex];
        swap(nums, pivotIndex, right);
        int storeIndex = left;

        for (int i = left; i <= right - 1; i++) {
            if (nums[i] < pivotValue) {
                swap(nums, storeIndex, i);
                storeIndex++;
            }
        }

        swap(nums, storeIndex, right);
        return storeIndex;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        KthLargestElement solution = new KthLargestElement();

        // Example usage
        int[] nums = { 3, 2, 1, 5, 6, 4 };
        int k = 2;
        System.out.println("The " + k + "th largest element is: " + solution.findKthLargest(nums, k)); // Output: 5

        int[] nums2 = { 3, 2, 3, 1, 2, 4, 5, 5, 6 };
        k = 4;
        System.out.println("The " + k + "th largest element is: " + solution.findKthLargest(nums2, k)); // Output: 4
    }
}
