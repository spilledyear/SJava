package com.hand.sxy.sort;

public class TestSort {
    public static void main(String[] args) {
        TestSort testSort = new TestSort();
    }


    /**
     * 冒泡排序
     * 通过相邻两两比较的方式，每次将最小的数放到前面
     * O(n^2)
     *
     * @param arr
     */
    public void bubbleSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = arr.length - 1; j > i; j--) {
                if (arr[j] < arr[j - 1]) {
                    int temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }


    /**
     * 选择排序
     * 与冒泡类似，但过程不同。冒泡是相邻两两比较找到最小数，选择是通过一次遍历找到一个最小数
     * O(n^2)
     *
     * @param arr
     */
    public void selectSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        int index = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            index = i;

            // 每次内循环保证找到一个最小数，从i+1开始比较
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[index]) {
                    index = j;
                }
            }

            // index != i 说明存在比 arr[i] 更小的值， 交换
            if (index != i) {
                int temp = arr[i];
                arr[i] = arr[index];
                arr[index] = temp;
            }
        }
    }


    /**
     * 插入排序
     * 从第二个数开始，每插入一个数据，都保证前面的数据是有序的。比如插入第二数的时候，在前两个数中找到合适位置；插入第三个数的时候，在在前三个数中找到合适位置
     * O(n^2)
     *
     * @param arr
     */
    public static void insertSort(int[] arr) {
        if (arr == null || arr.length == 0)
            return;

        for (int i = 1; i < arr.length; i++) {

            int j = i;
            int target = arr[i]; //待插入的

            //后移，因为是从后面往前面找，用前面的元素覆盖后面的元素，不会产生影响，最后再用target覆盖合适位置的值
            while (j > 0 && target < arr[j - 1]) {
                arr[j] = arr[j - 1];
                j--;
            }

            //插入
            arr[j] = target;
        }
    }


    /**
     * http://blog.51cto.com/flyingcat2013/1281614
     * 1.选定一个合适的值（理想情况中值最好，但实现中一般使用数组第一个值）,称为“枢轴”(pivot)。
     * 2.基于这个值，将数组分为两部分，较小的分在左边，较大的分在右边。
     * 3.可以肯定，如此一轮下来，这个枢轴的位置一定在最终位置上。
     * 4.对两个子数组分别重复上述过程，直到每个数组只有一个元素。
     * 5.排序完成。
     * 平均复杂度为O(n㏒n)，平均性能最好的，但是它不是稳定的，输入数据基本有序甚至完全有序的时候，这算法退化为冒泡排序，不再是O(n㏒n)，而是O(n^2)了
     *
     * @param arr
     */
    public void quickSort(int[] arr) {
        qsort(arr, 0, arr.length - 1);
    }

    private void qsort(int[] arr, int low, int high) {
        if (low < high) {
            int pivot = partition(arr, low, high);                //将数组分为两部分
            qsort(arr, low, pivot - 1);                     //递归排序左子数组
            qsort(arr, pivot + 1, high);                     //递归排序右子数组
        }
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[low];     //枢轴记录
        while (low < high) {
            while (low < high && arr[high] >= pivot) {
                --high;
            }
            arr[low] = arr[high];             //交换比枢轴小的记录到左端
            while (low < high && arr[low] <= pivot) {
                ++low;
            }
            arr[high] = arr[low];           //交换比枢轴小的记录到右端
        }
        //扫描完成，枢轴到位
        arr[low] = pivot;
        //返回的是枢轴的位置
        return low;
    }


}
