package com.crossoverjie.algorithm;
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}
public class DivideAndConquer {
    /**
     * 找到一个数组中的主元素，主元素指的是在一个数组中，元素的个数大于等于数组总元素个数一半的元素
     * @param nums 一个数组
     * @return 数组中的主元素
     */
    public int majorityElement(int[] nums) {
        return findMajority(nums,0,nums.length-1);
    }
    private int findMajority(int[] nums,int lo,int hi){
        if (lo==hi){
            return nums[lo];
        }
        int mid=(lo+hi)/2;
        int left=findMajority(nums,lo,mid);
        int right=findMajority(nums,mid+1,hi);
        int countLeft=0;
        int countRight=0;
        for (int i = lo; i <=hi ; i++) {
            if (nums[i]==left){
                countLeft++;
            }else if (nums[i]==right){
                countRight++;
            }
        }
        if (countLeft>countRight){
            return left;
        }
        return right;
    }

    /**
     * 合并多个链表，用分治法实现
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        int len=lists.length;
        return Merge(lists,0,len-1);
    }
    private ListNode Merge(ListNode[] lists,int lo,int hi){
        if (hi<lo){
            return null;
        }
        else if (lo==hi){
            return lists[lo];
        }
        int mid=(lo+hi)/2;
        ListNode l1=Merge(lists,lo,mid);
        ListNode l2=Merge(lists,mid+1,hi);
        return merge2Lists(l1,l2);
    }
    private ListNode merge2Lists(ListNode list1,ListNode list2){
        ListNode tem1=list1;
        ListNode tem2=list2;
        ListNode head=new ListNode(0);
        ListNode tem=head;
        while (tem1!=null&&tem2!=null){
            if (tem1.val<tem2.val){
                tem.next=tem1;
                tem1=tem1.next;
            }else {
                tem.next=tem2;
                tem2=tem2.next;
            }
            //  tem.next=tem1.val>tem2.val?tem1:tem2;
            tem=tem.next;
        }
        while (tem1!=null){
            tem.next=tem1;
            tem=tem.next;
            tem1=tem1.next;
        }
        while (tem2!=null){
            tem.next=tem2;
            tem=tem.next;
            tem2=tem2.next;
        }
        return head.next;
    }

    /**
     * 寻找有着最大和的子字符串
     * @param nums 一个数组
     * @return 其子序列的最大和
     */
    public int maxSubArray(int[] nums) {
        int length=nums.length;
        if (length==0) return 0;
        return find(nums,0,length-1);
    }
    public int find(int[] nums,int begin,int end){
        if (begin==end){
            return nums[begin];
        }
        int mid=(begin+end)/2;
        int a=find(nums,begin,mid);
        int b=find(nums,mid+1,end);
        int c=0;
        int d=0;
        int max1=Integer.MIN_VALUE;
        int max2=Integer.MIN_VALUE;
        for (int i = mid; i >=0 ; --i) {
            c+=nums[i];
            if (c>max1){
                max1=c;
            }
        }
        for (int i = mid+1; i <=end ; i++) {
            d+=nums[i];
            if (d>max2){
                max2=d;
            }
        }
        int e=max1+max2;
        if (a>b){
            if (e>a){
                return e;
            }
            return a;
        }else {
            if (e>b){
                return e;
            }
            return b;
        }
    }

    /**
     * 本题来自leetcode
     * 题干：Given n balloons, indexed from 0 to n-1.
     * Each balloon is painted with a number on it represented by array nums. You are asked to burst all the balloons.
     * If the you burst balloon i you will get nums[left] * nums[i] * nums[right] coins.
     * Here left and right are adjacent indices of i. After the burst, the left and right then becomes adjacent.
     * @param nums
     * @return
     */
    public int maxCoins(int[] nums) {
        //这道题的核心思想在于找到最后一个扎破的气球是哪一个，根据这个气球把问题分解为左右两部分。
        //带备忘地自顶向下解决问题，带有一点动态规划的思想
        int n=nums.length;
        int[][] dp=new int[n+1][n+1];
        int[] A=new int[n+2];
        A[0]=1;
        A[n+1]=1;
        for (int i = 1; i <n+1 ; i++) {
            A[i]=nums[i-1];
        }
        return findMaxCoins(A,dp,1,n);
    }
    private int findMaxCoins(int[] nums,int[][] dp,int i,int j){
        if (i>j){
            return 0;
        }
        if (dp[i][j]!=0){
            return dp[i][j];
        }
        int max=0;
        for (int k = i; k <=j ; k++) {
            int left=findMaxCoins(nums,dp,i,k-1);//递归的思想，已经找到了左半部分的最大值
            int right=findMaxCoins(nums,dp,k+1,j);//右半部分的最大值
            max=Math.max(max,left+right+nums[i-1]*nums[k]*nums[j+1]);
        }
        dp[i][j]=max;
        return max;
    }
}
