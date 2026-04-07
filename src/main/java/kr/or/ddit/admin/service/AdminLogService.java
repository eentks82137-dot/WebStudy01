package kr.or.ddit.admin.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

public class AdminLogService {
    public String[] getRecentLogs(int linesToRead) throws IOException {
        String filePath = "/home/san02/logs/app.log";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            String[] recentLogs = new String[linesToRead];
            int index = 0;
            while ((line = reader.readLine()) != null) {
                recentLogs[index % linesToRead] = line;
                index++;
            }
            /*
             * 
             * 2026-04-07 11:41:58 [http-nio-8080-exec-72] INFO
             * k.o.d.a.filter.AccessLoggingFilter [req:b9e22420-7980-4d58-8e6e-cb7d8743beca]
             * - AccessLoggingFilter - requestId: b9e22420-7980-4d58-8e6e-cb7d8743beca,
             * userId: a001, requestURI: /admin/allMembers, clientIP: 0:0:0:0:0:0:0:1,
             * method: GET, status: 200, elapsedMs: 1
             * 
             * 
             */
            Arrays.sort(recentLogs, (e1, e2) -> {
                if (e1 == null && e2 == null)
                    return 0;
                if (e1 == null)
                    return 1;
                if (e2 == null)
                    return -1;
                String s1 = e1.trim();
                String s2 = e2.trim();
                if (s1.isEmpty() && s2.isEmpty())
                    return 0;
                if (s1.isEmpty())
                    return 1;
                if (s2.isEmpty())
                    return -1;

                String timestamp1 = s1.split(" ")[0] + " " + s1.split(" ")[1];
                String timestamp2 = s2.split(" ")[0] + " " + s2.split(" ")[1];
                return timestamp2.compareTo(timestamp1);
                // timestamp2와 timestamp1을 비교하여 timestamp2가 더 최근 양수, timestamp1이 더 최근이면 음수,
                // 같으면 0을 반환하여 내림차순 정렬
                // ex) timestamp1: 2026-04-07 11:41:58, timestamp2: 2026-04-07 11:42:00
            });

            return recentLogs;
        }
    }
}
