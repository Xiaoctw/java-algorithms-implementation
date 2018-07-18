package com.crossoverjie.algorithm;

public class DynamicProgramming {
    /**
     *  给定一个m * n的二维数组，
     *  寻找一条路径，从左上角到达右下角，并且使得
     *  路径上的数字加起来的和最小
     *  自底向上解决问题
     *  用一个二维数组解决问题，
     * dp[i][j]代表的是到达i，j处所需最短距离，可以得到表达式：
     * dp[i][j]=grid[0][0] (i==j==0)
     *          dp[i-1][0]+grid[i][0](i>0&&j==0)
     *          dp[0][j]=dp[0][j-1]+grid[0][j](j>0&&i==0)
     *          dp[i][j]=Math.min(dp[i-1][j],dp[i][j-1])+grid[i][j](i>0&&j>0)
     * @param grid 一个二维数组
     * @return  所需最小值
     */
    public int minPathSum(int[][] grid){
        int m=grid.length;
        int n=grid[0].length;
        if (m==0&&n==0){
            return 0;
        }
        int[][] dp=new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i==0&&j==0){
                    dp[0][0]=grid[0][0];
                }else if (j==0){
                    dp[i][0]=dp[i-1][0]+grid[i][0];
                }else if (i==0){
                    dp[0][j]=dp[0][j-1]+grid[0][j];
                }else {
                    dp[i][j]=Math.min(dp[i-1][j],dp[i][j-1])+grid[i][j];
                }
            }
        }
        return dp[m-1][n-1];
    }
    /**
     * 给定一个m * n的二维数组，
     * 寻找一条路径，从左上角到达右下角，并且使得
     * 路径上的数字加起来的和最小
     * 这个函数用一个一维数组解决问题，
     * 首先假设二位数组只有一列的情况：
     * 此时dp[i]=grid[i][0]+dp[i-1]；唯一的路径为从顶端到达底端
     * 之后对二维数组进行“拓展”
     * 考虑到第j列时，对于第i行，到达i，j处可能分为两种情况，
     * 由本行到达这个点或者是从上一行到达此点。因此可以得到式子：
     * dp[i]=Math.min(dp[i],dp[i-1])+grid[i][j];
     * @param grid 二维数组
     * @return 最小和
     */
    public int minPathSum1(int[][] grid) {
        int m=grid.length;
        int n=grid[0].length;
        if (m==1&&n==1){
            return grid[0][0];
        }
        int[] dp=new int[m];
        dp[0]=grid[0][0];
        for (int i = 1; i <m ; i++) {
            dp[i]=grid[i][0]+dp[i-1];
        }
        for (int j = 1; j < n; j++) {
            for (int i = 0; i <m ; i++) {
                if (i==0){
                    dp[i]+=grid[i][j];
                }else {
                    dp[i]=Math.min(dp[i],dp[i-1])+grid[i][j];
                }
            }
        }
        return dp[m-1];
    }

    /**
     * 有两个字符串s1和s2，每次从这两个字符串中取出一个字符，
     * 直到两个字符串均取完，这样能够构成一个新的字符串，问
     * s3是否可以由s1和s2构成
     * 同样利用二位数组自底向上解决问题
     * 利用数组dp保存s1中第i位和s2中第j位能否组成合法字符串
     * 得到表达式：
     *   dp[i][j]=true(i==0&&j==0)
     *            dp[i-1][0]&&(s1.charAt(i-1)==s3.charAt(i-1))(i>0&&j==0)
     *            dp[0][j-1]&&(s2.charAt(j-1)==s3.charAt(j-1))(i==0&&j>0)
     *            (dp[i-1][j]&&(s1.charAt(i-1)==s3.charAt(i+j-1)))||(dp[i][j-1]&&(s2.charAt(j-1)==s3.charAt(i+j-1)))(i>0&&j>0)
     * @param s1 第一个字符串
     * @param s2 第二个字符串
     * @param s3 组合而成的字符串
     * @return 是否合理
     */
    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1.length()+s2.length()!=s3.length()){
            return false;
        }
        int len1=s1.length();
        int len2=s2.length();
        boolean[][] dp=new boolean[len1+1][len2+1];
        dp[0][0]=true;
        for (int i = 1; i <len1+1 ; i++) {
            dp[i][0]=dp[i-1][0]&&(s1.charAt(i-1)==s3.charAt(i-1));
        }
        for (int i = 1; i <len2+1 ; i++) {
            dp[0][i]=dp[0][i-1]&&(s2.charAt(i-1)==s3.charAt(i-1));
        }
        for (int i = 1; i < len1+1 ; i++) {
            for (int j = 1; j < len2+1; j++) {
                dp[i][j]=(dp[i-1][j]&&(s1.charAt(i-1)==s3.charAt(i+j-1)))||(dp[i][j-1]&&(s2.charAt(j-1)==s3.charAt(i+j-1)));
            }
        }
        return dp[len1][len2];
    }
}
