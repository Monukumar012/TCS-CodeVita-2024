import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Round2{

    
    //First
    static boolean isSafe(int i, int j, int n, int m){
        return i>=0 && i<n && j>=0 & j<m;
    }
    static void solve1(){
        int n=sc.nextInt(),m=sc.nextInt();

        int mat[][]=sc.nextIntMatrix(n, m);
        boolean vis[][]=new boolean[n][m];

        int srcI=sc.nextInt(),srcJ=sc.nextInt();
        int destI=sc.nextInt(),destJ=sc.nextInt();
        int x=sc.nextInt(),y=sc.nextInt();


        List<int[]> dir=new ArrayList<>();
        dir.add(new int[]{x,y});
        dir.add(new int[]{-x,-y});
        dir.add(new int[]{-y,x});
        dir.add(new int[]{y,-x});


        Queue<int[]> pq=new PriorityQueue<>((a,b)->a[2]-b[2]);
        pq.add(new int[]{srcI,srcJ,0});
        vis[srcI][srcJ]=true;
        while(!pq.isEmpty()){
            int i=pq.peek()[0],j=pq.peek()[1],step=pq.poll()[2];
            if(i==destI && j==destJ){
                System.out.print(step);
                return;
            }

            for(int d[]:dir){
                int a=d[0]+i,b=j+d[1];
                if(isSafe(a, b, n, m) && !vis[a][b] && mat[a][b]==0){
                    pq.add(new int[]{a,b,1+step});
                    vis[a][b]=true;
                }
            }
        }
        System.out.print(-1);
    }

    // Third
    static void solve2(){
        int n=sc.nextInt(),m=sc.nextInt();

        int mat[][]=sc.nextIntMatrix(n, m);
        
        int srcI=sc.nextInt(),srcJ=sc.nextInt();
        int destI=sc.nextInt(),destJ=sc.nextInt();
        --srcI;--srcJ;--destI;--destJ;
        
        
        int dis[][]=new int[n+1][m+1];
        for(int d[]:dis)Arrays.fill(d,(int)1e9);
        int dir[][]={{0,1},{1,0}};


        Queue<int[]> pq=new LinkedList<>();
        pq.add(new int[]{srcI,srcJ,mat[srcI][srcJ]});
        dis[srcI][srcJ]=mat[srcI][srcJ];

        while(!pq.isEmpty()){
            int i=pq.peek()[0],j=pq.peek()[1],step=pq.poll()[2];

            for(int d[]:dir){
                int a=d[0]+i,b=j+d[1];
                if(isSafe(a, b, n, m) && dis[a][b]>step+mat[a][b]){
                    dis[a][b]=step+mat[a][b];
                    pq.add(new int[]{a,b,dis[a][b]});
                }
            }
        }

        if(dis[destI][destJ]>=(int)1e9)System.out.print("-1");
        else System.out.print(dis[destI][destJ]);

    }


    // Fourth
    static void solve4(){
        int l=sc.nextInt(),n=5;
        int arr[]=sc.nextIntArray(n);
        Arrays.sort(arr);

        int[] dp = new int[l+1];
        Arrays.fill(dp, -1);
        dp[0] = 0;

        for (int h = 1; h <= l; h++) {
            for (int e : arr) {
                if (h-e >=0 && dp[h-e] != -1) 
                    dp[h] = Math.max(dp[h], dp[h-e] + 1);
                
            }
        }

        if(dp[l] == -1) {
            System.out.print("Impossible");
            return;
        }

        Map<Integer, Integer> map = new HashMap<>();
        while (l > 0) {
            for (int e : arr) {
                if (l-e >= 0 && dp[l] == dp[l-e] + 1) {
                    map.put(e, map.getOrDefault(e, 0) + 1);
                    l -= e;
                    break;
                }
            }
        }

        List<int[]> ans=new ArrayList<>();
        for(int k:map.keySet()){
            ans.add(new int[]{k,map.get(k)});
        }
        Collections.sort(ans,(a,b)->a[0]==b[0] ? a[0]-b[0] : b[1]-a[1]);

        int m=ans.size();
        for (int i = 0; i < m; i++) {
            System.out.print(ans.get(i)[0]);
            if (i < m-1) {
                System.out.print(" ");
            }
        }

    }

    // Fifth
    static double xirr(double[] cashFlows, int n, Date[] dates) {
        final double epsilon = 1e-6; 
        double guess = 0.1,x0 = 0.0, x1;

        do {
            x1 = guess - helper(cashFlows, dates, guess) / derivative(cashFlows, n, dates, guess);
            if (Math.abs(x1 - guess) < epsilon) {
                return x1;
            }
            guess = x1;
        } while (Math.abs(x1 - x0) >= epsilon);

        return x1;
    }

    static double helper(double[] cash, Date[] dates, double rate) {
        double result = 0.0;
        for (int i = 0; i < cash.length; i++) {
            double days = (dates[i].getTime() - dates[0].getTime()) / (1000 * 60 * 60 * 24.0);
            result += cash[i] / Math.pow(1 + rate, days / 365.0);
        }
        return result;
    }

    static double derivative(double[] cash, int n, Date[] dates, double rate) {
        double ans = 0.0;
        for (int i = 0; i < n; i++) {
            double days = (dates[i].getTime() - dates[0].getTime()) / (1000*60*60*24.0);
            ans -= (days / 365.0) * cash[i] / Math.pow(1 + rate, (days/365.0) + 1);
        }
        return ans;
    }

    static void solve3(){
        int n = Integer.parseInt(sc.nextLine());

        double[] cash = new double[n];
        Date[] dates = new Date[n];

        for (int i = 0; i < n; i++) {
            String[] s = sc.nextLine().split(" ");
            cash[i] = Double.parseDouble(s[0]);
            try {
                dates[i] = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).parse(s[1]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        double ans = xirr(cash, n, dates);
        System.out.printf("%.2f", ans * 100);
    }



    public static void main(String[] args) {
        // int t=sc.nextInt();
        // while(t-->0){
        //     out.res.setLength(0);
        //     solve();
        // }
        // solve();
    }







// Input
    // public static Scanner sc = new Scanner(System.in);
    static Mix out = new Mix();
    static Scanner sc = new Scanner();

    static class Scanner {
        BufferedReader br;
        StringTokenizer st;
 
        public Scanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }
 
        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }
 
        int nextInt() {
            return Integer.parseInt(next());
        }
 
        long nextLong() {
            return Long.parseLong(next());
        }
 
        double nextDouble() {
            return Double.parseDouble(next());
        }
 
        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }

        int[] nextIntArray(int n){
            int arr[]=new int[n];
            for(int i=0;i<n;i++){
                arr[i]=nextInt();
            }
            return arr;
        }

        long[] nextLongArray(int n){
            long arr[]=new long[n];
            for(int i=0;i<n;i++){
                arr[i]=nextLong();
            }
            return arr;
        }

        double[] nextDoubleArray(int n){
            double arr[]=new double[n];
            for(int i=0;i<n;i++){
                arr[i]=nextDouble();
            }
            return arr;
        }

        char[] nextToCharArray(){
            return next().toCharArray();
        }

        char[] nextCharArray(int n){
            char[] arr=new char[n];
            for(int i=0;i<n;i++){
                arr[i]=next().charAt(0);
            }
            return arr;
        }

        int [][] nextIntMatrix(int n,int m){
            int arr[][]=new int[n][m];
            for(int i=0;i<n;i++){
                for(int j=0;j<m;j++){
                    arr[i][j]=nextInt();
                }
            }
            return arr;
        }

        long [][] nextLongMatrix(int n,int m){
            long arr[][]=new long[n][m];
            for(int i=0;i<n;i++){
                for(int j=0;j<m;j++){
                    arr[i][j]=nextLong();
                }
            }
            return arr;
        }

        double [][] nextDobuleMatrix(int n,int m){
            double arr[][]=new double[n][m];
            for(int i=0;i<n;i++){
                for(int j=0;j<m;j++){
                    arr[i][j]=nextDouble();
                }
            }
            return arr;
        }

        char [][] nextCharMatrix(int n,int m){
            char arr[][]=new char[n][m];
            for(int i=0;i<n;i++){
                for(int j=0;j<m;j++){
                    arr[i][j]=next().charAt(0);
                }
            }
            return arr;
        }

    }


    static class Mix{
        
        public StringBuilder res=new StringBuilder();
        public void yes(){
            System.out.println("YES");
        }
        public void no(){
            System.out.println("NO");
        }

        public void nline(){
            System.out.println();
        }

        public void print(String x){
            res.append(x);
        }

        public void print(int x){
            res.append(x);
        }

        public void print(long x){
            res.append(x);
        }

        public void print(char x){
            res.append(x);
        }

        public void println(String x){
            res.append(x).append("\n");
        }

        public void println(int x){
            res.append(x).append("\n");
        }

        public void println(long x){
            res.append(x).append("\n");
        }

        public void println(char x){
            res.append(x).append("\n");
        }

        public void printArray(int arr[]){
            for(int e: arr)res.append(e+" ");
            System.out.println(res);
        }

        public void printArray(long arr[]){
            for(long e: arr)res.append(e+" ");

            System.out.println(res);
        }

        public void printMatrix(int arr[][]){
            
        }

        public int max(int arr[]){
            int max = Integer.MIN_VALUE;
            for(int e: arr)max=Math.max(max,e);
            return max;
        }

        public int min(int arr[]){
            int min = Integer.MAX_VALUE;
            for(int e: arr)min=Math.min(min,e);
            return min;
        }

        public long sum(int arr[]){
            long sum=0;
            for(int e:arr)sum+=e;
            return sum;
        }

        public long sum(long arr[]){
            long sum=0;
            for(long e:arr)sum+=e;
            return sum;
        }

        public boolean isSorted(int arr[], int i ,int j){
            int n=arr.length;
            while(i!=j){
                if(arr[i%n]>arr[(i+1)%n])return false;
                i++;i%=n;
            }
            return true;
        }

        public void reverse(int arr[], int i, int j){
            while(i<=j){
                int t=arr[i];
                arr[i]=arr[j];
                arr[j]=t;
                i++;j--;
            }
        }

        public void reverse(char arr[], int i, int j){
            while(i<j){
                char t=arr[i];
                arr[i]=arr[j];
                arr[j]=t;
                i++;j--;
            }
        }

        public void reverse(long arr[], int i, int j){
            while(i<j){
                long t=arr[i];
                arr[i]=arr[j];
                arr[j]=t;
                i++;j--;
            }
        }

        public int[] copy(int arr[]){
            int n=arr.length;
            int copy[]=new int[n];
            for(int i=0;i<n;i++)copy[i]=arr[i];
            return copy;
        }

        public long[] copy(long arr[]){
            int n=arr.length;
            long copy[]=new long[n];
            for(int i=0;i<n;i++)copy[i]=arr[i];
            return copy;
        }

        public char[] copy(char arr[]){
            int n=arr.length;
            char copy[]=new char[n];
            for(int i=0;i<n;i++)copy[i]=arr[i];
            return copy;
        }

        public boolean isPalindrome(String s, int i, int j){
            while(i<j){
                if(s.charAt(i++)!=s.charAt(j--))return false;
            }
            return true;
        }

        public boolean isPalindrome(char s[], int i, int j){
            while(i<j){
                if(s[i++]!=s[j--])return false;
            }
            return true;
        }

        public boolean isVowel(char c){
            return "aeiouAEIOU".indexOf(c)!=-1;
        }

        public boolean isConsonant(char c){
            return !isVowel(c);
        }


        //Lower Bound - Smallest Index i a[i]>=x
        public int lowerBound(List<Integer> arr, int n, int x){
            
            int l=0, h=n-1;
            while(l<=h){
                int m=l+(h-l)/2;
                if(arr.get(m)>=x){
                    h=m-1;
                }
                else l=m+1;
            }
            return l;
        }
        public int lowerBound(int arr[], int n, int x){
            
            int l=0, h=n-1;
            while(l<=h){
                int m=l+(h-l)/2;
                if(arr[m]>=x){
                    h=m-1;
                }
                else l=m+1;
            }
            return l;
        }


        //Upper Bound - Largest Index i a[i]<=x

        public int upperBound(List<Integer> arr, int n, int x){
            
            int l=0, h=n-1;
            while(l<=h){
                int m=l+(h-l)/2;
                if(arr.get(m)<=x){
                    l=m+1;
                }
                else h=m-1;
            }

            return h;
        }

        public int upperBound(int arr[], int n, int x){
            
            int l=0, h=n-1;
            while(l<=h){
                int m=l+(h-l)/2;
                if(arr[m]<=x){
                    l=m+1;
                }
                else h=m-1;
            }

            return h;
        }


        // Template
        // TC - N(log(logN)) -> ~O(N)
        public boolean[] sieve(int n){
            boolean primes[]=new boolean[n+1];
            
            // Mark all primes true
            for(int i=2;i<=n;i++)primes[i]=true;

            // Mark false for multiple of primes
            for(int p=2;p<=n;p++){
                if(primes[p]){
                    for(int i=p*p;i<=n;i+=p){
                        primes[i]=false;
                    }
                }
            }
            return primes;
        }

        public boolean isPrime(int n){
            if(n<=1)return true;

            for(int i=2;i*i<=n;i++){
                if(n%i==0)return false;
            }
            return true;
        }

        // Calulate gcd of a,b
        public int gcd(int a,int b){
            if(a==0)return b;
            return gcd(b%a, a);
        }

        public long gcd(long a,long b){
            if(a==0)return b;
            return gcd(b%a, a);
        }

        // get All Factors/Divisors of N 
        public List<Integer> getFactors(int n){
            List<Integer> factor = new ArrayList<>();
            
            for(int i=1;i*i<=n;i++){
                if(n%i==0){
                    factor.add(i);
                    if(i!=n/i)factor.add(n/i);
                }
            }
            return factor;
        }

        // Count All Factors/Divisors of N 
        public int countFactors(int n){
            int cnt=0;

            for(int i=1;i*i<=n;i++){
                if(n%i==0){
                    cnt++;
                    if(i!=n/i)cnt++;
                }
            }
            return cnt;
        }

        // Decimal to binary
        public String DtoB(int n){
            StringBuilder sb=new StringBuilder();
            while(n>0){
                sb.append(n%2);
                n>>=1;
            }
            return sb.reverse().toString();
        }

        //Binary to Decimal
        public int BtoD(String s){
            int n=0,k=s.length()-1;
            for(char c:s.toCharArray()){
                if(c=='1')n+=1<<k;
                k--;
            }
            return n;
        }

        // Check ith bit is set or not
        public boolean isBitSet(int n,int i){
            return (n&(1<<i))!=0;
        }

        // Check N is power of 2
        public boolean isPowerOf2(int n){
            return (n&(n-1))==0;
        }


        // Check N is power of 4 or not
        public boolean isPowerOf4(int n) {
            return n>0 && (n & (n-1))==0 && n%3==1;
        }

        // Count Set bit in N | TC - O(no. of set bits)
        public int cntSetBit(int n){
            int cnt=0;
            while(n>0){
                n=n&(n-1);
                cnt++;
            }
            return cnt;
        }

        // Check all bits are set in given num | 7-> 111
        public boolean allBitSet(int n){
            return (n&(n+1))==0;
        }

        // Check bits in num having in alternative form | 101010 | 010101
        public boolean isAlternativeBit(int n){
            // 101010 ^ 010101 (n>>1) = 111111
            return allBitSet(n^(n>>1));
        }
    }

    
    static class SegmentTree{
        public long segmentArr[];
        public int N;
        SegmentTree(int n){
            N=n;
            segmentArr=new long[4*n+1];
        }
    
        public void build(long arr[]){
            build(0,0,N-1,arr);
        }
        
    
        public long query(int l, int r){
            return query(0,0,N-1,l,r);
        }
    
        public void update(int i,long val){
            update(0,0,N-1,i,val);
        }
    
        public void build(int ind,int low, int high, long arr[]){
            if(low==high){
                segmentArr[ind]=arr[low];
                return;
            }
    
            int mid=(low+high)/2;
            build(2*ind+1,low,mid,arr);
            build(2*ind+2,mid+1,high,arr);
    
            segmentArr[ind]=segmentArr[2*ind+1]+segmentArr[2*ind+2];
        }
    
        public long query(int ind,int low, int high,int l, int r){
            // Complete Overlap [l   low   high   r]
            if(low>=l && high<=r)return segmentArr[ind];
    
            // No Overlap  [low   high  l   r]  || [l  r  low   high]
            if(r<low || l>high)return Integer.MIN_VALUE;
    
            // Partially overlap   [low  l  high   r]  || [l   low   r   high]
            int mid=low+(high-low)/2;
            long left=query(2*ind+1,low,mid,l,r);
            long right = query(2*ind+2,mid+1,high,l,r);
    
            return left+right;
        }
    
        public void update(int ind, int low, int high, int i, long val){
            if(low==high){
                segmentArr[ind]=val;
                return;
            }
    
            int mid=low+(high-low)/2;
    
            // Given pos in left
            if(i<=mid) update(2*ind+1, low, mid, i, val);
            // Given pos in right
            else update(2*ind+2, mid+1, high, i, val);
    
            // Update root after updating [2*ind+1] or [2*ind+2]
            segmentArr[ind]=segmentArr[2*ind+1]+segmentArr[2*ind+2];
        }
    
    }
    


}


