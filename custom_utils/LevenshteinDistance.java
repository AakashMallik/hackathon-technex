package custom_utils;

import java.io.*;
import java.util.*;
import java.lang.*;

public class LevenshteinDistance{
	public static int find_distance(String a, String b){
		System.out.println(a+" "+b);
		// Note: higher the distance, more dissimilar the strings are
		int[][] dp = new int[a.length() + 1][b.length() + 1];
		int subs_cost;

		for (int i = 0; i <= a.length(); i++) {
			for (int j = 0; j <= b.length(); j++) {
				if (i == 0) {
					dp[i][j] = j;
				}
				else if (j == 0) {
					dp[i][j] = i;
				}
				else {
					subs_cost = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;
					dp[i][j] = Math.min(Math.min(
						dp[i - 1][j - 1] + subs_cost, // Substitution
						dp[i - 1][j] + 1), // Deletion
						dp[i][j - 1] + 1); // Add/Insertion
				}
			}
		}

		return dp[a.length()][b.length()];
	}

}
