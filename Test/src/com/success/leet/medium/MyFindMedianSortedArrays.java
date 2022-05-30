package com.success.leet.medium;

public class MyFindMedianSortedArrays {

  public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    int merged[] = new int[nums1.length + nums2.length];
    int index = 0;
    int i = 0;
    int j = 0;
    while (i < nums1.length && j < nums2.length) {
      if (nums1[i] < nums2[j]) {
        merged[index++] = nums1[i];
        i++;
      } else if (nums1[i] > nums2[j]) {
        merged[index++] = nums2[j];
        j++;
      } else {
        merged[index++] = nums1[i];
        merged[index++] = nums2[j];
        i++;
        j++;
      }
    }

    while (i < nums1.length) {
      merged[index++] = nums1[i++];
    }

    while (j < nums2.length) {
      merged[index++] = nums2[j++];
    }

    double median;

    int length = merged.length;

    if (length % 2 == 0) {
      // even number length
      int element1 = merged[(length / 2) - 1];
      int element2 = merged[(length / 2)];
      median = (double) (element1 + element2) / 2;
    } else {
      // odd number length
      median = merged[length / 2];
    }
    return median;
  }

  public static void main(String[] args) {
    double median = 0;
    /*median =
    new MyFindMedianSortedArrays().findMedianSortedArrays(new int[] {0}, new int[] {});*/
    //    System.out.println(median);

    //    median = new MyFindMedianSortedArrays().findMedianSortedArrays(new int[] {1}, new int[]
    // {2, 3});
    //    System.out.println(median);

    median =
        new MyFindMedianSortedArrays()
            .findMedianSortedArrays(new int[] {0, 0, 0, 0, 0}, new int[] {-1, 0, 0, 0, 0, 0, 1});
    System.out.println(median);

    median = new MyFindMedianSortedArrays().findMedianSortedArrays(new int[] {1}, new int[] {1, 3});
    System.out.println(median);
  }
}
